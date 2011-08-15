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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.curl.orb.context.AbstractApplicationContext;
import com.curl.orb.context.ApplicationContextException;
import com.curl.orb.context.ApplicationContextFactory;
import com.curl.orb.security.Environment;
import com.curl.orb.security.RemoteService;

/**
 * Loader of the class property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
@RemoteService(Environment.DEVELOPMENT)
public class ClassPropertyLoader
{

	/**
	 * Get one class property.
	 * 
	 * @param name the component name
	 * @return the class property
	 * @throws GeneratorException
	 */
	public static ClassProperty getClassPropertyFromClassName(String name) throws GeneratorException
	{
		// debug
		(LogFactory.getLog(ClassPropertyLoader.class)).info("Generate Curl code [" + name + "]");
		ClassProperty classProperty = new ClassProperty(name);
		if (!classProperty.isPublic())
			throw new GeneratorException("Cannot access to this class:" + name);
		return classProperty;
	}


	/**
	 * Get one class property in ApplicationContext.
	 * 
	 * @param name the component name
	 * @return the class property
	 * @throws ApplicationContextException
	 * @throws GeneratorException
	 */
	public static ClassProperty getClassPropertyInApplicationContext(String name) throws ApplicationContextException, GeneratorException
	{
		// debug
		(LogFactory.getLog(ClassPropertyLoader.class)).info("Generate Curl code [" + name + "]");
		ClassProperty classProperty =
			new ClassProperty(ApplicationContextFactory.getInstance(null).getApplicationContext().getObjectType(name).getName(), name);
		if (!classProperty.isPublic())
			throw new GeneratorException("Cannot access to this class:" + name);
		return classProperty;
	}

	/**
	 * Get all class properties.
	 * 
	 * @return class properties
	 * @throws ApplicationContextException
	 */
	public static ClassProperty[] getAllClassProperties() throws ApplicationContextException
	{
		return ClassPathLoader.getInstance(null).getClassProperties();
	}

	/**
	 * Get all class properties in ApplicationContext.
	 * 
	 * @return class properties
	 * @throws ApplicationContextException
	 * @throws GeneratorException
	 */
	public static ClassProperty[] getAllClassPropertiesInApplicationContext() throws ApplicationContextException, GeneratorException
	{
		AbstractApplicationContext context = ApplicationContextFactory.getInstance(null).getApplicationContext();
		String[] names = context.getObjectNames();
		java.util.Arrays.sort(names);
		List<ClassProperty> classProperties = new ArrayList<ClassProperty>();
		for (int i = 0; i < names.length; i++)
		{
			String name = names[i];
			ClassProperty classProperty = new ClassProperty(context.getObjectType(name).getName(), name);
			if (classProperty.isPublic())
				classProperties.add(classProperty);
		}
		return classProperties.toArray(new ClassProperty[]{});
	}
}
