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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Field property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class FieldProperty implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private String modifier;
    private AnnotationProperty[] annotations;
    
    // constructor
    public FieldProperty()
    {
        // do nothing
    }

    public FieldProperty(Field field) throws GeneratorException
    {
        name = field.getName();
        type = field.getType().getName();
        modifier = Modifier.toString(field.getModifiers());
        annotations = AnnotationProperty.create(field.getAnnotations());
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

    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;   
    }

    public String getModifier()
    {
        return modifier;
    }
    public void setModifier(String modifier)
    {
        this.modifier = modifier;
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
