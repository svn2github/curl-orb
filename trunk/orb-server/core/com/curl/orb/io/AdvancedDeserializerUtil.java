// Copyright (C) 1998-2009, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curl.orb.io;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import com.curl.io.serialize.SerializeException;
import com.curl.orb.type.AbstractSerializableProxyType;
import com.curl.orb.type.CDate;
import com.curl.orb.type.CTime;
import com.curl.orb.type.CTimestamp;

/**
 * The utility for AdvancedSerializerXX
 *
 * @author Zhang Ucai
 * 
 */
public class AdvancedDeserializerUtil {
	
	static Object readIntance(Object val) throws IOException, SerializeException {
		if (val instanceof AbstractSerializableProxyType) {
			return ((AbstractSerializableProxyType) val).extractProperObject();
		}
		return val;
	}

	// Overwrite the method covertToArrayType of Deserializer6_0
	// convert com.curl.orb.type.* type to java.sql.* or java.math.* type.
	static Object covertToArrayType(Object componentType) throws SerializeException {
		if (componentType.equals(CTime.class)) {
			return Time.class;
		} else if (componentType.equals(CTimestamp.class)) {
			return Timestamp.class;
		} else if (componentType.equals(CDate.class)) {
			return java.sql.Date.class;
		} else if (componentType.equals(com.curl.orb.type.Date.class)) {
			return java.util.Date.class;
		} else if (componentType.equals(com.curl.orb.type.BigInteger.class)) {
			return java.math.BigInteger.class;
		} else if (componentType.equals(com.curl.orb.type.BigDecimal.class)) {
			return java.math.BigDecimal.class;
		} else if (componentType.equals(com.curl.orb.type.Blob.class)) {
			return SerialBlob.class;
		} else if (componentType.equals(com.curl.orb.type.Clob.class)) {
			return SerialClob.class;
		}
		return componentType;		
	}

	// Overwrite the method readPackageLookup of Deserializer6_0
//	protected Object readPackageLookup() throws IOException, SerializeException {
//		Object packageObject = super.readPackageLookup();
//		// if pak is package, then it will look up the class in the package.
//		if (packageObject.getClass().getName().equals("java.lang.Package")) {
//			return readPackageClass((Package)packageObject);
//		}
//		return packageObject;
//	}

	static Object readObject(Object obj) throws IOException, SerializeException {
		if (obj instanceof AbstractSerializableProxyType) {
			return ((AbstractSerializableProxyType) obj).extractProperObject();
		}
		return obj;
	}
}
