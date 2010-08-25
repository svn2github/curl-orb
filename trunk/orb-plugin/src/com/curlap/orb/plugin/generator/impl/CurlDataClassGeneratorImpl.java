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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
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
import com.curlap.orb.plugin.generator.bean.Field;

/**
 * generate Curl data class.
 * 
 * @author Hitoshi Okada
 * @since 0.8
 */
public class CurlDataClassGeneratorImpl extends CurlClassGenerator
{
	@Override
	public String getVelocityTemplateName() 
	{
		return "templates/DataClass.vm";
	}
	
	private List<Field> getAllSuperclassFields(
			Set<String> importPackages,
			String superclass
			) throws JavaModelException
	{
		List<Field> fields = new ArrayList<Field>();
		TypeNameMatch typeNameMatch = 
			new JavaElementSearcher(iCompilationUnit).searchClassInfo(superclass);
		IType iType = typeNameMatch.getType();
		for (IField iField : iType.getFields())
		{
			String name = iField.getElementName();
			Field field = new Field();
    		field.setName(name);
    		field.setType(
    				CurlSpecUtil.marshalCurlTypeWithSignature(
    						addImportedPackageIfNecessaryWithSignature(
    								importPackages, 
    								iField.getTypeSignature()
    						)
    				)
    		);
    		field.setDefaultValue(CurlSpecUtil.getDefaultValue(field.getType()));
    		/* Currently not necessary. */
    		/*
    		field.setIsStatic(
    				(Flags.isStatic(iField.getFlags()) ? "let" : "field")
    		);
    		field.setIsTransient(Flags.isTransient(iField.getFlags()));
    		String modifier = Flags.toString(iField.getFlags());
    		if (modifier.length() == 0)
    			modifier = "package";
    		field.setGetterModifier(modifier + "-get");
    		field.setSetterModifier(modifier + "-set");
    		*/
    		fields.add(field);
		}
		// more superclasses
		if (iType.getSuperclassName() != null)
			fields.addAll(getAllSuperclassFields(importPackages, iType.getSuperclassName()));
		return fields;
	}
	
	@Override
	public VelocityContext generateClass() throws CurlGenerateException
	{
		ICompilationUnit source = iCompilationUnit;
		try
		{
			Set<String> importPackages = new HashSet<String>();
			List<Field> fields = new ArrayList<Field>();
			List<Field> superClassFields = null;
			
			String packageName = null;
			String className = null;
			String superClassName = null;
			JavadocContent classJavadoc = null;
			
			for (IType iType : source.getAllTypes())
			{
				// package
				IPackageFragment iPackageFragment = iType.getPackageFragment();
				packageName = 
					(iPackageFragment != null ? 
							iPackageFragment.getElementName().toUpperCase() : ""
					);
					
				// class name
				className = iType.getElementName();
				if (className == null)
					throw new CurlGenerateException("There is no class name.");
				
				// javadoc
				classJavadoc = JavaElementAnalyzer.getJavaDoc(iType);
		    	
				// superclass
				if (iType.getSuperclassName() != null)
				{
					superClassName = 
						addImportedPackageIfNecessary(
								importPackages, 
								iType.getSuperclassName()
						);
					// fields of superclass
					superClassFields = 
						getAllSuperclassFields(importPackages, superClassName);
				}
				
				// fields
				for (IField iField : iType.getFields())
				{
					String name = iField.getElementName();
					Field field = new Field();
		    		field.setName(name);
		    		field.setType(
		    				CurlSpecUtil.marshalCurlTypeWithSignature(
		    						addImportedPackageIfNecessaryWithSignature(
		    								importPackages, 
		    								iField.getTypeSignature()
		    						)
		    				)
		    		);
		    		field.setDefaultValue(CurlSpecUtil.getDefaultValue(field.getType()));
		    		field.setIsStatic(
		    				(Flags.isStatic(iField.getFlags()) ? "let" : "field")
		    		);
		    		// NOTE: seriall version UID
					if (name.equals("serialVersionUID") && Flags.isStatic(iField.getFlags()))
						continue;
		    		field.setIsTransient(Flags.isTransient(iField.getFlags()));
		    		String modifier = Flags.toString(iField.getFlags());
		    		if (modifier.length() == 0)
		    			modifier = "package";
		    		field.setGetterModifier(modifier + "-get");
		    		field.setSetterModifier(modifier + "-set");
		    		field.setJavadocContent(JavaElementAnalyzer.getJavaDoc(iField));
		    		fields.add(field);
				}
				
				// methods 
				// NOTE: Extract getter and setter. The other methods is skipped.
				for (IMethod method : iType.getMethods())
				{
					String name = method.getElementName();
					// getter into field
					if (CurlSpecUtil.isGetter(name))
					{
						if (method.getParameterNames().length == 0)
						{
							String returnType = 
								CurlSpecUtil.marshalCurlTypeWithSignature(
										addImportedPackageIfNecessaryWithSignature(
												importPackages, 
												method.getReturnType()
										)
								);
							String modifier = Flags.toString(method.getFlags());
							if (modifier.length() == 0)
								modifier = "package";
							for (Field field : fields)
							{
								String fieldName = 
									CurlSpecUtil.getGetterOrSetterName(name);
								if (fieldName.equals(field.getName()) && 
										returnType.equals(field.getType()))
									field.setGetterModifier(modifier + "-get");
							}
						}
					}
					// setter into field
					if (CurlSpecUtil.isSetter(name))
					{
						if (CurlSpecUtil.marshalCurlTypeWithSignature(method.getReturnType()).equals("void") && 
								method.getParameterNames().length == 1)
						{
							String argumentType = 
								CurlSpecUtil.marshalCurlTypeWithSignature(
										addImportedPackageIfNecessaryWithSignature(
												importPackages, 
												method.getParameterTypes()[0]
										)
								);
							String modifier = Flags.toString(method.getFlags());
							if (modifier.length() == 0)
								modifier = "package";
							for (Field field : fields)
							{
								String fieldName = 
									CurlSpecUtil.getGetterOrSetterName(name);
								if (fieldName.equals(field.getName()) && 
										argumentType.equals(field.getType()))
									field.setSetterModifier(modifier + "-set");
							}
						}
					}
				}
			}
			
			// merge to velocity.
			VelocityContext context = new VelocityContext();
			context.put("package_name", packageName);
			context.put("import_packages", importPackages);
			context.put("class_name", className);
			context.put("classJavadoc", classJavadoc);
			if (superClassName != null)
			{
				context.put("has_superclass", true);
				context.put("superclass_name", superClassName);
				context.put("superclass_fields", superClassFields);
			}
			else
			{
				context.put("has_superclass", false);
			}
			context.put("fields", fields);
			return context;
		}
		catch (JavaModelException e) 
		{
			throw new CurlGenerateException(e);
		}
	}

	public CurlDataClassGeneratorImpl(
			ICompilationUnit iCompilationUnit,
			String savePath) 
	{
		super(iCompilationUnit, savePath);
	}
}
