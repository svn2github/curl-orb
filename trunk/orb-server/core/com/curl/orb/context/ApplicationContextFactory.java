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

package com.curlap.orb.context;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import org.apache.commons.logging.LogFactory;

/**
 * ApplicationContext factory.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class ApplicationContextFactory 
{
	// reserved strings
	private final static String APPLICATION_CONTEXT_CLASS = "com.curlap.orb.applicationContextClass";
	
	private static ApplicationContextFactory instance = null;
	private AbstractApplicationContext applicationContext = null;

	protected ApplicationContextFactory(ServletContext context) throws ApplicationContextException
	{
		try {
			String applicationContextClassName = context.getInitParameter(APPLICATION_CONTEXT_CLASS);
			if (applicationContextClassName != null)
			{
				Class<?> applicationContextClass = Class.forName(applicationContextClassName);
				applicationContext = 
					(AbstractApplicationContext)(applicationContextClass.getDeclaredConstructor(ServletContext.class).newInstance(context));
			}
		} 
		catch (ClassNotFoundException e)
		{
			throw new ApplicationContextException(e);
		}
		catch (NoSuchMethodException e) 
		{
			throw new ApplicationContextException(e);
		} 
		catch (IllegalAccessException e) 
		{
			throw new ApplicationContextException(e);
		} 
		catch (InvocationTargetException e) 
		{
			throw new ApplicationContextException(e);
		}
		catch (InstantiationException e)
		{
			throw new ApplicationContextException(e);
		}
		if (applicationContext == null)
			applicationContext = new ServletApplicationContext(context);
	}

	/**
	 * Create new ApplicationContextFactory instance or get existed one if it exists.
	 *
	 * @param context ServletContext
	 * @return ApplicationContextFactory instance
	 */
	public static ApplicationContextFactory getInstance(ServletContext context)
	{
		if (ApplicationContextFactory.instance == null) 
		{
			try 
			{
				ApplicationContextFactory.instance = new ApplicationContextFactory(context);
			}
			catch (ApplicationContextException e) 
			{
				// fatal log
				(LogFactory.getLog(ApplicationContextFactory.class)).fatal(e.getMessage(), e);                
			}
		}
		return ApplicationContextFactory.instance;
	}

	/**
	 * Get the application context
	 * 
	 * @return the application context
	 */
	public AbstractApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
}
