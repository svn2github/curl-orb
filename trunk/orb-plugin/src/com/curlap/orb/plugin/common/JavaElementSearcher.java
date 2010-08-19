// Copyright (C) 1998-2010, Sumisho Computer Systems Corp. All Rights Reserved.

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.curlap.orb.plugin.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.TypeNameMatch;
import org.eclipse.jdt.core.search.TypeNameMatchRequestor;

/**
 * Searcher to search java elements.
 * 
 * @author Yohsuke Sugahara
 * @since 0.8
 */
public class JavaElementSearcher {
	
	private IJavaSearchScope scope;
	private Set<String> packageNamesForMask;
	private String currentPackageName;
	
	// constructor
	public JavaElementSearcher(ICompilationUnit target) throws JavaModelException {
		scope = createScope(target);
		packageNamesForMask = createPackageNameMask(target);
	
		// get current package name.
		// TODO handle the case where the package declaration doesn't exist.
		IPackageDeclaration[] pkgDecs = target.getPackageDeclarations();
		for (IPackageDeclaration pkgDec :  pkgDecs)
			currentPackageName = pkgDec.getElementName();
	}
	
	// extracts package name from full qualified name.
	//  e.g. com.curlap.orb.Foo -> com.curlap.orb
	private String extractPackageName(String elementName) {
		int lastIndex = elementName.lastIndexOf('.');
		if (lastIndex != -1)
			return elementName.substring(0, lastIndex);
		
		return elementName; 
	}
	
	// creates scope for searching given IJavaElement.
	// the default is the source folder of target's project.  
	protected IJavaSearchScope createScope(ICompilationUnit target) throws JavaModelException {
		IJavaElement[] elements = { target.getJavaProject() };
		return SearchEngine.createJavaSearchScope(
				elements,
				IJavaSearchScope.SOURCES);
	}

	// retains imported packages' name in the target ICompilationUnit.
	// this is because we may filter found items with it later.
	protected Set<String> createPackageNameMask(ICompilationUnit target) throws JavaModelException {
		Set<String> mask = new HashSet<String>();
		//TODO: move to constants.
		String prefix = "java";
		
		IImportDeclaration[] impDecs = target.getImports();
		for (IImportDeclaration impDec : impDecs) 
			if (!impDec.getElementName().startsWith(prefix))
				mask.add(impDec.getElementName());
		
		return mask;
	}
	
	// filters out items that aren't imported in target from found items.
	protected TypeNameMatch maskPackageNames(List<TypeNameMatch> foundMatches) {
		List<TypeNameMatch> candidates = new ArrayList<TypeNameMatch>();
		TypeNameMatch currentPackage = null;

		// picking priority:
		//  Consider 3 Hoge classes exist in:
		//    com.curlap.a.Hoge
		//    com.curlap.b.Hoge
		//    com.curlap.c.Hoge
		//  And current target file is in com.curlap.a with using Hoge class in it.
		//  We pick Hoge's package in order of priority blow:
		//    1. import with the class name  e.g.) com.curlap.b.Hoge
		//    2. current package  e.g.) com.curlap.a
		//    3. import with *  e.g.) com.curlap.b.*
		for (TypeNameMatch match : foundMatches) {
			String matchPkgName = match.getPackageName();
			if (currentPackageName.equals(matchPkgName))
				currentPackage = match;

			for (String maskPkgName : packageNamesForMask)
				if (maskPkgName.equals(match.getFullyQualifiedName()))
					return match;
				else if (maskPkgName.endsWith("*") &
						extractPackageName(maskPkgName).equals(matchPkgName))
					candidates.add(match);
		}
		if (currentPackage != null)
			return currentPackage;
		else if (!candidates.isEmpty())
			return candidates.get(0);
		else 
			return null;
	}
	
	// searches for given class name.
	// return found items without any filtering.
	public List<TypeNameMatch> searchRawClassInfo(String className) throws JavaModelException {
		final List<TypeNameMatch>results = new ArrayList<TypeNameMatch>();
		TypeNameMatchRequestor requestor = new TypeNameMatchRequestor() {
			@Override
			public void acceptTypeNameMatch(TypeNameMatch match) {
				results.add(match);
			}
		};
		new SearchEngine().searchAllTypeNames(
				null, 
				SearchPattern.R_EXACT_MATCH,
				className.toCharArray(),
				SearchPattern.R_EXACT_MATCH,
				IJavaSearchConstants.CLASS_AND_INTERFACE,
				scope,
				requestor,
				IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH,
				null);
		return results;
	}

	// searches for given class name.
	// return value is filtered by imported packages' name.
	public TypeNameMatch searchClassInfo(String className) throws JavaModelException {
		return maskPackageNames(searchRawClassInfo(className));
	}

	// searches for given jdt's signature styled class name. 
	public TypeNameMatch searchClassInfoWithSignature(String classNameInSignature)
	throws JavaModelException, IllegalArgumentException {
		String className = Signature.toString(classNameInSignature);
		return searchClassInfo(className);
	}
	
	// searches for given class name.
	// return found items without any filtering.
	// TODO change the way to search multiple classes.
	public List<TypeNameMatch> searchRawClassesInfo(String ... classNames) throws JavaModelException {
		// name
		char[][] charClassNames = new char[classNames.length][];
		for (int i = 0; i < charClassNames.length; i++)
			charClassNames[i] = classNames[i].toCharArray();
		
		// requestor
		final List<TypeNameMatch> results = new ArrayList<TypeNameMatch>();
		TypeNameMatchRequestor requestor = new TypeNameMatchRequestor() {
			@Override
			public void acceptTypeNameMatch(TypeNameMatch match) {
				results.add(match);
			}
		};
		new SearchEngine().searchAllTypeNames(
				null,
				charClassNames,
				scope,
				requestor,
				IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH,
				null);
		return results;
	}
}
