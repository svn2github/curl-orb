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

/**
 * Request to destroy the instance.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class DestroyInstanceRequest extends InstanceManagementRequest
{
	private static final long serialVersionUID = 1L;

	private String objectId;

	/**
	 * Create new DestroyInstanceRequest.
	 */
	public DestroyInstanceRequest()
	{
		objectId = null;
	}

	/* (non-Javadoc)
	 * @see com.curl.orb.common.InstanceManagementRequest#getClassName()
	 */
	@Override
	public String getClassName()
	{
		return null;
	}

	/* (non-Javadoc) */
	public void setClassName(String s)
	{ // do nothing
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return 
		"[Header:" + getHeader() + "] " +
		"[Object ID:" + objectId + "]";
	}
}
