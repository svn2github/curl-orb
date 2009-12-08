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
import java.io.InputStream;

import com.curl.io.serialize.Deserializer;
import com.curl.io.serialize.SerializeException;

/**
 * The wrapper class of Curl's deserializer.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class CurlSerializableStreamReader implements SerializableStreamReader 
{
    private Deserializer deserializer = null;

    /**
     * Create new CurlSerializableStreamReader.
     * 
     * @param inputStream InputStream
     * @throws SerializerException
     */
    public CurlSerializableStreamReader(InputStream inputStream) throws SerializerException
    {
        try 
        {
            deserializer = new AdvancedDeserializer6_0(inputStream, true, Object.class, false);
        }
        catch (SerializeException e)
        {
            throw new SerializerException(e);
        }
        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }
    
    /* (non-Javadoc)
     * @see com.curlap.orb.io.SerializableStreamReader#read()
     */
    public Object read() throws SerializerException 
    {
        try 
        {
            return deserializer.read();
        }
        catch (IOException e)
        {
            throw new SerializerException(e);
        }
        catch (SerializeException e)
        {
            throw new SerializerException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.curlap.orb.io.SerializableStreamReader#close()
     */
    public void close() throws SerializerException 
    {
        try 
        {
            deserializer.close();
        }
        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }
}
