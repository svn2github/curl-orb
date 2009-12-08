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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Loader of the jar file to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class JarFileLoader
{
	/**
	 * Create new JarFileLoader.
	 * 
	 * @param jarFilePath the jar file path
	 * @throws GeneratorException
	 */
	public static List<ClassProperty> loadJarFileProperty(String jarFilePath) throws GeneratorException
	{
		List<ClassProperty> classProperties = new ArrayList<ClassProperty>();
		try 
		{
			for (Enumeration<JarEntry> enumeration = (new JarFile(new File(jarFilePath))).entries(); enumeration.hasMoreElements();) 
			{
				ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
				if (!zipentry.isDirectory() && !zipentry.getName().startsWith(("META-INF")))
				{
					String classPath = zipentry.getName();
					if (classPath.endsWith(".class"))
					{
						String className = classPath.substring(0, classPath.lastIndexOf('.')).replace('/', '.');
						ClassProperty classProperty = new ClassProperty(className);
						if (classProperty.isPublic())
							classProperties.add(classProperty); // if not public, it will be skipped.
					}
				}
			}
			return classProperties;
		}
		catch(IOException e)
		{
			throw new GeneratorException(e);
		}
	}
}
