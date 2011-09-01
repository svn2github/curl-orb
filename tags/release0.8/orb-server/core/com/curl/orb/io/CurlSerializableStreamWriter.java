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
import java.io.OutputStream;

import com.curl.io.serialize.SerializeException;
import com.curl.io.serialize.Serializer;

/**
 * The wrapper class of Curl's Serializer
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class CurlSerializableStreamWriter implements SerializableStreamWriter 
{
    private Serializer serializer = null;

    /**
     * Create new CurlSerializableStreamWriter.
     * 
     * @param outputStream OutputStream
     * @throws SerializerException
     */
    public CurlSerializableStreamWriter(
    		OutputStream outputStream,
    		boolean doNotShare
    ) throws SerializerException
    {
        try 
        {
            serializer = new AdvancedSerializer6_0(outputStream, true, false, doNotShare);
        }
        catch (Exception e)
        {
            throw new SerializerException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.curl.orb.io.SerializableStreamWriter#write(java.lang.Object)
     */
    public void write(Object obj) throws SerializerException
    {
        try 
        {
            serializer.write(obj);
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
     * @see com.curl.orb.io.SerializableStreamWriter#flush()
     */
    public void flush() throws SerializerException {
        try 
        {
        	serializer.flush();
        }
        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.curl.orb.io.SerializableStreamWriter#close()
     */
    public void close() throws SerializerException
    {
        try 
        {
        	serializer.close();
        }
        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }
}
