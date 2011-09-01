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

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.curlap.orb.plugin.generator.impl.CurlDIServiceClassGeneratorImpl;
import com.curlap.orb.plugin.generator.impl.CurlDataClassGeneratorImpl;
import com.curlap.orb.plugin.generator.impl.CurlExceptionGeneratorImpl;
import com.curlap.orb.plugin.generator.impl.CurlHttpSessionServiceClassGeneratorImpl;

/**
 * 
 * 
 * @author Wang Huailiang, Hitoshi Okada
 * @since 0.8
 */
public class CurlClassGeneratorFactory 
{
	private static CurlClassGeneratorFactory instance;
	
	public static CurlClassGeneratorFactory getInstance()
	{
		if (CurlClassGeneratorFactory.instance == null)
			CurlClassGeneratorFactory.instance = new CurlClassGeneratorFactory();
		return CurlClassGeneratorFactory.instance;
	}
	
	private String getSavePath(IAnnotation annotation) throws CurlGenerateException 
	{
		try
		{
			for (IMemberValuePair pair : annotation.getMemberValuePairs())
			{
				String memberName = pair.getMemberName();
				if (memberName.equals("savePath"))
					return (String) pair.getValue();
			}
		}
		catch (JavaModelException e) 
		{
			throw new CurlGenerateException(e);
		}
		// TODO: package name (e.g. COM.TEST.ABC.Foo --> COM/TEST/ABC/)
		return ""; 
	}
	
	public CurlClassGenerator createGenerator(ICompilationUnit iCompilationUnit) throws CurlGenerateException
	{
		ICompilationUnit source = iCompilationUnit;
		try
		{
			for (IType iType : source.getAllTypes())
			{
		    	for (IAnnotation annotation : iType.getAnnotations())
		    	{
		    		String name = annotation.getElementName();
		    		if (name.equals("AutoGenerateCurlServiceClient"))
		    		{
		    			String savePath = getSavePath(annotation);
		    			for (IMemberValuePair pair : annotation.getMemberValuePairs())
		    			{
		    				if (pair.getMemberName().equals("serviceType"))
		    					if (((String) pair.getValue()).equals("HttpSession"))
		    						return new CurlHttpSessionServiceClassGeneratorImpl(source, savePath);
		    			}
		    			return new CurlDIServiceClassGeneratorImpl(source, savePath);
		    		}
		    		else if (name.equals("AutoGenerateCurlDto"))
		    		{
		    			String savePath = getSavePath(annotation);
		    			return new CurlDataClassGeneratorImpl(source, savePath);
		    		}
		    		else if (name.equals("AutoGenerateCurlException"))
		    		{
		    			String savePath = getSavePath(annotation);
		    			return new CurlExceptionGeneratorImpl(source, savePath);
		    		}
		    	}
			}
		}
		catch (JavaModelException e) 
		{
			throw new CurlGenerateException(e);
		}
		//throw new CurlGenerateException("Cannot create generator.");
		return null; 
	}
	
	private CurlClassGeneratorFactory()
	{
		// do nothing
	}
}
