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

package com.curl.orb.type;

import com.curl.io.serialize.SerializeException;

/**
 * Proxy class of java.sql.Timestamp to serializer and deserialize.
 * 
 * @author Ucai Zhang
 * @since 0.6
 */
public class CTimestamp extends Date {

	private static final long serialVersionUID = 1L;

	private int nanosValue;

	@Override
	public Object extractProperObject() throws SerializeException {	
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
		setField(timestamp, "nanos", nanosValue);		
		return timestamp;
	}

	@Override
	public void injectProperObject(Object obj) throws SerializeException {
		java.sql.Timestamp timestamp = (java.sql.Timestamp)obj;
		long tempTime  = timestamp.getTime();		 
		nanosValue = ((Integer)getField(timestamp, "nanos")).intValue();	
		time  = tempTime - nanosValue / 1000000;
	}
}
