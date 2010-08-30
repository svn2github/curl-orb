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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.TypeNameMatch;

import com.curlap.orb.plugin.common.CurlSpecUtil;
import com.curlap.orb.plugin.common.JavaElementAnalyzer;
import com.curlap.orb.plugin.common.JavaElementSearcher;
import com.curlap.orb.plugin.common.JavadocContent;
import com.curlap.orb.plugin.generator.CurlClassGenerator;
import com.curlap.orb.plugin.generator.CurlGenerateException;
import com.curlap.orb.plugin.generator.bean.Method;

/**
 * generate Curl serivce class.
 * 
 * @author Wang Huailiang
 * @since 0.8
 */
public abstract class CurlServiceClassGenerator extends CurlClassGenerator 
{
	@Override
	public String getVelocityTemplateName() 
	{
		return "templates/service-class.vm";
	}
	
	protected List<Method> getMethodsFromIType(
			IType iType, 
			Set<String> importPackages, 
			boolean returnTypeIsNullable) throws JavaModelException, IllegalArgumentException
	{
		// NOTE: Extract getter and setter. The other methods is skipped.
		List<Method> methods = new ArrayList<Method>();
		for (IMethod iMethod : iType.getMethods())
		{
			// NOTE: generate only "public" method. 
			if (!Flags.toString(iMethod.getFlags()).equals("public"))
				continue;
			Method method = new Method();
			String methodName = iMethod.getElementName();
			method.setMethodName(methodName);
			method.setMethodName4Curl(
					CurlSpecUtil.marshalCurlName(methodName, true)
			);
			boolean returnTypeOfMethodIsNullable = returnTypeIsNullable;
			if (returnTypeOfMethodIsNullable)
			{
				if (iMethod.getAnnotation("NotNull").exists())
					returnTypeOfMethodIsNullable = false;
			}
			else
			{
				if (iMethod.getAnnotation("Nullable").exists())
					returnTypeOfMethodIsNullable = true;
			}
			method.setMethodReturnType(
					CurlSpecUtil.marshalCurlTypeWithSignature(
							addImportedPackageIfNecessaryWithSignature(
									importPackages, 
									iMethod.getReturnType()
							),
							returnTypeOfMethodIsNullable,
							false
					)
			);
			String[] paramNames = iMethod.getParameterNames();
			String[] paramTypes = iMethod.getParameterTypes();
			StringBuffer buf = new StringBuffer();
			StringBuffer invokeBuf = new StringBuffer();
			for (int i = 0; i < paramNames.length; i++)
			{
				if (i != 0)
				{
					buf.append(", ");
					invokeBuf.append(", ");
				}
				String paramName = 
					CurlSpecUtil.marshalCurlName(paramNames[i], true);
				buf.append(paramName);
				buf.append(':');
				buf.append(
						CurlSpecUtil.marshalCurlTypeWithSignature(
								addImportedPackageIfNecessaryWithSignature(
										importPackages, 
										paramTypes[i]
								)
						)		
				);
				invokeBuf.append(paramName);
			}
			method.setMethodParams(buf.toString());
			method.setMethodArguments4Curl(invokeBuf.toString());
			methods.add(method);
		}
		return methods;
	}

	protected List<Method> addMethodsOfSuperclass(
			ICompilationUnit source,
			String superclass,
			Set<String> importPackages, 
			boolean returnTypeIsNullable) throws JavaModelException
	{
		TypeNameMatch typeNameMatch = 
			new JavaElementSearcher(source).searchClassInfo(superclass);
		IType iType = typeNameMatch.getType();
		List<Method> methods = new ArrayList<Method>();
		methods.addAll(
				getMethodsFromIType(
						iType, 
						importPackages,
						returnTypeIsNullable
				)
		);
		addImportedPackageIfNecessary(importPackages, superclass);
		String superclassOfthis = iType.getSuperclassName();
		if (superclassOfthis != null)
		{
			methods.addAll(
					addMethodsOfSuperclass(
							iType.getCompilationUnit(),
							superclassOfthis, 
							importPackages,
							returnTypeIsNullable
					)
			);
		}
		return methods;
	}

	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		ICompilationUnit source = iCompilationUnit;
		Set<String> importPackages = new HashSet<String>();
		List<Method> methods = new ArrayList<Method>();

		String packageName = null;
		String targetInterface = null;
		JavadocContent classJavadoc = null;
		String className = null;
		boolean returnTypeIsNullable = true;
		boolean generateTestTemplate = false;
		boolean generateAsyncMethod = true;
		importPackages.add("COM.CURLAP.ORB");
		try 
		{
			for (IType iType : source.getAllTypes())
			{
				// package
				IPackageFragment iPackageFragment = iType.getPackageFragment();
				packageName = 
					(iPackageFragment != null ? 
							iPackageFragment.getElementName().toUpperCase() : ""
					);

				// javadoc
				classJavadoc = JavaElementAnalyzer.getJavaDoc(iType);
				
				// class name
				className = iType.getElementName();
				if (className == null)
					throw new CurlGenerateException("There is no class name.");

				// superclass
				String superclass = iType.getSuperclassName();
				if (superclass != null)
				{
					methods.addAll(
							addMethodsOfSuperclass(
									source,
									superclass, 
									importPackages,
									returnTypeIsNullable
							)
					);
					// NOTE: the following code would be not necessary 
					// superclassBuf.append(", ");
					// superclassBuf.append(superclass);
					// addImportedPackageIfNecessary(importPackages, superclass);
				}
				
				// annotation
				for (IAnnotation iAnnotation : iType.getAnnotations())
				{
					if (iAnnotation.getElementName().equals("AutoGenerateCurlServiceClient"))
					{
						for (IMemberValuePair pair : iAnnotation.getMemberValuePairs())
						{
							String memberName = pair.getMemberName();
							if (memberName.equals("generateTestTemplate"))
								generateTestTemplate = ((Boolean) pair.getValue()).booleanValue();
							if (memberName.equals("generateAsyncMethod"))
								generateAsyncMethod = ((Boolean) pair.getValue()).booleanValue();
							if (memberName.equals("targetInterface"))
								targetInterface = (String) pair.getValue();
						}
					}
					if (iAnnotation.getElementName().equals("DefaultNotNull"))
						returnTypeIsNullable = false;	
				}

				// interface
				String[] interfaceNames = iType.getSuperInterfaceNames();
				if (interfaceNames.length > 0)
				{
					if (interfaceNames.length == 1)
						targetInterface = interfaceNames[0]; // In the case of only one interface 
					else
					{
						boolean hasSuchInterface = false;
						for (String interfaceName : interfaceNames)
							if (interfaceName.equals(targetInterface))
								hasSuchInterface = true;
						if (!hasSuchInterface)
							throw new CurlGenerateException("'targetInterface' is wrong interface.");
					}
				}
				if (targetInterface != null)
				{
					// Add interfaces' methods
					TypeNameMatch typeNameMatch = 
						new JavaElementSearcher(source).searchClassInfo(targetInterface);
					IType interfaceIType = typeNameMatch.getType();
					className = targetInterface;
					fileName = className + ".scurl";
					methods.addAll(
							getMethodsFromIType(
									interfaceIType, 
									importPackages,
									returnTypeIsNullable
							)
					);
					addImportedPackageIfNecessary(importPackages, targetInterface);

					// Add interfaces' methods of interface.
					for (String interfaceNamesOfInterface : interfaceIType.getSuperInterfaceNames())
					{
						TypeNameMatch typeNameMatchOfInterface = 
							new JavaElementSearcher(
									interfaceIType.getCompilationUnit()
							).searchClassInfo(interfaceNamesOfInterface);
						methods.addAll(
								getMethodsFromIType(
										typeNameMatchOfInterface.getType(), 
										importPackages,
										returnTypeIsNullable
								)
						);
					}
				}
				else
				{
					// methods 
					methods.addAll(
							getMethodsFromIType(
									iType,
									importPackages,
									returnTypeIsNullable
							)
					);
				}
			}
		}
		catch (JavaModelException e) 
		{
			throw new CurlGenerateException(e);
		}

		// merge to velocity.
		VelocityContext context = new VelocityContext();
		context.put("is_template", generateTestTemplate); 
		context.put("has_async_method", generateAsyncMethod);
		context.put("package_name", packageName);
		context.put("import_packages", importPackages);
		context.put("javadocContent", classJavadoc);
		context.put("class_name", className);
		context.put("methods", methods);
		return context;
	}
	
	public CurlServiceClassGenerator(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
