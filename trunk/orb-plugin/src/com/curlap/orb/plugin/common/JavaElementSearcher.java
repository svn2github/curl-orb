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

public class JavaElementSearcher {
	
	private IJavaSearchScope scope;
	private Set<String> packageNamesForMask;
	private String currentPackageName;
	
	public JavaElementSearcher(ICompilationUnit target) throws JavaModelException {
		scope = createScope(target);
		packageNamesForMask = createPackageNameMask(target);
	
		// get current package name.
		//TODO: handle the case where the package declaration doesn't exist.
		IPackageDeclaration[] pkgDecs = target.getPackageDeclarations();
		for (IPackageDeclaration pkgDec :  pkgDecs)
			currentPackageName = pkgDec.getElementName();
	}
	
	private String extractPackageName(String elementName) {
		int lastIndex = elementName.lastIndexOf('.');
		if (lastIndex != -1)
			return elementName.substring(0, lastIndex);
		
		return elementName; 
	}
	
	protected IJavaSearchScope createScope(ICompilationUnit target) throws JavaModelException {
		IJavaElement[] elements = { target.getJavaProject() };
		return SearchEngine.createJavaSearchScope(
				elements,
				IJavaSearchScope.SOURCES);
	}

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
	
	protected TypeNameMatch maskPackageNames(List<TypeNameMatch> foundMatches) {
		List<TypeNameMatch> candidates = new ArrayList<TypeNameMatch>();
		TypeNameMatch currentPackage = null;

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

	public TypeNameMatch searchClassInfo(String className) throws JavaModelException {
		return maskPackageNames(searchRawClassInfo(className));
	}

	public TypeNameMatch searchClassInfoWithSignature(String classNameInSignature)
	throws JavaModelException, IllegalArgumentException {
		String className = Signature.toString(classNameInSignature);
		return searchClassInfo(className);
	}
	
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
