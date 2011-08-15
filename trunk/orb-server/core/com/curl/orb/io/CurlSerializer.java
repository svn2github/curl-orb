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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Curl's Serializer.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class CurlSerializer extends AbstractSerializer
{
	private final Log log = LogFactory.getLog(getClass());

	/**
	 * Create new CurlSerializer.
	 */
	public CurlSerializer()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see com.curl.orb.io.AbstractSerializer#serialize(java.lang.Object, java.io.OutputStream)
	 */
	@Override
	public void serialize(
			Object obj, 
			Map<String, Object> options,
			OutputStream ostream
	) throws SerializerException 
	{
		serialize(obj, options, ostream, true);
	}

	public void serialize(
			Object obj, 
			Map<String, Object> options,
			OutputStream ostream, 
			boolean closeRawStream
	) throws SerializerException 
	{
		SerializableStreamWriter writer = null;
		try
		{
			// extract doNotShare.
			final String dns = "do-not-share"; 
			boolean doNotShare =
				(Boolean) (options != null && options.containsKey(dns) ? options.get(dns) : false);
			writer = new CurlSerializableStreamWriter(ostream, doNotShare);
			writer.write(obj);
			writer.flush();
			log.debug("The serialized object is " + obj);
		}
		finally
		{
			if (writer != null && closeRawStream)
				writer.close();
		}
	}

	/* (non-Javadoc)
	 * @see com.curl.orb.io.AbstractSerializer#deserialize(java.io.InputStream)
	 */
	@Override
	public Object deserialize(InputStream istream) throws SerializerException
	{
		SerializableStreamReader reader = null;
		Object obj = null;
		try
		{
			reader = new CurlSerializableStreamReader(istream);
			obj = reader.read();
			log.debug("The deserialized object is " + obj);
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		return obj;
	}
}
