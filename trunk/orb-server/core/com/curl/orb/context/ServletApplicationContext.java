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

import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletContext;

/**
 * Application context to integrate ServletContext. 
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class ServletApplicationContext extends AbstractApplicationContext 
{
	private ServletContext context;

    /**
     * Create new ServletApplicationContext
     * 
     * @param context ServletContext
     * @throws ApplicationContextException
     */
	public ServletApplicationContext(ServletContext context)
	{
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * @see com.curlap.orb.context.AbstractApplicationContext#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String name) throws ApplicationContextException
	{
		return context.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.curlap.orb.context.AbstractApplicationContext#setObject(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setObject(String name, Object value) throws ApplicationContextException
	{
		context.setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.curlap.orb.context.AbstractApplicationContext#getObjectNames()
	 */
	@Override
	public String[] getObjectNames() throws ApplicationContextException
	{
		@SuppressWarnings("unchecked")
		Enumeration<String> names = context.getAttributeNames();
		Vector<String> vector = new Vector<String>();
		while (names.hasMoreElements())
			vector.addElement((String)names.nextElement());
		String[] array = new String[vector.size()];
		vector.copyInto(array);
		return array;
	}

	/*
	 * (non-Javadoc)
	 * @see com.curlap.orb.context.AbstractApplicationContext#getObjectType(java.lang.String)
	 */
	@Override
	public Class<?> getObjectType(String name) throws ApplicationContextException
	{
		return context.getAttribute(name).getClass();
	}
}
