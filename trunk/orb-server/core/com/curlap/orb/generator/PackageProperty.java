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

package com.curlap.orb.generator;

/**
 * Package property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class PackageProperty implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

    private String name;
    private ClassProperty[] classProperties;
    
    // constructor
    public PackageProperty(String name)
    {
        this.name = name;
    }

    public PackageProperty(String name, ClassProperty[] classProperties)
    {
        this.name = name;
        this.classProperties = classProperties;
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

    public ClassProperty[] getClassProperties()
    {
        return classProperties;
    }
    public void setClassProperties(ClassProperty[] classProperties)
    {
        this.classProperties = classProperties;
    }
    
    // methods for JarFileLoader
    public void addClassProperty(ClassProperty classProperty)
    {
        //classProperties.add(classProperty);
//        classProperties = classProperty;
    }
    
    public ClassProperty getClassProperty(String className)
    {
        // HACK!
        for (int i = 0; i < classProperties.length; i++)
        {
            ClassProperty c = classProperties[i];
            if (c.getName().equals(className))
            {
                return c;   
            }
        }
        return null;
    }
}
