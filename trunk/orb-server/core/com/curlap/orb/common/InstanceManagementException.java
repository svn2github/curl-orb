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

package com.curlap.orb.common;

/**
 * Exception thrown during instance management.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class InstanceManagementException extends Exception
{
	private static final long serialVersionUID = 1L;

    /**
     * Create a new InstanceManagementException. 
     */
	public InstanceManagementException()
    {
        super();
    }

    /**
     * Create a new InstanceManagementException with the specified detail message. 
     * 
     * @param message the detail message
     */
    public InstanceManagementException(String message)
    {
        super(message);
    }

    /**
     * Create a new InstanceManagementException with the specified detail message and the given root cause. 
     * 
     * @param message the detail message
     * @param rootCause the root cause
     */
    public InstanceManagementException(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }

    /**
     * Create a new InstanceManagementException with and the given root cause. 
     * 
     * @param rootCause the root cause
     */
    public InstanceManagementException(Throwable rootCause)
    {
        super(rootCause);
    }
}
