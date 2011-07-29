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

package com.curlap.orb.test;

import java.util.Date;

import junit.framework.TestCase;

import com.curlap.orb.type.SerializableRecordData;
import com.curlap.orb.type.SerializableRecordField;
import com.curlap.orb.type.SerializableRecordSet;

import curl.language.date_time.DateTime;

/**
 * SerializableRecordSetTest
 */
public class SerializableRecordSetTest extends TestCase
{

    public void testRecordSet() throws Exception
    {
        // create RecordSet
        int count = 10;
        SerializableRecordSet records = new SerializableRecordSet(
                new SerializableRecordField[]{
                        new SerializableRecordField("name", String.class),
                        new SerializableRecordField("money", int.class),
                        new SerializableRecordField("date", DateTime.class)
                }        
        );
        for (int i = 0; i < count; i++)
        	records.addRecord(
        			new SerializableRecordData(
        					new String[]{"name", "money", "date"},
        					new Object[]{"str" + i, i, new DateTime()})
        	);
        
        // validation
        assertEquals("RecordSet test", records.getRecordSize(), count);
        assertEquals("RecordSet test", records.getFields().length, 3);
        assertEquals("RecordSet test", records.getFields()[0].getName(), "name");
        assertEquals("RecordSet test", records.getFields()[1].getName(), "money");
        assertEquals("RecordSet test", records.getFields()[2].getName(), "date");
        for (int i = 0; i < count; i++)
        {
        	SerializableRecordData record = (SerializableRecordData)records.getRecord(i);
            assertEquals("RecordSet test", record.get("name"), "str" + i);
            assertEquals("RecordSet test", record.get("money"), i);
            assertTrue("RecordSet test", ((DateTime)record.get("date")).getData().getDate().compareTo(new Date()) <= 0);
        }
        
        // clear
        records.clearRecords();
        assertEquals("RecordSet test", records.getRecordSize(), 0);
    }
}
