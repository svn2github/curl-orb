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
 * RemoteObject
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class RemoteObject implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String objectId;

	/* (non-Javadoc) */
	public String getObjectId() {
		return objectId;
	}

	/* (non-Javadoc) */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
