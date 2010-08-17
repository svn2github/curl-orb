// Copyright (C) 1998-2010, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curlap.orb.plugin.generator.bean;

/**
 * Curl specification utility.
 * 
 * @author 
 * @since 0.8
 */
public class Field {

	// field name
	private String name;
	// Curl type
	private String type;
	// let or field
	private String isStatic;
	// "transient" or ""
	private boolean isTransient;
	// public, package, protected, private +(transient) + "-get"
	private String getterModifier;
	// public, package, protected, private +(transient) + "-set"
	private String setterModifier;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsStatic() {
		return isStatic;
	}
	public void setIsStatic(String isStatic) {
		this.isStatic = isStatic;
	}
	public boolean getIsTransient() {
		return isTransient;
	}
	public void setIsTransient(boolean isTransient) {
		this.isTransient = isTransient;
	}
	public String getGetterModifier() {
		return getterModifier;
	}
	public void setGetterModifier(String modifier) {
		this.getterModifier = modifier;
	}
	public String getSetterModifier() {
		return setterModifier;
	}
	public void setSetterModifier(String setterModifier) {
		this.setterModifier = setterModifier;
	}
}
