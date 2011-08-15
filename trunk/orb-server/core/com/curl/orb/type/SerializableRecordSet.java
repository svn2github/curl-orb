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

package com.curl.orb.type;

import java.util.List;
import java.util.ArrayList;

/**
 * A serializable RecordSet class for interoperability of Curl's RecordSet.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class SerializableRecordSet implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

    private SerializableRecordField[] fields;
    //private List<SerializableRecordData> records;
    private List<Object> records;

    public SerializableRecordSet() {
    	
    }
    
    /**
     * Create new instance.
     * 
     * @param fields the RecordFields.
     */
    public SerializableRecordSet(SerializableRecordField[] fields)
    {
        this.fields = fields;
        records = new ArrayList<Object>();
    }

    /**
     * Get fields.
     * 
     * @return RecordFields
     */
    public SerializableRecordField[] getFields(){
        return fields;
    }

    /**
     * Add one record from column data's array.
     * 
     * @param record
     */
    public void addRecord(SerializableRecordData record)
    {
        records.add(record);
    }
    
    /**
     * Clear all records.
     */
    public void clearRecords()
    {
        records.clear();
    }

    /**
     * Get one record by index.
     * 
     * @param index the index.
     * @return the record
     */
    public SerializableRecordData getRecord(int index)
    {
        return (SerializableRecordData)records.get(index);
    }

    /**
     * Get the record size.
     * 
     * @return size
     */
    public int getRecordSize()
    {
        return records.size();
    }
}
