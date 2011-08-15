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

import java.util.Arrays;

/**
 * Request to invoke the instance in HttpSession.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class InvokeHttpSessionRequest extends InstanceManagementRequest
{
	private static final long serialVersionUID = 1L;

	private String objectId;
	private String className;
	private String methodName;
	private Object arguments[];

	/* (non-Javadoc) */
	public InvokeHttpSessionRequest()
	{
		super();
		objectId = null;
		className = null;
		methodName = null;
		arguments = null;
	}

	/* (non-Javadoc)
	 * @see com.curlap.orb.common.InstanceManagementRequest#getClassName()
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
	public void setObjectId(String objectId)
	{
		this.objectId = objectId;
	}

	/* (non-Javadoc) */
	public String getObjectId()
	{
		return objectId;
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
		"[Header:" + getHeader() + " ]" +
		"[Object ID:" + objectId + " ]" + 
		"[Class name:" + className + " ]" + 
		"[Method name:" + methodName + " ]" +
		"[Arguments:" + Arrays.toString(arguments) + "]";
	}
}
