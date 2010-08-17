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

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * 
 * 
 * @author 
 * @since 0.8
 */
public abstract class CurlClassGenerator
{
	protected ICompilationUnit iCompilationUnit;
	
	private String savePath;
	
	private String fileName;

	public String getSavePath() 
	{
		return savePath;
	}

	public String getFileName() 
	{
		return fileName;
	}
	
	public abstract String getVelocityTemplateName();
	public abstract VelocityContext generateClass() throws CurlGenerateException;

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
				if (savePath == null || savePath.length() == 0)
				{
					IPackageFragment iPackageFragment = iType.getPackageFragment();
					savePath = "/" +
						(iPackageFragment != null ? 
								iPackageFragment.getElementName().toUpperCase() : ""
						);
				}
				
				// class name + ".scurl" is file name.
				this.fileName = iType.getElementName() + ".scurl";
			}
		} 
		catch (JavaModelException e) 
		{
			// skipped here
		}
	}
}
