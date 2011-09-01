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

package com.curl.orb.io;

import java.io.IOException;
import java.io.InputStream;

import com.curl.io.serialize.Deserializer6_0;
import com.curl.io.serialize.SerializeException;

/**
 * The class extents Deserializer6_0,it have been added functions to
 * deal with java.sql.Date,java.sql.Time,java.sql.Timestamp,java.math.BigInteger,
 * java.math.BigDecimal
 *
 * @author Ucai Zhang
 * @since 0.6
 */
public class AdvancedDeserializer6_0 extends Deserializer6_0 {

	/** constructor **/

	/**
	 * 	Create the AdvancedDeserializer6_0 instance.
	 *
	 * @param inputStream the InputStream
	 * @throws IOException
	 * @throws SerializeException
	 */
	public AdvancedDeserializer6_0(
			InputStream inputStream
	) throws IOException,SerializeException {
		super(inputStream);
	}

	/**
	 * 	Create the AdvancedDeserializer6_0 instance.
	 * 
	 * @param inputStream the InputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @throws IOException
	 * @throws SerializeException
	 */
	public AdvancedDeserializer6_0(
			InputStream inputStream,
			boolean isCurlStyle
	) throws IOException, SerializeException {
		super(inputStream, isCurlStyle);
	}

	/**
	 * 	Create the AdvancedDeserializer6_0 instance.
	 * 
	 * @param inputStream the InputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @param cls the type of returned value 
	 * @throws IOException
	 * @throws SerializeException
	 */
	public AdvancedDeserializer6_0(
			InputStream inputStream,
			boolean isCurlStyle, Class<?> cls
	) throws IOException, SerializeException {
		super(inputStream, isCurlStyle, cls);
	}

	/**
	 * 	Create the AdvancedDeserializer6_0 instance.
	 * 
	 * @param inputStream the InputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @param cls the type of returned value 
	 * @param isSerializableCheck check serializable or not.
	 * @throws IOException
	 * @throws SerializeException
	 */
	public AdvancedDeserializer6_0(
			InputStream inputStream, 
			boolean isCurlStyle, 
			Class<?> cls,
			boolean isSerializableCheck
	) throws IOException, SerializeException {
		super(inputStream, isCurlStyle, cls, isSerializableCheck);
	}


	/** protected and private methods **/

	/** override **/
	// Overwrite the method readIntance of Deserializer6_0
	// this method deal with the complex type such as Date,Time,Timestamp,BigInteger and BigDecimal
	protected Object readIntance(Object val) throws IOException, SerializeException {
		return AdvancedDeserializerUtil.readIntance(super.readInstance(val));
	}

	// Overwrite the method covertToArrayType of Deserializer6_0
	// convert com.curl.orb.type.* type to java.sql.* or java.math.* type.
	@Override
	protected Object covertToArrayType(Object componentType) throws SerializeException {
		//return super.covertToArrayType(AdvancedDeserializerUtil.covertToArrayType(componentType));
		return super.covertToArrayType(AdvancedDeserializerUtil.covertToArrayType(componentType));
	}

	// Overwrite the method readObject
	// if super method readObject() returns AbstractExtraSerializableType instance data type,
	// then it will convert the object to java.sql.* or java.math.* data type object.
	@Override
	protected Object readObject() throws IOException, SerializeException {
		return AdvancedDeserializerUtil.readObject(super.readObject());
	}
	
	@Override
	protected boolean isSkippedArrayWithoutElement(Class<?> componentType) {
		return (java.sql.Date.class.isAssignableFrom(componentType) || 
				java.util.Date.class.isAssignableFrom(componentType) || 
				java.sql.Timestamp.class.isAssignableFrom(componentType) || 
				java.sql.Time.class.isAssignableFrom(componentType) || 
				java.math.BigInteger.class.isAssignableFrom(componentType) || 
				java.math.BigDecimal.class.isAssignableFrom(componentType) || 
				java.sql.Clob.class.isAssignableFrom(componentType) || 
				java.sql.Blob.class.isAssignableFrom(componentType) || 
				super.isSkippedArrayWithoutElement(componentType));
	}
}
