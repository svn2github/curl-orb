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

import java.util.Map;
import java.util.HashMap;

/**
 * A serializable file class to download and upload.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public abstract class AbstractSerializableFile implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	final public transient String RESERVED_KEY_NAME = "name";
    final public transient String RESERVED_KEY_SIZE = "size";
    final protected Map<Object, Object> fileDescriptor;
    
    // constructors
    public AbstractSerializableFile()
    {
        fileDescriptor = new HashMap<Object, Object>();
    }
    
    // getter/setter
    public Map<Object, Object> getFileDescriptor() 
    {
        return fileDescriptor;
    }
    //public void setFileDescriptor(Map fileDescriptor)
    //{
    //    this.fileDescriptor = fileDescriptor;
    //}
    
    // - - abstract methods - -
    // get data's content
    public abstract Object getContent();

    // set data's content 
    public abstract void setContent(Object content);
    
    // write data's content to file
    public abstract void write(String url) throws DataTypeException;
}
