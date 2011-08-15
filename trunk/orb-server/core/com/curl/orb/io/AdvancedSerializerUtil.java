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

package com.curlap.orb.io;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.curl.io.serialize.SerializeException;
import com.curlap.orb.type.AbstractSerializableProxyType;
import com.curlap.orb.type.CDate;
import com.curlap.orb.type.CTime;
import com.curlap.orb.type.CTimestamp;

/**
 * The utility for AdvancedSerializerXX
 *
 * @author Zhang Ucai
 * 
 */
public class AdvancedSerializerUtil {

	static Map<String, String> convertedTypes = new HashMap<String, String>();
	static {
		convertedTypes.put("java.sql.Date;", "com.curlap.orb.type.CDate;");
		convertedTypes.put("java.util.Date;", "com.curlap.orb.type.Date;");
		convertedTypes.put("java.sql.Time;", "com.curlap.orb.type.CTime;");
		convertedTypes.put("java.sql.Timestamp;", "com.curlap.orb.type.CTimestamp;");
		convertedTypes.put("java.math.BigInteger;", "com.curlap.orb.type.BigInteger;");
		convertedTypes.put("java.math.BigDecimal;", "com.curlap.orb.type.BigDecimal;");
		convertedTypes.put("java.sql.Blob;", "com.curlap.orb.type.Blob;");
		convertedTypes.put("javax.sql.rowset.serial.SerialBlob;", "com.curlap.orb.type.Blob;");
		convertedTypes.put("java.sql.Clob;", "com.curlap.orb.type.Clob;");
		convertedTypes.put("javax.sql.rowset.serial.SerialClob;", "com.curlap.orb.type.Clob;");
	}
	
	static Object writeCommon(Object val) throws SerializeException, IOException {	
		if (val instanceof Time || val.equals(Time.class)){
			if (val.equals(Time.class))
				return CTime.class;
			else {
				AbstractSerializableProxyType time = new CTime();
				time.injectProperObject(val);
				return time;
			}
		} else if (val instanceof Timestamp || val.equals(Timestamp.class)) {
			if (val.equals(Timestamp.class))
				return CTimestamp.class;
			else {
				AbstractSerializableProxyType timestamp = new CTimestamp();
				timestamp.injectProperObject(val);
				return timestamp;
			}
		} else if (val instanceof java.sql.Date || val.equals(java.sql.Date.class)) {
			if (val.equals(java.sql.Date.class))
				return com.curlap.orb.type.CDate.class;
			else {
				AbstractSerializableProxyType date = new CDate();
				date.injectProperObject(val);
				return date;
			}
		} else if (val instanceof java.util.Date || val.equals(java.util.Date.class)) {
			if (val.equals(java.util.Date.class))
				return com.curlap.orb.type.Date.class;
			else {
				AbstractSerializableProxyType date = new com.curlap.orb.type.Date();
				date.injectProperObject(val);
				return date;
			}
		} else if (val instanceof java.math.BigInteger || val.equals(java.math.BigInteger.class)) {
			if (val.equals(java.math.BigInteger.class))
				return com.curlap.orb.type.BigInteger.class;
			else {
				AbstractSerializableProxyType bigInt = new com.curlap.orb.type.BigInteger();
				bigInt.injectProperObject(val);
				return bigInt;
			}
		} else if (val instanceof java.math.BigDecimal || val.equals(java.math.BigDecimal.class)) {
			if (val.equals(java.math.BigDecimal.class))
				return com.curlap.orb.type.BigDecimal.class;
			else {
				AbstractSerializableProxyType bigDec = new com.curlap.orb.type.BigDecimal();
				bigDec.injectProperObject(val);
				return bigDec;
			}
		} else if (val instanceof java.sql.Blob || val.equals(java.sql.Blob.class)) {
			if (val.equals(java.sql.Blob.class))
				return com.curlap.orb.type.Blob.class;
			else {
				AbstractSerializableProxyType blob = new com.curlap.orb.type.Blob();
				blob.injectProperObject(val);
				return blob;
			}
		} else if (val instanceof java.sql.Clob || val.equals(java.sql.Clob.class)) {
			if (val.equals(java.sql.Clob.class))
				return com.curlap.orb.type.Clob.class;
			else {
				AbstractSerializableProxyType clob = new com.curlap.orb.type.Clob();
				clob.injectProperObject(val);
				return clob;
			}
		}
		return val;
	}

	static Object writeArray(Object val) throws SerializeException, IOException {
		// If the array is the array of java.sql.Date, java.sql.Time, java.sql.Timestamp
		// java.math.BigInteger or java.math.BigDecimal
		String componentName = val.getClass().getName();
		for (Map.Entry<String, String> e : convertedTypes.entrySet()) {
			if (componentName.endsWith(e.getKey()))
			{
				return convertToComplexTypeArray(val);
			}
		}
		return val;
	}

	// e.g) [Ljava.sql.Date --> [Lcom.curlap.orb.type.CDate
	private static Class<?> convertTypeName(String src, String s1, String s2) throws SerializeException {
		try {
			String result = src.substring(0, src.indexOf(s1)) + s2;
			return Class.forName(result);
		} catch (Exception e) {
			throw new SerializeException(e.getMessage());
		}
	}

	private static Class<?> getArrayType(Class<?> clazz) throws SerializeException {
		String name = clazz.getName();
		for (Map.Entry<String, String> e : convertedTypes.entrySet()) {
			String srcType = e.getKey();
			String destType = e.getValue();
			if (name.endsWith(srcType)) {
				return convertTypeName(name, srcType, destType);
			}
		}
		return clazz;
	}
	
	// convert the java.sql.* or java.math.* type array to com.curlap.orb.type.* type array.
	private static Object convertToComplexTypeArray(Object val) throws SerializeException {
		if (val instanceof java.sql.Date) {
			AbstractSerializableProxyType date = new com.curlap.orb.type.CDate();
			date.injectProperObject(val);
			return date;			
		}
		if (val instanceof java.util.Date && val.getClass().equals(java.util.Date.class)) {
			AbstractSerializableProxyType date = new com.curlap.orb.type.Date();
			date.injectProperObject(val);
			return date;
		} else if (val instanceof java.sql.Time) {
			AbstractSerializableProxyType time = new com.curlap.orb.type.CTime();
			time.injectProperObject(val);
			return time;
		} else if (val instanceof java.sql.Timestamp) {
			AbstractSerializableProxyType timestamp = new com.curlap.orb.type.CTimestamp();
			timestamp.injectProperObject(val);
			return timestamp;
		} else if (val instanceof java.math.BigInteger) {
			AbstractSerializableProxyType bigInt = new com.curlap.orb.type.BigInteger();
			bigInt.injectProperObject(val);
			return bigInt;
		} else if (val instanceof java.math.BigDecimal) {
			AbstractSerializableProxyType bigDec = new com.curlap.orb.type.BigDecimal();
			bigDec.injectProperObject(val);
			return bigDec;
		} else if (val instanceof java.sql.Blob) {
			AbstractSerializableProxyType blob = new com.curlap.orb.type.Blob();
			blob.injectProperObject(val);
			return blob;
		} else if (val instanceof java.sql.Clob) {
			AbstractSerializableProxyType clob = new com.curlap.orb.type.Clob();
			clob.injectProperObject(val);
			return clob;
		}
		// Create the com.curlap.orb.type.* type array.
		Object[] arrayVal = (Object[])val;
		Object returnArray = 
			Array.newInstance(getArrayType(val.getClass()).getComponentType(), arrayVal.length);
		for (int i = 0; i < arrayVal.length; i++) {
			Array.set(returnArray, i, convertToComplexTypeArray(arrayVal[i]));
		}
		return returnArray;
	}
}
