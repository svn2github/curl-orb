//Copyright (C) 1998-2008, Sumisho Computer Systems Corp. All Rights Reserved.

//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at

//http://www.apache.org/licenses/LICENSE-2.0

//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.curlap.orb.type;

import com.curl.io.serialize.SerializeException;

/**
 * Proxy class of java.math.BigDecimal to serializer and deserialize.
 * 
 * @author Ucai Zhang
 * @since 0.6
 */
public class BigDecimal extends AbstractSerializableProxyType {

	private static final long serialVersionUID = 1L;

	/**
	 * A string to contain the the BigDecimal's value
	 */
	private String strValue = "";

	@Override
	public Object extractProperObject() throws SerializeException {
		return new java.math.BigDecimal(strValue);
	}

	@Override
	public void injectProperObject(Object obj) throws SerializeException {
		java.math.BigDecimal bigDecimal = (java.math.BigDecimal)obj;		
		strValue = bigDecimal.toPlainString();
	}
}
