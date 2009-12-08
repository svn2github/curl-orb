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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.curlap.orb.common.InstanceManagementException;
import com.curlap.orb.common.InvokeApplicationContextRequest;
import com.curlap.orb.context.AbstractApplicationContext;
import com.curlap.orb.context.ApplicationContextFactory;
import com.curlap.orb.security.RemoteServiceAnnotationChecker;

/**
 * Servlet to invoke the object in application context
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class InvokeApplicationContextServlet extends InstanceManagementServlet
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
        InvokeApplicationContextRequest invokeRequest = 
        	(InvokeApplicationContextRequest) InstanceManagementUtil.getRequest(request);
        try
        {
            String className = invokeRequest.getClassName(); // context's object name
            String methodName = invokeRequest.getMethodName();
            Object[] arguments = invokeRequest.getArguments();
            
            AbstractApplicationContext applicationContext = 
            	ApplicationContextFactory.getInstance(getServletContext()).getApplicationContext();
            Object obj = applicationContext.getObject(className);
            if(obj == null)
                throw new InstanceManagementException("This object does not exsit [" + className + "]");
            // security
            RemoteServiceAnnotationChecker.check(applicationContext.getProperType(obj), environment);
            Method method = InstanceManagementUtil.getMethod(obj, methodName, arguments);
            InstanceManagementUtil.setResponse(
            		request,
            		InstanceManagementUtil.invokeMethod(method, obj, arguments),
            		InstanceManagementUtil.getSurborinateObject(method)
            );
            // debug
            log.debug("Request invoke method(DI Container)");
        }
        // IOException, SerializerException, ApplicationContextException, InstanceManagementException ...
        catch(Exception e)
        {
            InstanceManagementUtil.setResponse(request, e, null);
        }
    }
}
