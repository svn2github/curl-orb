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
import java.util.IdentityHashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

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
		} else if (val instanceof SerialBlob || val.equals(SerialBlob.class)) {
			if (val.equals(SerialBlob.class))
				return com.curlap.orb.type.Blob.class;
			else {
				AbstractSerializableProxyType blob = new com.curlap.orb.type.Blob();
				blob.injectProperObject(val);
				return blob;
			}
		} else if (val instanceof SerialClob || val.equals(SerialClob.class)) {
			if (val.equals(SerialClob.class))
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
		Class<?> componentType = val.getClass();	
		// if the array is the array of java.sql.Date ,java.sql.Time,java.sql.Timestamp
		// java.math.BigInteger or java.math.BigDecimal
		if (componentType.getName().endsWith("java.sql.Date;") ||
				componentType.getName().endsWith("java.util.Date;") ||
				componentType.getName().endsWith("java.sql.Time;") ||
				componentType.getName().endsWith("java.sql.Timestamp;") ||
				componentType.getName().endsWith("java.math.BigInteger;") ||
				componentType.getName().endsWith("java.math.BigDecimal;") ||
				componentType.getName().endsWith("javax.sql.rowset.serial.SerialBlob;") ||
				componentType.getName().endsWith("javax.sql.rowset.serial.SerialClob;")
		) {
			return convertToComplexTypeArray(val);
		}
		return val;
	}

	// convert the java.sql.* or java.math.* type array to com.curlap.orb.type.* type array.
	private static Object convertToComplexTypeArray(Object val) throws SerializeException {
		if (val instanceof java.sql.Date) {
			AbstractSerializableProxyType date = new CDate();
			date.injectProperObject(val);
			return date;			
		}
		if (val instanceof java.util.Date && val.getClass().equals(java.util.Date.class)) {
			AbstractSerializableProxyType date = new com.curlap.orb.type.Date();
			date.injectProperObject(val);
			return date;
		} else if (val instanceof java.sql.Time) {
			AbstractSerializableProxyType time = new CTime();
			time.injectProperObject(val);
			return time;
		} else if (val instanceof java.sql.Timestamp) {
			AbstractSerializableProxyType timestamp = new CTimestamp();
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
		} else if (val instanceof javax.sql.rowset.serial.SerialBlob) {
			AbstractSerializableProxyType blob = new com.curlap.orb.type.Blob();
			blob.injectProperObject(val);
			return blob;
		} else if (val instanceof javax.sql.rowset.serial.SerialClob) {
			AbstractSerializableProxyType clob = new com.curlap.orb.type.Clob();
			clob.injectProperObject(val);
			return clob;
		}
		Object[] objArray = (Object[])val;
		// this container contains the original object and the new converted object.
		Map<Object,Object> objMap = new IdentityHashMap<Object,Object>();
		Object[] convertArray = new Object[objArray.length];
		for (int i = 0; i < objArray.length; i++) {
			Object item = objArray[i];
			if (objMap.containsKey(item)) {
				convertArray[i] = objMap.get(item);
			} else {					
				convertArray[i] = convertToComplexTypeArray(item);
				objMap.put(item, convertArray[i]);
			}
		}
		// create the com.curlap.orb.type.* type array.
		Object returnArray = 
			Array.newInstance(convertArray[0].getClass(), convertArray.length);
		for (int i = 0; i < convertArray.length; i++) {
			Array.set(returnArray, i, convertArray[i]);
		}
		return returnArray;
	}
}
