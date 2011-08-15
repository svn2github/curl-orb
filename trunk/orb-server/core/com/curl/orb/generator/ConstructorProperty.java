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
import java.lang.reflect.Modifier;

/**
 * Constructor property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class ConstructorProperty implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String modifier;
	private String[] argumentTypes; 
	private AnnotationProperty[] annotations;
	
	public ConstructorProperty(Constructor<?> constructor) throws GeneratorException
	{
		modifier = Modifier.toString(constructor.getModifiers());
		Class<?>[] params = constructor.getParameterTypes();
		argumentTypes = new String[params.length];
		for (int i = 0; i < params.length; i++) 
			argumentTypes[i] = params[i].getName();
		annotations = AnnotationProperty.create(constructor.getAnnotations());
	}

	public String getModifier()
	{
		return modifier;
	}
	public void setModifier(String modifier)
	{
		this.modifier = modifier;
	}

	public String[] getArgumentTypes()
	{
		return argumentTypes;
	}
	public void setArgumentTypes(String[] argumentTypes)
	{
		this.argumentTypes = argumentTypes;
	}
	
	public AnnotationProperty[] getAnnotations()
	{
		return annotations;
	}
	public void setAnnotations(AnnotationProperty[] annotations)
	{
		this.annotations = annotations;
	}
}
