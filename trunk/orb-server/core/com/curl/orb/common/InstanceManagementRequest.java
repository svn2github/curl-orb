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

package com.curl.orb.common;

import java.util.Map;

/**
 * Request to manage the instance.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public abstract class InstanceManagementRequest implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private Map<Object, Object> header;
    
	/**
	 * Create new InstanceManagementRequest.
	 */
    public InstanceManagementRequest()
    {
        //header = new HashMap(); 
    }

    /**
     * Get headers of InstanceManagementRequest.
     * 
     * @return the header
     */
    public Map<Object, Object> getHeader()
    {
        return header;
    }
    
    /**
     * Set headers of InstanceManagementRequest.
     * 
     * @param header the header
     */
    public void setHeader(Map<Object, Object> header)
    {
    	this.header = header;
    }
    
    /**
     * Get the class name.
     * 
     * @return the class name
     */
    public abstract String getClassName();
}
