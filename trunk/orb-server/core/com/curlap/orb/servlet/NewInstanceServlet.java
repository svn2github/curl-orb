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

package com.curlap.orb.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;

import com.curlap.orb.common.NewInstanceRequest;
import com.curlap.orb.security.RemoteServiceAnnotationChecker;

/**
 * Servlet to create new instance to HttpSession
 */
public class NewInstanceServlet extends InstanceManagementServlet
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
        NewInstanceRequest newInstanceRequest = 
        	(NewInstanceRequest) InstanceManagementUtil.getRequest(request);
        try
        {
            String className = newInstanceRequest.getClassName();
            Class<?> cls = Class.forName(className);
            // security
            RemoteServiceAnnotationChecker.check(cls, environment);
            // new instance
    		HttpSession session = request.getSession(false);
    		if(session == null)
    			session = request.getSession(true);
            Object obj = 
            	InstanceManagementUtil.newInstance(cls, switchRemoteObject(newInstanceRequest.getArguments(), session));
            
            // NOTE: objectId is HttpSession.getId() + Object.hashCode()
            String objectId = session.getId() + (new StringBuilder(String.valueOf(obj.hashCode()))).toString();
            session.setAttribute(objectId, obj);
            InstanceManagementUtil.setResponse(request, objectId, null);
            // debug
            (LogFactory.getLog(getClass())).debug("Request new instance");
        }
        // IOException, SerializerException ...etc
        catch(Exception e)
        {
            InstanceManagementUtil.setResponse(request, e, null);
        }
    }
}
