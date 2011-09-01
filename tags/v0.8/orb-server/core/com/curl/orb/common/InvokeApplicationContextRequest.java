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

import java.util.Arrays;

/**
 * Request to invoke the instance in application context.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class InvokeApplicationContextRequest extends InstanceManagementRequest
{
	private static final long serialVersionUID = 1L;

	private String className;
	private String methodName;
	private Object arguments[];

	// constructors
	/* (non-Javadoc) */
	public InvokeApplicationContextRequest()
	{
		className = null;
		methodName = null;
		arguments = null;
	}

	/* (non-Javadoc)
	 * @see com.curl.orb.common.InstanceManagementRequest#getClassName()
	 */
	@Override
	public String getClassName()
	{
		return className;
	}

	/* (non-Javadoc) */
	public void setClassName(String className)
	{
		this.className = className;
	}

	/* (non-Javadoc) */
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	/* (non-Javadoc) */
	public String getMethodName()
	{
		return methodName;
	}

	/* (non-Javadoc) */
	public void setArguments(Object arguments[])
	{
		this.arguments = arguments;
	}

	/* (non-Javadoc) */
	public Object[] getArguments()
	{
		return arguments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return
		"[Header:" + getHeader() + "] " +
		"[Component name:" + className + "] " + 
		"[Method name:" + methodName + "] " +
		"[Arguments:" + Arrays.toString(arguments) + "]";
	}
}
