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

/**
 * @author Hitoshi Okada
 */

package com.curlap.orb.test;

import junit.framework.TestCase;

import com.curlap.orb.type.AbstractSerializableFile;
import com.curlap.orb.type.DataTypeException;
import com.curlap.orb.type.SerializableBinaryFile;
import com.curlap.orb.type.SerializableTextFile;

import curl.language.containers.ByteArray;

/**
 * SerializableBinaryTest
 */
public class SerializableBinaryFileTest extends TestCase
{
    final String filename = "test-out.dat";
    
    // SerializableBinaryFile
    public void testBinary() throws Exception
    {
        byte[] bytes = new byte[]{'t', 'e', 's', 't'};

        // writes
        AbstractSerializableFile writer = new SerializableBinaryFile();
        writer.setContent(new ByteArray(bytes));
        assertEquals("SerializableBinary test", 
                writer.getFileDescriptor().get(writer.RESERVED_KEY_SIZE),
                bytes.length
        );
        writer.write(filename);

        // read
        AbstractSerializableFile reader = new SerializableBinaryFile(filename);
        assertEquals("SerializableBinary test", 
                reader.getFileDescriptor().get(reader.RESERVED_KEY_NAME),
                filename
        );
        assertEquals("SerializableBinary test", 
                reader.getFileDescriptor().get(reader.RESERVED_KEY_SIZE),
                bytes.length
        );
        byte[] tmp = ((ByteArray)reader.getContent()).getRawBytes();
        for (int i = 0; i < bytes.length; i++)
            assertEquals("SerializableBinary test", bytes[i], tmp[i]);
    }

    // SerializableTextFile
    public void testText() throws Exception
    {
        String str = "test";
        AbstractSerializableFile writer = new SerializableTextFile();
        writer.setContent(str);
        assertEquals("SerializableText test", 
                writer.getFileDescriptor().get(writer.RESERVED_KEY_SIZE),
                str.length()
        );
        writer.write(filename);

        AbstractSerializableFile reader = new SerializableTextFile(filename);
        assertEquals("SerializableText test", 
                reader.getFileDescriptor().get(reader.RESERVED_KEY_NAME),
                filename
        );
        assertEquals("SerializableText test", 
                reader.getFileDescriptor().get(reader.RESERVED_KEY_SIZE),
                str.length()
        );
        assertEquals("SerializableText test", reader.getContent(), str);
    }
    
    // binary exception
    public void testBinaryException() throws Exception
    {
        try {
            new SerializableBinaryFile("nothing-file");
            fail("serializableBinary test");
        } catch (DataTypeException e) {}
    }

    // text exception
    public void testTextException() throws Exception
    {
        try {
            new SerializableTextFile("nothing-file");
            fail("serializableText test");
        } catch (DataTypeException e) {}
    }
}
