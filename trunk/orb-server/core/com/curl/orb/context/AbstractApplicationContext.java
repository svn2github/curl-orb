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

package com.curl.orb.context;

/**
 * Abstract class of application context. 
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public abstract class AbstractApplicationContext 
{
	/**
	 * Get the managed object in application context.
	 * 
	 * @param name the component name
	 * @return the managed object
	 * @throws ApplicationContextException
	 */
    public abstract Object getObject(String name) throws ApplicationContextException;
   
    /**
     * Set the object to application context.
     * 
     * @param name the component name 
     * @param value the managed object
     * @throws ApplicationContextException
     */
    public abstract void setObject(String name, Object value) throws ApplicationContextException;

    /**
     * get the managed object type in application context.
     * 
     * @param name the component name
     * @return the object type
     * @throws ApplicationContextException
     */
    public abstract Class<?> getObjectType(String name) throws ApplicationContextException;

    /**
     * Get names of managed objects.
     * 
     * @return names of managed objects
     * @throws ApplicationContextException
     */
    public abstract String[] getObjectNames() throws ApplicationContextException;
    
    /**
     * Get the proper class from object.
     * 
     * @param obj
     * @return the proper class
     */
    public Class<?> getProperType(Object obj) throws ApplicationContextException
    {
    	return obj.getClass();
    }
}
