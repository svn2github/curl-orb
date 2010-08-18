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


import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.curlap.orb.plugin.common.CurlSpecUtil;
import com.curlap.orb.plugin.generator.CurlClassGenerator;
import com.curlap.orb.plugin.generator.CurlGenerateException;
import com.curlap.orb.plugin.generator.bean.Field;
import com.curlap.orb.plugin.generator.bean.Method;

/**
 * 
 * 
 * @author 
 * @since 0.8
 */
public class CurlHttpSessionServiceClassGeneratorImpl extends CurlClassGenerator 
{
	@Override
	public String getVelocityTemplateName() 
	{
		return "templates/HttpSession.vm";
	}
	
	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		// TODO Auto-generated method stub
		ICompilationUnit source = iCompilationUnit;
		try
		{
			List<Method> methodsList = new ArrayList<Method>();
			//List<Field> fields = new ArrayList<Field>();
			
			String packageName = null;
			String packageName4Curl = null;
			String className = null;

			String tmp = null;
			
			for (IType iType : source.getAllTypes())
			{
				// package
				IPackageFragment iPackageFragment = iType.getPackageFragment();
				packageName = 
					(iPackageFragment != null ? 
							iPackageFragment.getElementName() : ""
					);
					
				packageName4Curl = packageName.toUpperCase();
				// class name
				className = iType.getElementName();
				if (className == null)
					throw new CurlGenerateException("There is no class name.");
				
			/*	// superclass
				if (iType.getSuperclassName() != null)
				{
					superClassName = 
						CurlSpecUtil.marshalCurlTypeWithSignature(iType.getSuperclassName());				
				
				}
			*/
				
				
/*				// fields
				for (IField iField : iType.getFields())
				{
					String name = iField.getElementName();

					Field field = new Field();
		    		field.setName(name);
		    		String fieldType = 
		    			CurlSpecUtil.marshalCurlTypeWithSignature(iField.getTypeSignature());
		    		field.setType(fieldType);
		    		field.setIsStatic((Flags.isStatic(iField.getFlags()) ? "let" : "field"));
		    		field.setIsTransient(Flags.isTransient(iField.getFlags()));
		    		String modifier = Flags.toString(iField.getFlags());
		    		field.setGetterModifier(modifier + "-get");
		    		field.setSetterModifier(modifier + "-set");
		    		fields.add(field);
				}
			*/
				
				//methods
				IMethod[] methods = iType.getMethods();
		    	for (IMethod method : methods){
		    		
			    	Method m = new Method();

		    		tmp = method.getElementName();
		    		m.setMethodName(tmp);
		    		m.setMethodName4Curl(CurlSpecUtil.marshalCurlName(tmp, true));
		    		
		    		String[] paramNames = method.getParameterNames();
		    		String[] paramTypes = method.getParameterTypes();
		    		
		    		String noParams = "NoParams";
		    		String noReturn = "Empty";
		    		String noArguments = "Empty";
		    		String paramName = null;
		    		
		    		for (int i = 0; i<paramNames.length; i++){
		    			paramName = CurlSpecUtil.marshalCurlName(paramNames[i], true);
		    			if (i==0){
		    				noParams = paramName + ":" + CurlSpecUtil.marshalCurlTypeWithSignature(paramTypes[i]);
		    				noArguments = paramName;
		    			} else {
		    				noParams = noParams + ", " + paramName + ":" + CurlSpecUtil.marshalCurlTypeWithSignature(paramTypes[i]);;
		    				noArguments = noArguments + ", " + paramName;
		    			}
		    		}
		    		
		    		m.setMethodParams(noParams);
		    		m.setMethodArguments4Curl(noArguments);
		    		
		    		noReturn = CurlSpecUtil.marshalCurlTypeWithSignature(method.getReturnType());
		    		m.setMethodReturnType(noReturn);

		    		methodsList.add(m);
		    	}
				
			}
			
			
			// merge to velocity.
			VelocityContext context = new VelocityContext();
			
			context.put("packageName", packageName);
			context.put("packageName4Curl", packageName4Curl);
			context.put("className",className );
			context.put("methodsList", methodsList);
	
			
			return context;
		}
		catch (JavaModelException e) 
		{
			throw new CurlGenerateException(e);
		}
		
	}

	public CurlHttpSessionServiceClassGeneratorImpl(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
