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
import org.eclipse.jdt.core.ICompilationUnit;

import com.curlap.orb.plugin.generator.CurlGenerateException;

/**
 * Curl service class for HttpSession generation.
 * 
 * @author Wang Huailiang
 * @since 0.8
 */
public class CurlHttpSessionServiceClassGeneratorImpl extends CurlServiceClassGenerator 
{
	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		VelocityContext context = super.generateClass();
		context.put("server_component", 
				((String) context.get("package_name")).toLowerCase() + "." +context.get("class_name")
		);
		context.put("superclass", "HttpSessionClient");
		return context;
	}

	public CurlHttpSessionServiceClassGeneratorImpl(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
