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

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import com.curl.io.serialize.SerializeException;

import curl.language.containers.ByteArray;

/**
 * Proxy class of java.sql.Blob to serializer and deserialize.
 * 
 * @author Ucai Zhang
 * @since 0.6
 */
public class Blob extends AbstractSerializableProxyType {

	private static final long serialVersionUID = 1L;

	/**	 
     * A serialized array of uninterpreted bytes representing the
     * value of this Blob object.
     */
	private ByteArray bytes = null;

	@Override
	public Object extractProperObject() throws SerializeException {
		try {
			return new SerialBlob(bytes.getRawBytes());
		} catch (SQLException se) {
			throw new SerializeException(se);
		}
	}

	@Override
	public void injectProperObject(Object obj) throws SerializeException {
		java.sql.Blob blob = (java.sql.Blob)obj; 
		try {
			bytes = new ByteArray(blob.getBytes(1, (int)blob.length()));
		} catch (SQLException se) {
			throw new SerializeException(se);
		}
	}
}
