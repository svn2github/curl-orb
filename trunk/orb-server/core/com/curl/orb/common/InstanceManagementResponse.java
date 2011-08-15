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

package com.curl.orb.common;

import java.util.Map;

/**
 * Response to manage the instance.
 * 
 * @author Hitoshi Okada
 * @since 0.7
 */
public class InstanceManagementResponse implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	/* header */
	private Map<Object, Object> header;
	
	/* body */
	private Object body;
	
	/**
	 * Create new InstanceManagementResponse.
	 */
    public InstanceManagementResponse()
    {
    	// do nothing
    }
    
	/**
	 * Create new InstanceManagementResponse.
	 */
    public InstanceManagementResponse(Object body)
    {
    	this.body = body;
    }

    /**
     * Get headers of InstanceManagementResponse.
     * 
     * @return the header
     */
    public Map<Object, Object> getHeader()
    {
        return header;
    }
    
    /**
     * Set headers of InstanceManagementResponse.
     * 
     * @param header the header
     */
    public void setHeader(Map<Object, Object> header)
    {
    	this.header = header;
    }
    
    /**
     * Get body of InstanceManagementResponse.
     * 
     * @return the body
     */
    public Object getBody() 
    {
    	return body;
    }
    
    /**
     * Set body of InstanceManagementResponse.
     * 
     * @param body the body
     */
    public void setBody(Object body) 
    {
    	this.body = body;
    }
}
