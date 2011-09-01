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

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.ICompilationUnit;

import com.curlap.orb.plugin.generator.CurlGenerateException;

/**
* generate Curl exception class.
* 
* @author Wang Huailiang
* @since 0.8
*/
public class CurlExceptionGeneratorImpl extends CurlDataClassGeneratorImpl
{
	@Override
	public String getVelocityTemplateName() 
	{
		return "templates/exception-class.vm";
	}

	@SuppressWarnings("unchecked")
	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		VelocityContext context = super.generateClass();
		//String superClassName = (String) context.get("superclass_name");
		//if (superClassName.equals("RuntimeException") || superClassName.equals("Throwable") || superClassName.equals("Error"))
		//	context.put("superclass_name", "Exception");
		context.put("superclass_name", "Exception");
		List<String> fields = (List<String>) context.get("fields");
		fields.addAll((List<String>) context.get("superclass_fields"));
		return context;
	}
	
	public CurlExceptionGeneratorImpl(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
