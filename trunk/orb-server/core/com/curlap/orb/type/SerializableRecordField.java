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

/**
 * A serializable RecordField class for interoperability of Curl's RecordField.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class SerializableRecordField implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String caption;
	private Class<?> domain;
	private boolean isModifiable = true;
	private boolean isNullable = false;
	private Object defaultValue = null;
	private int indexType = 2; // 0:unique, 1:multiple, 2:none

	public SerializableRecordField() {
		// do nothing
	}
	
	/**
	 * Create new instance.
	 * 
	 * @param name the field name
	 * @param domain the domain class
	 */
	public SerializableRecordField(String name, Class<?> domain)
	{
		this.name = name;
		this.caption = name;
		this.domain = domain;
	}

	/**
	 * Create new instance.
	 * 
	 * @param name the field name
	 * @param caption the caption name
	 * @param domain the domain class
	 */
	public SerializableRecordField(String name, String caption, Class<?> domain)
	{
		this.name = name;
		this.caption = (caption != null ? caption : name);
		this.domain = domain;
	}

	/**
	 * Create new instance.
	 * 
	 * @param name the field name
	 * @param caption the caption name
	 * @param domain the domain class
	 * @param isModifiable modifiable or not
	 * @param isNullable nullable or not
	 * @param defaultValue the default value
	 * @param indexType the index type
	 */
	public SerializableRecordField(String name, String caption, Class<?> domain, 
			boolean isModifiable, boolean isNullable, Object defaultValue, int indexType)
	{
		this.name = name;
		this.caption = (caption != null ? caption : name);
		this.domain = domain;
		this.isModifiable = isModifiable;
		this.isNullable = isNullable;
		this.defaultValue = (isNullable ? null : defaultValue);
		this.indexType = indexType;
	}

	/**
	 * Get the field name.
	 * @return the field name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the field name.
	 * @param name the field name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Get the caption name.
	 * @return the caption name
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * Set the caption name.
	 * @param caption the caption name.
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	/** 
	 * Get the domain class.
	 * @return the domain class
	 */
	public Class<?> getDomain()
	{
		return domain;
	}

	/**
	 * Set the domain class.
	 * @param domain the domain class
	 */
	public void setDomain(Class<?> domain)
	{
		this.domain = domain;
	}

	/**
	 * Get the modifiable.
	 * @return modifiable
	 */
	public boolean getIsModifiable()
	{
		return isModifiable;
	}

	/**
	 * Set the modifiable.
	 * @param isModifiable modifiable
	 */
	public void setIsModifiable(boolean isModifiable)
	{
		this.isModifiable = isModifiable;
	}

	/**
	 * Get nullable.
	 * @return nullable
	 */
	public boolean getIsNullable()
	{
		return isNullable;
	}

	/**
	 * Set nullable.
	 * @param isNullable nullable
	 */
	public void setIsNullable(boolean isNullable)
	{
		this.isNullable = isNullable;
	}

	/**
	 * Get the default value.
	 * @return the default value
	 */
	public Object getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Set the default value.
	 * @param defaultValue the default value
	 */
	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * Get the index type.
	 * @return the index type
	 */
	public int getIndexType()
	{
		return indexType;
	}

	/**
	 * Set the index type.
	 * @param indexType the index type
	 */
	public void setIndexType(int indexType)
	{
		this.indexType = indexType;
	}
}
