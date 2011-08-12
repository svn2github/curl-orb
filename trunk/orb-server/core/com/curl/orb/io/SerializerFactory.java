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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//public class SerializerFactory 
//{
//	private static SerializerFactory instance;
//	
//	/** constructor **/
//	private SerializerFactory() { }
//	
//	/** singleton **/
//	public SerializerFactory getInstance()
//	{
//		if (instance == null)
//			instance = new SerializerFactory();
//		return instance;
//	}
//	
//	/** 
//	 * Get the Serializer of proper version.
//	 */
//	public Serializer getSerializer(int versionNumber, OutputStream stream, boolean isCurlStyle, boolean isSerializableCheck) throws IOException, SerializeException
//	{
//		switch (versionNumber) {
//		case SerializeProtocol.version_4_0:
//			return new Serializer4_0(stream, isCurlStyle, isSerializableCheck);
//		case SerializeProtocol.version_6_0:
//			return new Serializer6_0(stream, isCurlStyle, isSerializableCheck);
//		}
//		throw new SerializeException("Missing magic number for serialization");
//	}
//}

/**
 * Serializer factory. 
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class SerializerFactory 
{
	private static SerializerFactory instance = null;
	private AbstractSerializer serializer;

	private final Log log = LogFactory.getLog(getClass());

	// protected constructors
	protected SerializerFactory () 
	{
		serializer = new CurlSerializer();
		log.debug("Started Serializer.");
	}

	/**
	 * Create new SerializerFactory instance or get the instance if it exists.
	 * 
	 * @return SerializerFactory instance
	 */
	public static SerializerFactory getInstance() 
	{
		if (SerializerFactory.instance == null)
			SerializerFactory.instance = new SerializerFactory();
		return SerializerFactory.instance;
	}

	/**
	 * Get Serializer.
	 * 
	 * @return AbstractSerializer
	 */
	public AbstractSerializer getSerializer()
	{
		return serializer;
	}
}
