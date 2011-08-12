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

package com.curlap.orb.type;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import curl.language.containers.ByteArray;

/**
 * A serializable binary file class to download and upload.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class SerializableBinaryFile extends AbstractSerializableFile implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

    // content
    private ByteArray bytes;

    // constructors
    public SerializableBinaryFile()
    {
        super();
        // TODO:
        // bytes = new ByteArray();
    }

    public SerializableBinaryFile(String url) throws DataTypeException
    {
        super();
        fileDescriptor.put(RESERVED_KEY_NAME, url);
        try 
        {
            // TODO: performance turning
            List<Byte> bytes = new ArrayList<Byte>();
            FileInputStream stream = new FileInputStream(new File(url));
            int b;
            while((b = stream.read()) != -1)
                bytes.add((byte)b);
            stream.close();
            this.bytes = new ByteArray(bytes);
            fileDescriptor.put(RESERVED_KEY_SIZE, bytes.size());
        }
        catch (FileNotFoundException e)
        {
            throw new DataTypeException(e);
        }
        catch (IOException e)
        {
            throw new DataTypeException(e);
        }
    }

    @Override
    public Object getContent() 
    {
        return bytes;
    }

    @Override
    public void setContent(Object content) 
    {
        fileDescriptor.clear();
        bytes = (ByteArray)content;
        fileDescriptor.put(RESERVED_KEY_SIZE, bytes.getRawBytes().length);
    }
    
    @Override
    public void write(String url) throws DataTypeException
    {
        FileOutputStream stream = null;
        try
        {
            stream = new FileOutputStream(new File(url));
            stream.write(bytes.getRawBytes());
        }
        catch (FileNotFoundException e)
        {
            throw new DataTypeException(e);
        }
        catch (IOException e)
        {
            throw new DataTypeException(e);
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    throw new DataTypeException(e);
                }
            }
        }
    }
}
