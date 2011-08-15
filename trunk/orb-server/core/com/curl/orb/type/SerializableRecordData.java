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

import java.util.HashMap;;

/**
 * @author ucai.zhang
 * @since 0.7
 */
public class SerializableRecordData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> data;

	public SerializableRecordData(){
		data = new HashMap<String, Object>();
	}

	public SerializableRecordData(String[] names, Object[] datas) throws DataTypeException {
		if(names.length != datas.length)
			throw new DataTypeException("The key's lenght is not equal to the lenght of value's");
		data = new HashMap<String, Object>();
		for(int i = 0; i < names.length; i++)
			data.put(names[i],datas[i]);
	}

	public void set(String key, Object value) {
		data.put(key, value);
	}

	public Object get(String key){
		return data.get(key);
	}

	public boolean contains(String key) {
		return data.containsKey(key);
	}

	public void remove(String key) {
		data.remove(key);
	}

	public void clear() {
		data.clear();
	}
}

