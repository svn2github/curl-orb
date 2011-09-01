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

package com.curlap.orb.plugin.generator;

import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.TypeNameMatch;

import com.curlap.orb.plugin.common.CurlSpecUtil;
import com.curlap.orb.plugin.common.JavaElementSearcher;

/**
 * 
 * 
 * @author 
 * @since 0.8
 */
public abstract class CurlClassGenerator
{
	protected ICompilationUnit iCompilationUnit;
	
	protected String savePath;

	protected String packageName;
	
	protected String fileName;
	
	protected String packageFileName;

	public String getPackageName() 
	{
		return packageName;
	}

	public String getSavePath() 
	{
		return savePath;
	}

	public String getFileName() 
	{
		return fileName;
	}
	
	public String getPackageFileName() 
	{
		return packageFileName;
	}
	
	public abstract String getVelocityTemplateName();
	public abstract VelocityContext generateClass() throws CurlGenerateException;
	
	// utility methods
	protected String addImportedPackageIfNecessary(
			Set<String> importPackages, 
			String fullClassName) throws JavaModelException
	{
		String importedPackageName = 
			CurlSpecUtil.marshalCurlPackage(fullClassName, true);
		if (importedPackageName != null)
			importPackages.add(importedPackageName.toUpperCase());
		else
		{
			TypeNameMatch typeNameMatch = 
				new JavaElementSearcher(iCompilationUnit).searchClassInfo(fullClassName);
			if (typeNameMatch != null)
				importPackages.add(typeNameMatch.getPackageName().toUpperCase());
		}
		return CurlSpecUtil.getClassNameFromPackageName(fullClassName);
	}
	
	protected String addImportedPackageIfNecessaryWithSignature(
			Set<String> importPackages, 
			String fullClassName) throws JavaModelException
	{
		String importedPackageName = 
			CurlSpecUtil.marshalCurlPackage(fullClassName, true);
		if (importedPackageName != null)
			importPackages.add(importedPackageName.toUpperCase());
		else
		{
			TypeNameMatch typeNameMatch = 
				new JavaElementSearcher(iCompilationUnit).searchClassInfoWithSignature(fullClassName);
			if (typeNameMatch != null)
				importPackages.add(typeNameMatch.getPackageName().toUpperCase());
		}
		return CurlSpecUtil.getClassNameFromPackageName(fullClassName);
	}
	
	// constructor
	protected CurlClassGenerator(ICompilationUnit iCompilationUnit, String savePath)
	{
		this.iCompilationUnit = iCompilationUnit;
		this.savePath = savePath;
		try
		{
			for (IType iType : iCompilationUnit.getAllTypes())
			{
				// package name is directory name to save. e.g) /COM.TEST
				IPackageFragment iPackageFragment = iType.getPackageFragment();
				this.packageName = iPackageFragment.getElementName();
				if (savePath == null || savePath.length() == 0)
				{
					savePath = "/" +
						(iPackageFragment != null ? 
								packageName.toUpperCase() : ""
						);
				}
				
				// class name + ".scurl" is file name.
				this.fileName = iType.getElementName() + ".scurl";
				this.packageFileName = "load.scurl";
			}
		} 
		catch (JavaModelException e) 
		{
			// skipped here
		}
	}
	
	public String getPackageVelocityTemplateName(){
		return "templates/load.vm";
	}
}
