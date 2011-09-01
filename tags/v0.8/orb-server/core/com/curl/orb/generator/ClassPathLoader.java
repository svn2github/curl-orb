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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Load classes from the following directory on web content. ClassPathLoader is used to generate Curl codes.<p>
 *  /WEB-INF/lib/*.jar <p>
 *  /WEB-INF/classes/*.class <p>
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class ClassPathLoader 
{
	private static ClassPathLoader instance;
	private List<ClassProperty> classProperties;
	private String paths = null;
	
	private final Log log = LogFactory.getLog(getClass());
	
	protected ClassPathLoader(String paths)
	{
		classProperties = new ArrayList<ClassProperty>();
		this.paths = (paths != null ? paths : "");
	}

	/**
	 * Create new ClassPathLoader instance.
	 * 
	 * @return the ClassPathLoader.
	 */
	public static ClassPathLoader getInstance(String paths)
	{
		if (instance == null)
			instance = new ClassPathLoader(paths);
		return instance;
	}

	/**
	 * Search .jar and .class files in the real path and add packages and classes to PackagePropertys.
	 * 
	 * @param realPath the directory of WEB-INF/lib, WEB-INF/classes and classpath env.
	 */
	public void addClassProperties(String realPath) throws GeneratorException
	{
		int rootPathLength = realPath.length();
		searchPath(new File(realPath), rootPathLength);
	}

	/**
	 * Get ClassPropertys.
	 * 
	 * @return ClassPropertys
	 */
	public ClassProperty[] getClassProperties() 
	{
		List<ClassProperty> classPropertiesCopy = new ArrayList<ClassProperty>();
		for (ClassProperty classProperty : classProperties)
		{
			// TODO: make new ClassLoaderFilter interface(?)
			String name = classProperty.getName();
			if (!(name.startsWith("com.curl.orb.")
					|| name.startsWith("com.curl.io.serialize.")
					|| name.startsWith("java.")
					|| name.startsWith("javax.")
					|| filter(name)))
			{
				classPropertiesCopy.add(classProperty);
			}

		}
		return classPropertiesCopy.toArray(new ClassProperty[classPropertiesCopy.size()]);
	}
	

	// - - private - -
	
	private boolean filter(String name) {
		StringTokenizer paths = new StringTokenizer(this.paths, ",");
		while (paths.hasMoreTokens())
		{
			String to = paths.nextToken().trim();
			if (name.startsWith(to))
				return true;
		}
		return false;
	}
	
	private void searchPath(File dir, int rootPathLength) throws GeneratorException 
	{
		File[] files = dir.listFiles();
		for (File file : files) 
		{
			if (file.isDirectory()) 
			{
				searchPath(file, rootPathLength);
			} 
			else if (file.isFile())
			{
				String filename = null;
				try 
				{
					filename = file.getCanonicalPath();
					if (filename.endsWith(".jar"))
					{
						if (!(filename.endsWith("curl-orb-server.jar") || 
								filename.endsWith("curl-serializer.jar")))
							classProperties.addAll(JarFileLoader.loadJarFileProperty(filename));
					}
					else if (filename.endsWith(".class"))
					{
						// Windows and UNIX
						String className = 
							filename.substring(rootPathLength + 1, filename.length() - 6).replace('\\', '.').replace('/', '.'); 
						ClassProperty classProperty = new ClassProperty(className);
						if (classProperty.isPublic())
							classProperties.add(classProperty); // if not public, it will be skipped.
					}
				}
				catch (IOException e)
				{
					throw new GeneratorException(e);
				}
				/* skiped Exception */
				catch (NoClassDefFoundError skip)
				{
					log.warn(filename + " was skiped due to " + 
							skip.getClass().getName() + " " + skip.getMessage());
				}
				catch (UnsatisfiedLinkError skip) 
				{
					log.warn(filename + " was skiped due to " + 
							skip.getClass().getName() + " " + skip.getMessage());
				}
				// TODO: all exceptions should be skipped?
				catch (Throwable skip)  
				{
					log.error(filename + " was skiped due to " + 
							skip.getClass().getName() + " " + skip.getMessage());
				}
			}
		}
	}
}
