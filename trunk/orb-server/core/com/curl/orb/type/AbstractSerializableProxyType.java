// Copyright (C) 1998-2008, Sumisho Computer Systems Corp. All Rights Reserved.

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

//   http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.curl.orb.type;

import java.lang.reflect.Field;

import com.curl.io.serialize.SerializeException;

/**
 * Abstract proxy class of java's classes.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public abstract class AbstractSerializableProxyType implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Extract the proper object from the extra object to serialize. <p>
	 * e.g) types.BigInteger -> java.math.BigInteger(@return)
	 *  
	 * @return the object
	 * @throws SerializeException
	 */
	public abstract Object extractProperObject() throws SerializeException;
	

	/**
	 * Inject the proper object to the extra object to deserialize. <p>
	 * e.g) java.math.BigInteger(@param) -> types.BigInteger
	 *
	 * @param obj the object
	 * @throws SerializeException
	 */
	public abstract void injectProperObject(Object obj) throws SerializeException;
	
	
	// - - internal method - -

	// get the value from field of obj.
	Object getField(Object obj, String fieldName) throws SerializeException 
	{
		try
		{
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object returnedObject = field.get(obj);
			field.setAccessible(false);
			return returnedObject;
		}
		catch (NoSuchFieldException e) 
		{
			throw new SerializeException(e);
		}
		catch (IllegalAccessException e) 
		{
			throw new SerializeException(e);
		}
	}
	
	// set the value to field of obj.
	void setField(Object obj, String fieldName, Object value) throws SerializeException 
	{
		try
		{
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
		catch (NoSuchFieldException e) 
		{
			throw new SerializeException(e);
		}
		catch (IllegalAccessException e) 
		{
			throw new SerializeException(e);
		}
	}
}
