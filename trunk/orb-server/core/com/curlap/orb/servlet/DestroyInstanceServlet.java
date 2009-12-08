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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.curlap.orb.common.DestroyInstanceRequest;
import com.curlap.orb.common.InstanceManagementException;
import com.curlap.orb.security.RemoteServiceAnnotationChecker;

/**
 * Servlet to destroy the object in HttpSession.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class DestroyInstanceServlet extends InstanceManagementServlet
{
	private static final long serialVersionUID = 1L;
	
	// reserved strings
    private static final String KILL_SESSION = "com.curlap.orb.internal-kill-session";
    
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
        DestroyInstanceRequest destroyInstanceRequest = 
        	(DestroyInstanceRequest) InstanceManagementUtil.getRequest(request);
        try
        {
            HttpSession session = request.getSession(false);
			if (session == null)
				throw new InstanceManagementException("Does not exist HttpSession.");
            String objectId = destroyInstanceRequest.getObjectId();
            Object obj = session.getAttribute(objectId);
            // security
            RemoteServiceAnnotationChecker.check(obj.getClass(), environment);
            // remove the object from session
            session.removeAttribute(objectId);
            
            // kill session
            if (destroyInstanceRequest.getHeader() != null && destroyInstanceRequest.getHeader().containsKey(KILL_SESSION) && 
            		(Boolean)destroyInstanceRequest.getHeader().get(KILL_SESSION))
            {
            	log.debug("Killed HttpSession:" + session.getId());
            	session.invalidate();
            }
            InstanceManagementUtil.setResponse(request, null, null);
            log.debug("Request destroyed");
        }
        // IOException, SerializerException, InstanceManagementException
        catch (Exception e)
        {
        	InstanceManagementUtil.setResponse(request, e, null);
        }
    }
}
