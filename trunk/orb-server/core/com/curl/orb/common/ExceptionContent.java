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

package com.curl.orb.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception's content to return to client.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class ExceptionContent implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String exceptionName;
    private String message;
    private ExceptionContent cause;
    private List<Object> exceptionFields;

    /**
     * Create new ExceptionContent.
     * 
     * @param throwable the throwable.
     * @throws InstanceManagementException
     */
    public ExceptionContent(Throwable throwable) throws InstanceManagementException
    {
        exceptionName = throwable.getClass().getName();
        message = throwable.getMessage();
        try 
        {
        	exceptionFields = getFieldValues(throwable);
        }
        catch (IllegalAccessException e)
        {
        	throw new InstanceManagementException();
        }
        cause = (throwable.getCause() != null ? new ExceptionContent(throwable.getCause()) : null);
    }
    
    // NOTE: To deserialize an exception object
    //       when com.curl.orb.client receives exception.
    public ExceptionContent()
    {
    	// do nothing
    }

	/* (non-Javadoc) */
    public String getExceptionName()
    {
        return exceptionName;
    }

	/* (non-Javadoc) */
    public String getMessage()
    {
        return message;
    }
    
	/* (non-Javadoc) */
    public ExceptionContent getCause() 
    {
        return cause;
    }
    
	/* (non-Javadoc) */
    public List<Object> getExceptionFields() 
    {
        return exceptionFields;
    }
    
    
    // get field's value as array
	/* (non-Javadoc) */
    private List<Object> getFieldValues(Object obj) throws IllegalAccessException
    {
    	if (obj instanceof java.lang.reflect.InvocationTargetException)
    		return null;
    	Class<?> cls = obj.getClass();
    	Field[] fields = cls.getDeclaredFields();
    	List<Object> fieldList = new ArrayList<Object>();
    	for (Field field : fields) 
    	{
    		int modifier = field.getModifiers();
			if (!Modifier.isTransient(modifier) && !Modifier.isStatic(modifier)) {
				field.setAccessible(true);
				fieldList.add(field.get(obj));
				field.setAccessible(false);
			}
    	}
    	return fieldList;
    }
}
