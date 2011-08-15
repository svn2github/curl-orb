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

package com.curl.orb.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.curl.orb.common.InstanceManagementException;
import com.curl.orb.common.OnetimeTrue;
import com.curl.orb.common.RemoteObject;
import com.curl.orb.context.AbstractApplicationContext;
import com.curl.orb.context.ApplicationContextFactory;
import com.curl.orb.generator.ClassPathLoader;
import com.curl.orb.generator.GeneratorException;
import com.curl.orb.security.Environment;

/**
 * Abstract servlet to manage instance
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public abstract class InstanceManagementServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// reserved strings
	static final String REQUEST_OBJECT = "com.curl.orb.internal-request-object";
	static final String RESPONSE_OBJECT = "com.curl.orb.internal-response-object";
	static final String RESPONSE_SUBORDINATE_OBJECT = "com.curl.orb.internal-response-subordinate-object";
	static final String ENVIRONMENT = "com.curl.orb.environment";
	static final String GENERATOR_FILTER = "com.curl.orb.generator.filter";
	static final String GENERATOR_LOAD_FILETYPE = "com.curl.orb.generator.load-filetype";

	// enabled an access control
	static Environment environment = null; // none

	// lock object to call only once.
	private static OnetimeTrue initializedLock = OnetimeTrue.getInstance();

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init()
	{
		synchronized (initializedLock) 
		{
			if (!initializedLock.isFirstTime())
				return;
			Log log = LogFactory.getLog(getClass());
			
			// Set to environment
			String environment = getServletContext().getInitParameter(ENVIRONMENT);
			if (environment != null)
			{
				if (environment.equalsIgnoreCase(Environment.PRODUCTION.toString()))
					InstanceManagementServlet.environment = Environment.PRODUCTION;
				else if (environment.equalsIgnoreCase(Environment.TEST.toString()))	
					InstanceManagementServlet.environment = Environment.TEST;
				else if (environment.equalsIgnoreCase(Environment.DEVELOPMENT.toString()))
					InstanceManagementServlet.environment = Environment.DEVELOPMENT;
			}
			log.info("Environment [" + environment + "]");

			// Load ApplicationContext
			AbstractApplicationContext applicationContext = 
				(ApplicationContextFactory.getInstance(getServletContext())).getApplicationContext();
			log.info("Loaded ApplicationContext [" + applicationContext + "]");

			// Load jars/classes files
			try
			{
				if (!(Environment.TEST.contain(InstanceManagementServlet.environment)) ||
						InstanceManagementServlet.environment == null)
				{
					ClassPathLoader loader = 
						ClassPathLoader.getInstance(getServletContext().getInitParameter(GENERATOR_FILTER));
					String fileType = getServletContext().getInitParameter(GENERATOR_LOAD_FILETYPE);
					if (fileType == null || fileType.equalsIgnoreCase("both")) 
					{
						loader.addClassProperties(getServletContext().getRealPath("/WEB-INF/lib"));
						loader.addClassProperties(getServletContext().getRealPath("/WEB-INF/classes"));
					} 
					else if(fileType.equalsIgnoreCase("class"))
					{
						loader.addClassProperties(getServletContext().getRealPath("/WEB-INF/classes"));
					}
					else if (fileType.equalsIgnoreCase("jar"))
					{
						loader.addClassProperties(getServletContext().getRealPath("/WEB-INF/lib"));
					}
					// System.getProperty("java.class.path")
					log.info("Loaded java libraries to generate Curl code.");
				}
			}
			catch (GeneratorException e)
			{
				log.warn(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream; charset=\"UTF-8\"");
	}

	// NOTE: accept only POST request.
	//  @Override
	//  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	//  {
	//  doPost(request, response);
	//  }
	
	/*
	 * Switch RemoteObject to actual object
	 *   TODO support for RemoteObject[] (array) 
	 *        *But it might be slow, if it appears a lot of objects.
	 */
	static Object[] switchRemoteObject(Object[] args, HttpSession session) throws InstanceManagementException
	{
		if (args != null)
		{
			for (int i = 0; i < args.length; i++)
			{
				Object obj = args[i];
				if (obj instanceof RemoteObject)
				{
					if (session == null)
						throw new InstanceManagementException("Does not exist HttpSession.");
					args[i] = session.getAttribute(((RemoteObject)obj).getObjectId());
					if (args[i] == null)
						throw new InstanceManagementException("No RemoteObject in HttpSession [argument:" + i + "]");
				}
			}
		}
		return args;
	}
}
