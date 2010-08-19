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
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.TypeNameMatch;

import com.curlap.orb.plugin.common.CurlSpecUtil;
import com.curlap.orb.plugin.common.JavaElementSearcher;
import com.curlap.orb.plugin.generator.CurlClassGenerator;
import com.curlap.orb.plugin.generator.CurlGenerateException;
import com.curlap.orb.plugin.generator.bean.Field;
import com.curlap.orb.plugin.generator.bean.Method;

/**
 * 
 * 
 * @author Wang Huailiang
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
		VelocityContext context = new VelocityContext();
		
		try
		{
			List<Method> methodsList = new ArrayList<Method>();
			List<String> interfaces = new ArrayList<String>();
			//List<Field> fields = new ArrayList<Field>();
			
			String packageName = null;
			String packageName4Curl = null;
			String className = null;
			String completeStatus = null;

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
				
				//interfaces 
				for(String s:iType.getSuperInterfaceNames()){
					System.out.println(s);
					interfaces.add(s);
				}

				//Annotation
		    	System.out.println("Annotation");
		    	for (IAnnotation annotation : iType.getAnnotations()){
		    		for (IMemberValuePair pair : annotation.getMemberValuePairs()){
		    			System.out.println(pair.getMemberName());
		    			System.out.println("\t"+pair.getValue());
		    			
		    			if (pair.getMemberName().equals("targetInterface"))
				    	{
				    		String interfaceName = (String) pair.getValue();
				    		IType interfaceIType;
				    		//if the interface does exist 
				    		if (interfaces.contains(interfaceName)){
				    			TypeNameMatch typeNameMatch = new JavaElementSearcher(iCompilationUnit).searchClassInfo(interfaceName);
				    			if(typeNameMatch!= null){
				    				interfaceIType = typeNameMatch.getType();
				    				// .............
				    				//change the class name as the interface's name
				    				className = interfaceName;
				    				
				    				//methods
				    				IMethod[] methods = interfaceIType.getMethods();
				    		    	for (IMethod method : methods){
				    		    		
				    			    	Method m = new Method();
				    			    	String tmp;
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
				    				
				    		    	
				    		    	completeStatus = "Curl source code is generated!";
				    		    	int classNameLength = className.length();
				    		    	System.out.println(className);
				    				context.put("packageName", packageName);
				    				context.put("packageName4Curl", packageName4Curl);
				    				context.put("className",className );
				    				context.put("methodsList", methodsList);
				    				context.put("completeStatus", completeStatus);
				    				
				    				return context;
				    		    	
				    			}
				    					
				    		} 
				    		else {
				    			completeStatus = "Error, cannot find the target Interface!";
				    			context.put("completeStatus", completeStatus);
				    			return context;
				    		}
				    	}
		    		}
		    	}

				
				
				//methods
				IMethod[] methods = iType.getMethods();
		    	for (IMethod method : methods){
		    		
			    	Method m = new Method();
			    	String tmp;
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
			
			completeStatus = "Curl source code is generated!";
			
			// merge to velocity.
		
			context.put("packageName", packageName);
			context.put("packageName4Curl", packageName4Curl);
			context.put("className",className );
			context.put("methodsList", methodsList);
			context.put("completeStatus", completeStatus);
			
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
