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

package com.curlap.orb.servlet;

import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.curlap.orb.common.InstanceManagementException;
import com.curlap.orb.common.InvokeHttpSessionRequest;
import com.curlap.orb.security.RemoteServiceAnnotationChecker;

/**
 * Servlet to invoke the object in HttpSession
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class InvokeHttpSessionServlet extends InstanceManagementServlet
{
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.curlap.orb.servlet.InstanceManagementServlet#init()
	 */
	@Override 
	public void init() 
	{
		super.init();
	}
	
	/* (non-Javadoc)
	 * @see com.curlap.orb.servlet.InstanceManagementServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		super.doPost(request, response);
		Log log = LogFactory.getLog(getClass());
		InvokeHttpSessionRequest invokeRequest = 
			(InvokeHttpSessionRequest) InstanceManagementUtil.getRequest(request);
		try
		{
			String methodName = invokeRequest.getMethodName();
			Object[] arguments = invokeRequest.getArguments();
			Object returnValue = null;
			Method method = null;
			HttpSession session = request.getSession(false);
			if(invokeRequest.getObjectId() == null)
			{
				// static method
				if(invokeRequest.getClass() == null)
					throw new InstanceManagementException("Does not exist class name and object id.");
				String className = invokeRequest.getClassName();
				// security
				Class<?> cls = Class.forName(className);
				RemoteServiceAnnotationChecker.check(cls, environment);
				Object[] switchedArguments = switchRemoteObject(arguments, session);
				method = InstanceManagementUtil.getStaticMethod(cls, methodName, switchedArguments);
				returnValue = InstanceManagementUtil.invokeStaticMethod(method, cls, switchedArguments);
				// debug
				log.debug("Request invoke static method(HttpSession)");
			} 
			else
			{
				// non static method
				if (session == null)
					throw new InstanceManagementException("Does not exist HttpSession.");
				Object obj = session.getAttribute(invokeRequest.getObjectId());
				// security
				RemoteServiceAnnotationChecker.check(obj.getClass(), environment);
				Object[] switchedArguments = switchRemoteObject(arguments, session);
				method = InstanceManagementUtil.getMethod(obj, methodName, switchedArguments);
				returnValue = InstanceManagementUtil.invokeMethod(method, obj, switchedArguments);
				// debug
				log.debug("Request invoke method(HttpSession)");
			}
			InstanceManagementUtil.setResponse(
					request, 
					returnValue,
					InstanceManagementUtil.getSurborinateObject(method)
			);
		}
		// IOException, SerializerException, InstanceManagementException ...etc
		catch (Exception e)
		{
			InstanceManagementUtil.setResponse(request, e, null);
		}
	}
}
