package com.curlap.orb.plugin.bean;


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
