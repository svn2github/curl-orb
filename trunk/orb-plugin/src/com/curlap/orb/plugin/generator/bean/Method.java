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
 * @author Wang Huailiang
 * @since 0.8
 */
public class Method {
	
	private String methodName; //method name in Java 
	private String methodName4Curl; //method name in Curl
	private String methodParams ; //method parameters
	private String methodReturnType; // return type 
	private String methodArguments4Curl; // arguments in Curl method body
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodName4Curl() {
		return methodName4Curl;
	}
	public void setMethodName4Curl(String methodName4Curl) {
		this.methodName4Curl = methodName4Curl;
	}
	public String getMethodParams() {
		return methodParams;
	}
	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}
	public String getMethodReturnType() {
		return methodReturnType;
	}
	public void setMethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
	}
	public String getMethodArguments4Curl() {
		return methodArguments4Curl;
	}
	public void setMethodArguments4Curl(String methodArguments4Curl) {
		this.methodArguments4Curl = methodArguments4Curl;
	}
	
	
}
