// Copyright (C) 1998-2009, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curl.orb.context;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.MethodUtils;

/**
 * Application context to integrate with spring framework. (http://www.springframework.org/)
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class Spring2_5ApplicationContext extends AbstractApplicationContext 
{
	// org.springframework.web.context.WebApplicationContext
	private Object context = null;

	/**
	 * Create new Spring2_5ApplicationContext
	 * 
	 * @param context ServletContext
	 * @throws ApplicationContextException
	 */
	public Spring2_5ApplicationContext(ServletContext context) throws ApplicationContextException
	{
		try 
		{
			// org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			Class<?> cls = Class.forName("org.springframework.web.context.support.WebApplicationContextUtils");
			this.context = MethodUtils.invokeStaticMethod(cls, "getRequiredWebApplicationContext", context);
		} 
		catch (ClassNotFoundException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (NoSuchMethodException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (IllegalAccessException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (InvocationTargetException ex) 
		{
			throw new ApplicationContextException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.curl.orb.context.AbstractApplicationContext#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String name) throws ApplicationContextException 
	{
		try 
		{
			// return context.getBean(name);
			return MethodUtils.invokeMethod(context, "getBean", new Object[]{ name });
		}
		catch (NoSuchMethodException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (IllegalAccessException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (InvocationTargetException ex) 
		{
			throw new ApplicationContextException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.curl.orb.context.AbstractApplicationContext#setObject(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setObject(String name, Object value) throws ApplicationContextException
	{
		// do nothing (should throw exception?)
	}

	/*
	 * (non-Javadoc)
	 * @see com.curl.orb.context.AbstractApplicationContext#getObjectNames()
	 */
	@Override
	public String[] getObjectNames() throws ApplicationContextException
	{
		try 
		{
			// return ((ApplicationContext)this.context).getBeanDefinitionNames();
			return (String[]) MethodUtils.invokeMethod(context, "getBeanDefinitionNames", null);
		} 
		catch (NoSuchMethodException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (IllegalAccessException ex) 
		{
			throw new ApplicationContextException(ex);
		} 
		catch (InvocationTargetException ex)
		{
			throw new ApplicationContextException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.curl.orb.context.AbstractApplicationContext#getObjectType(java.lang.String)
	 */
	@Override
	public Class<?> getObjectType(String name) throws ApplicationContextException
	{
		try 
		{
			// return ((ApplicationContext)this.context).getType();
			return (Class<?>) MethodUtils.invokeMethod(context, "getType", new String[]{ name });
		}
		catch (NoSuchMethodException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (IllegalAccessException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (InvocationTargetException ex) 
		{
			throw new ApplicationContextException(ex);
		}
	}

	/** 
	 * NOTE:
	 *  Spring framework would wrap org.springframework.aop.framework.Advised class as naming of "$ProxyNN"
	 *  in the case of implementing interface and session object.
	 *  The following method is to fix this spring framework issue.
	 */
	@Override
	public Class<?> getProperType(Object obj) throws ApplicationContextException
	{
		// if (obj instanceof Advised) return ((Advised) obj).getTargetSource().getTargetClass();
		// NOTE: Instead of above remarked codes. (No dependency code on spring framework)
		try 
		{
			Class<?> cls = Class.forName("org.springframework.aop.framework.Advised");
			if (cls.isAssignableFrom(obj.getClass()))
			{
				Object targetSource = MethodUtils.invokeMethod(obj, "getTargetSource", null);
				return (Class<?>) MethodUtils.invokeMethod(targetSource, "getTargetClass", null);
			}
		}
		catch (ClassNotFoundException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (NoSuchMethodException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (IllegalAccessException ex)
		{
			throw new ApplicationContextException(ex);
		} 
		catch (InvocationTargetException ex) 
		{
			throw new ApplicationContextException(ex);
		}
		return super.getProperType(obj);
	}
}
