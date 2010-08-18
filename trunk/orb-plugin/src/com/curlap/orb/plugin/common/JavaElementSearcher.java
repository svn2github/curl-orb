package com.curlap.orb.plugin.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.TypeNameMatch;
import org.eclipse.jdt.core.search.TypeNameMatchRequestor;

public class JavaElementSearcher {
	
	private IJavaSearchScope scope;
	
	public JavaElementSearcher(ICompilationUnit target) throws JavaModelException {
		scope = createScope(target);
	}
	
	protected IJavaSearchScope createScope(ICompilationUnit target) throws JavaModelException {
		IJavaProject project = target.getJavaProject();
		IPackageFragmentRoot[] roots = project.getAllPackageFragmentRoots();
		return SearchEngine.createJavaSearchScope(roots); 
	}
	
	public List<TypeNameMatch> pickPackageNames(String[] classNames) throws JavaModelException {
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

		new SearchEngine().searchAllTypeNames(null,
				charClassNames,
				scope,
				requestor,
				IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH,
				null);
		return results;
	}
}
