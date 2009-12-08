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

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialClob;

import com.curl.io.serialize.SerializeException;

/**
 * Proxy class of java.sql.Clob to serializer and deserialize.
 * 
 * @author Ucai Zhang
 * @since 0.6
 */
public class Clob extends AbstractSerializableProxyType {

	private static final long serialVersionUID = 1L;

	/**
	 * A serialized array of characters containing the data of the SQL
	 * CLOB value that this Clob object
	 * represents.
	 *
	 */
	private char[] chars = null;

	@Override
	public Object extractProperObject() throws SerializeException{
		try {			
			return new SerialClob(chars);			
		} catch (SQLException se) {
			throw new SerializeException(se);
		}
	}

	@Override
	public void injectProperObject(Object obj) throws SerializeException {
		java.sql.Clob clob = (javax.sql.rowset.serial.SerialClob)obj;
		chars = (char[])getField(clob,"buf");
	}
}
