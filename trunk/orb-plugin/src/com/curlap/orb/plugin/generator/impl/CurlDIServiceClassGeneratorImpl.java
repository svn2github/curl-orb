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

package com.curlap.orb.plugin.generator.impl;

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.curlap.orb.plugin.generator.CurlGenerateException;

/**
 * generate Curl DI serivce class.
 * 
 * @author Wang Huailiang, Hitoshi Okada
 * @since 0.8
 */
public class CurlDIServiceClassGeneratorImpl extends CurlServiceClassGenerator 
{
	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		ICompilationUnit source = iCompilationUnit;
		VelocityContext context = super.generateClass();
		String beanId = null;
		try {
			for (IType iType : source.getAllTypes())
			{
				// annotation
				for (IAnnotation iAnnotation : iType.getAnnotations())
				{
					if (iAnnotation.getElementName().equals("AutoGenerateCurlServiceClient"))
					{
						for (IMemberValuePair pair : iAnnotation.getMemberValuePairs())
						{
							String memberName = pair.getMemberName();
							if (memberName.equals("serviceBeanId"))
								beanId = (String) pair.getValue();
						}
					}

					// "@Service" is a Spring framework annotation.
					//  See org.springframework.stereotype.Service
					if (iAnnotation.getElementName().equals("Service"))
					{
						for (IMemberValuePair pair : iAnnotation.getMemberValuePairs())
						{
							String memberName = pair.getMemberName();
							if (memberName.equals("value"))
								beanId = (String) pair.getValue();
						}
					}
				}
				if (beanId == null || beanId.length() == 0)
					throw new CurlGenerateException(
							"DI serivce needs 'bean id'. See 'serviceBeanId' of @AutoGenerateCurlServiceClient annotation."
					);
			}
			context.put("server_component", beanId);
			context.put("superclass", "ApplicationContextClient");
			return context;
		}
		catch (JavaModelException e)
		{
			throw new CurlGenerateException(e);
		}
	}

	public CurlDIServiceClassGeneratorImpl(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
