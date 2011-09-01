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

package com.curl.orb.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.curl.orb.io.CurlSerializableStreamReader;
import com.curl.orb.io.CurlSerializableStreamWriter;
import com.curl.orb.io.SerializableStreamReader;
import com.curl.orb.io.SerializableStreamWriter;

import junit.framework.TestCase;

/**
 * CurlSerializableStreamTest
 *   NOTE: serializer tests have almost done in other projects. 
 */
public class CurlSerializableStreamTest extends TestCase
{
    final String filename = "test-out.dat";

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testSerializableStreamWriter() throws Exception
    {
        SerializableStreamWriter writer = 
        	new CurlSerializableStreamWriter(new FileOutputStream(new File(filename)), false);
        writer.write("str");
        writer.write(123);
        writer.write(true);
        writer.close();
    }
    
    public void testSerializableStreamReader() throws Exception
    {
        SerializableStreamReader reader = new CurlSerializableStreamReader(new FileInputStream(new File(filename)));
        assertEquals(reader.read(), "str");
        assertEquals(reader.read(), 123);
        assertEquals(reader.read(), true);
        reader.close();
    }
    
    public void testClose() throws Exception
    {
        //assertTrue((new File(filename)).delete());
    }
}
