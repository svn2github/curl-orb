// Copyright (C) 1998-2008, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curl.orb.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class ClassProperty implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
	private transient int modifier;
	private ClassProperty superClass; 
	private ClassProperty[] interfaceProperties; 
	private ConstructorProperty[] constructorProperties;
	private FieldProperty[] fieldProperties;
	private MethodProperty[] methodProperties;
	private AnnotationProperty[] annotations;
	
	private ClassProperty implementClass;
	private String serverClassName;

	// constructors
	public ClassProperty()
	{
		// do nothing
	}

	public ClassProperty(String name) throws GeneratorException
	{
		// class name
		this.name = name;
		this.serverClassName = name;
		if (isPrimitive(name)) 
			return;
		try 
		{
			Class<?> cls = Class.forName(name);
			modifier = cls.getModifiers();
			
			// super class
			Class<?> superClass = cls.getSuperclass();
			if (superClass != null && superClass.getName() != "java.lang.Object") 
				this.superClass = new ClassProperty(superClass.getName());

			// interfaces
			Class<?>[] interfaces = cls.getInterfaces();
			interfaceProperties = new ClassProperty[interfaces.length];
			for (int i = 0; i < interfaces.length; i++)
			{
				interfaceProperties[i] = new ClassProperty(interfaces[i].getName());
				interfaceProperties[i].setImplementClass(this);
			}

			// constructors
			Constructor<?>[] constructors = cls.getDeclaredConstructors();
			constructorProperties = new ConstructorProperty[constructors.length];
			for (int i = 0 ; i < constructors.length; i++) 
				constructorProperties[i] = new ConstructorProperty(constructors[i]);

			// fields
			Field[] fields = cls.getDeclaredFields();
			fieldProperties = new FieldProperty[fields.length];
			for (int i = 0; i < fields.length; i++)
				fieldProperties[i] = new FieldProperty(fields[i]);

			// methods
			Method[] methods = cls.getDeclaredMethods();
			methodProperties = new MethodProperty[methods.length];
			for (int i = 0; i < methods.length; i++)
				methodProperties[i] = new MethodProperty(methods[i]);
			
			// annotations
			if (!cls.isAnnotation())
				annotations = AnnotationProperty.create(cls.getAnnotations());
		}
		catch (ClassNotFoundException e)
		{
			throw new GeneratorException(e);
		}
		catch (NoClassDefFoundError e)
		{
			throw new GeneratorException(e);
		}
		catch (Throwable e) {
			// HACK! This code is just in case
			throw new GeneratorException(e);
		}
	}

	public ClassProperty(String name, String serverClassName) throws GeneratorException
	{
		this(name);
		this.serverClassName = serverClassName;
		for (int i = 0; i < interfaceProperties.length; i++)
			interfaceProperties[i].serverClassName = serverClassName;
	}


	// getter/setter
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public ClassProperty getSuperClass()
	{
		return superClass;
	}
	public void setSuperClass(ClassProperty superClass)
	{
		this.superClass = superClass;   
	}

	public FieldProperty[] getFieldProperties()
	{
		return fieldProperties;
	}
	public void setFieldProperties(FieldProperty[] fieldProperties)
	{
		this.fieldProperties = fieldProperties;
	}

	public ConstructorProperty[] getConstructorProperties()
	{
		return constructorProperties;
	}
	public void setConstructorProperties(ConstructorProperty[] constructorProperties)
	{
		this.constructorProperties = constructorProperties;
	}

	public MethodProperty[] getMethodProperties()
	{
		return methodProperties;
	}
	public void setMethodProperties(MethodProperty[] methodProperties)
	{
		this.methodProperties = methodProperties;
	}

	public String getNameInContext()
	{
		return serverClassName;
	}
	public void setNameInContext(String nameInContext)
	{
		this.serverClassName = nameInContext;    
	}

	ClassProperty getImplementClass()
	{
		return implementClass;
	}
	void setImplementClass(ClassProperty implementClass)
	{
		this.implementClass = implementClass;
	}
	
	public AnnotationProperty[] getAnnotations() {
		return annotations;
	}
	public void setAnnotations(AnnotationProperty[] annotations) {
		this.annotations = annotations;
	}
	
	boolean isPublic() 
	{
		return Modifier.isPublic(modifier);
	}
	
	
	private boolean isPrimitive(String name)
	{
		return (name.equals("int") || name.equals("boolean") || name.equals("byte") || name.equals("char") ||
				name.equals("long") || name.equals("short") || name.equals("double") || name.equals("float") ||
				name.equals("void"));
	}
}
