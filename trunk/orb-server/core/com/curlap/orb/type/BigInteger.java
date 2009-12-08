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

package com.curlap.orb.type;

import com.curl.io.serialize.SerializeException;

/**
 * Proxy class of java.math.BigInteger to serializer and deserialize.
 * 
 * @author Ucai Zhang
 * @since 0.6
 */
public class BigInteger extends AbstractSerializableProxyType
{
	private static final long serialVersionUID = 1L;

	/*
	 * A string to contain the the BigInteger's value
	 */
	private String strValue;

	@Override
	public Object extractProperObject() throws SerializeException {		
		return new java.math.BigInteger(strValue);
	}

	@Override
	public void injectProperObject(/* java.math.BigInteger */Object obj) throws SerializeException {
		java.math.BigInteger bigInteger = (java.math.BigInteger)obj;
		strValue = bigInteger.toString();
	}
}
