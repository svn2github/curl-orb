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

package com.curlap.orb.io;

import java.io.IOException;
import java.io.OutputStream;

import com.curl.io.serialize.SerializeException;
import com.curl.io.serialize.Serializer6_0;

/**
 * The class extents Serializer6_0, it has been added functions to
 * deal with java.sql.Date, java.sql.Time, java.sql.Timestamp, java.math.BigInteger,
 * java.math.BigDecimal
 *
 * @author Ucai Zhang
 * @since 0.6
 */
public class AdvancedSerializer6_0 extends Serializer6_0 {

	/**
	 * Create AdvancedSerializer6_0 instance.
	 *
	 * @param outputStream the OutputStream
	 * @throws IOException
	 */
	public AdvancedSerializer6_0(
			OutputStream outputStream
	) throws IOException, SerializeException {
		super(outputStream);
	}

	/**
	 * Create AdvancedSerializer6_0 instance.
	 * 
	 * @param outputStream the OutputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @throws IOException
	 */
	public AdvancedSerializer6_0(
			OutputStream outputStream, 
			boolean isCurlStyle
	) throws IOException, SerializeException {
		super(outputStream, isCurlStyle);
	}

	/**
	 * Create AdvancedSerializer6_0 instance.
	 * 
	 * @param outputStream the OutputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @param isSerializableCheck check serializable or not.
	 * @throws IOException
	 */
	public AdvancedSerializer6_0(
			OutputStream outputStream, 
			boolean isCurlStyle, 
			boolean isSerializableCheck
	) throws IOException, SerializeException {
		super(outputStream, isCurlStyle, isSerializableCheck);
	}

	/**
	 * Create AdvancedSerializer6_0 instance.
	 * 
	 * @param outputStream the OutputStream
	 * @param isCurlStyle the curl style or not. (Curl style is to change the package name to upper case characters.)
	 * @param isSerializableCheck check serializable or not.
	 * @param doNotShare the reference feature, which is to compact the binary data, is unusable when doNotShare is true. 
	 * @throws IOException
	 */
	public AdvancedSerializer6_0(
			OutputStream outputStream,
			boolean isCurlStyle,
			boolean isSerializableCheck,
			boolean doNotShare
	) throws IOException, SerializeException {
		super(outputStream, isCurlStyle, isSerializableCheck, doNotShare);
	}

	
	/** protected and private methods **/

	/** override **/
	protected void writeCommon(Object val) throws SerializeException, IOException {	
		super.writeCommon(AdvancedSerializerUtil.writeCommon(val));
	}

	// First convert the java.sql.* type array or java.math.* type array to 
	// com.curlap.orb.type.* type array.then write the com.curlap.orb.type.* type 
	// array to outputstream
	protected void writeArray(Object val) throws SerializeException, IOException {
		super.writeArray(AdvancedSerializerUtil.writeArray(val));
	}
}
