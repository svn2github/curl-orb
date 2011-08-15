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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Abstract class of Serializer
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public abstract class AbstractSerializer 
{
	/**
	 * Create new AbstractSerializer.
	 */
	public AbstractSerializer()
	{ // do nothing
	}

	/**
	 * Serialize to each serialized data.
	 * 
	 * @param obj the object to serialize
	 * @param ostream the OutputStream
	 * @throws SerializerException
	 */
	public abstract void serialize(
			Object obj, 
			Map<String, Object> options,
			OutputStream ostream
	) throws SerializerException;

	/**
	 * Serialize to each serialized data.
	 * 
	 * @param obj the object to serialize
	 * @param ostream the OutputStream
	 * @param closeRawStream whether close ostream or not.
	 * @throws SerializerException
	 */
	public abstract void serialize(
			Object obj, 
			Map<String, Object> options,
			OutputStream ostream, 
			boolean closeRawStream
	) throws SerializerException;

	/**
	 * Deserialize to java object
	 * 
	 * @param istream the InputStream
	 * @return the deserialized object
	 * @throws SerializerException
	 */
	public abstract Object deserialize(
			InputStream istream
	) throws SerializerException;
}
