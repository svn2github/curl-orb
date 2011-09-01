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

/**
 * Writer of serializable stream.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public interface SerializableStreamWriter 
{
    /**
     * Write the object
     * 
     * @param obj the object
     * @throws SerializerException
     */
	public void write(Object obj) throws SerializerException;

    /**
     * Flush the object to OutputStream.
     * 
     * @throws SerializerException
     */
	public void flush() throws SerializerException;

	/**
	 * Close the stream.
	 * 
	 * @throws SerializerException
	 */
	public void close() throws SerializerException;
}
