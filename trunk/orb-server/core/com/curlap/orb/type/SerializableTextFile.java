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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A serializable text file class to download and upload.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class SerializableTextFile extends AbstractSerializableFile 
{
	private static final long serialVersionUID = 1L;

    private static transient String DEFAULT_CHARSET = "UTF8";
    private String text;
    
    /**
     * Create new instance.
     */
    public SerializableTextFile()
    {
        super();
    }

    /**
     * Create new instance with the url.
     * 
     * @param url the url
     * @throws DataTypeException
     */
    public SerializableTextFile(String url) throws DataTypeException
    {
        this(url, DEFAULT_CHARSET);
    }

    /**
     * Create new instance with the url and the character set.
     * 
     * @param url the url
     * @param charset the character set
     * @throws DataTypeException
     */
    public SerializableTextFile(String url, String charset) throws DataTypeException
    {
        super();
        fileDescriptor.put(RESERVED_KEY_NAME, url);
        BufferedReader reader = null;
        try 
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(url), charset));
            String line = "";
            StringBuffer buf = new StringBuffer();
            int i = 0;
            while((line = reader.readLine()) != null)
            {
                if (i != 0) 
                    buf.append(System.getProperty("line.separator")); // return character
                buf.append(line);
            }
            text = buf.toString();
            fileDescriptor.put(RESERVED_KEY_SIZE, text.length());
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
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    throw new DataTypeException(e);
                }
            }
        }
    }
    
    @Override
    public Object getContent() 
    {
        return text;
    }

    @Override
    public void setContent(Object content) 
    {
        this.fileDescriptor.clear();
        this.text = (String)content;
        fileDescriptor.put(RESERVED_KEY_SIZE, text.length());
    }

    @Override
    public void write(String url) throws DataTypeException
    {
        write(url, DEFAULT_CHARSET);
    }

    public void write(String url, String charset) throws DataTypeException
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url), charset));
            writer.write(text);
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
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    throw new DataTypeException(e);
                }
            }
        }
    }
}
