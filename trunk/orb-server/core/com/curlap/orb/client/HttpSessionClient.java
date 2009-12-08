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

package com.curlap.orb.client;

import java.util.HashMap;

import com.curlap.orb.common.DestroyInstanceRequest;
import com.curlap.orb.common.InvokeHttpSessionRequest;
import com.curlap.orb.common.NewInstanceRequest;

/**
 * Client to invoke a method of object managed on HttpSession.
 *
 * @author Akira Mori
 * @since 0.6
 */
public class HttpSessionClient {

	private ClientUtil clientUtil = null; 
	private String objectId = null;
	private String serverUrl = null;

	/**
	 * Instantiate the class with your own ORB server URL and your own context name
	 * @param serverUrl   the ORB server URL
	 * @param contextName the context name for instantiation
	 * @param className   the class name you want to instantiate
	 * @param args          arguments needed for the class instantiation
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public HttpSessionClient(String serverUrl, String contextName, String className, Object[] args) throws ORBClientException, ORBServerException  {
		newInstance(serverUrl, contextName, className, args);
	}
	/**
	 * Instantiate the class with default ORB server URL and your own context name
	 * default URL: http://localhost:8080/curl-orb-server
	 * @param contextName the context name for instantiation
	 * @param className   the class name you want to instantiate
	 * @param args          arguments needed for the class instantiation
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public HttpSessionClient(String contextName, String className, Object[] args) throws ORBClientException, ORBServerException  {
		newInstance(ORBDefaultServerUrl.DEFAULT_SERVER_URL, contextName, className, args);
	}
	/**
	 * Instantiate the class with default ORB server URL and default context name
	 * default URL: http://localhost:8080/curl-orb-server
	 * default context name:  /new-instance
	 * @param className   the class name you want to instantiate
	 * @param args          arguments needed for the class instantiation
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public HttpSessionClient(String className, Object[] args) throws ORBClientException, ORBServerException  {
		newInstance(ORBDefaultServerUrl.DEFAULT_SERVER_URL, ORBDefaultServerUrl.NEW_INSTANCE_CONTEXT_NAME, className, args);
	}

	private void newInstance(String serverUrl, String contextName, String className, Object[] args) throws ORBClientException, ORBServerException {
		this.serverUrl = serverUrl;
		clientUtil = new ClientUtil();
		NewInstanceRequest request = new NewInstanceRequest();
		request.setClassName(className);
		request.setArguments(args);
		objectId = (String) clientUtil.requestToORB(request, serverUrl + contextName);
	}

	/**
	 * Invoke method with your own context name and receive the data with stream type
	 * @param contextName   the context for method invocation
	 * @param methodName    the method name you want to invoke
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	Object invokeMethod(String contextName, String methodName,Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		InvokeHttpSessionRequest request = new InvokeHttpSessionRequest();
		if (isStream) {
			request.setHeader(new HashMap<Object, Object>());
			request.getHeader().put("com.curlap.orb.internal-stream-response",true);
		}
		request.setMethodName(methodName);
		request.setArguments(args);
		request.setObjectId(objectId);
		return clientUtil.requestToORB(request, serverUrl + contextName, isStream);
	}
	/**
	 * Invoke method with your own context name
	 * @param contextName   the context for method invocation
	 * @param methodName    the method name you want to invoke
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public Object invokeMethod(String contextName, String methodName,Object[] args) throws ORBClientException, ORBServerException {
		return invokeMethod(contextName, methodName, args, false);
	}
	
	/**
	 * Invoke method with default context name and receive the data with stream type
	 * default context name : /invoke-http-session
	 * @param methodName    the method name you want to invoke
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	Object invokeMethod(String methodName, Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		return invokeMethod(ORBDefaultServerUrl.INVOKE_HTTPSESSION_NAME, methodName, args,isStream);
	}
	
	/**
	 * Invoke method with default context name
	 * default context name : /invoke-http-session
	 * @param methodName    the method name you want to invoke
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public Object invokeMethod(String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeMethod(ORBDefaultServerUrl.INVOKE_HTTPSESSION_NAME, methodName, args, false);
	}
	
	// destroy
	/**
	 * Destroy the Object you instantiated with your own context name
	 * @param contextName the context for destroy object
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public void destroy(String contextName) throws ORBClientException, ORBServerException  {
		DestroyInstanceRequest request = new DestroyInstanceRequest();
		request.setObjectId(objectId);
		clientUtil.requestToORB(request, serverUrl + contextName);
	}
	/**
	 * Destroy the Object you instantiated with default context name
	 * default context name : /destroy-instance
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public void destroy() throws ORBClientException, ORBServerException  {
		destroy(ORBDefaultServerUrl.DESTROY_CONTEXT_NAME);
	}

	// invokeStaticMethod	
	/**
	 * Invoke static method with your own ORB server URL and context name receiving data with stream type
	 * @param serverUrl     the ORB server URL
	 * @param contextName   the context for method invocation
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	static Object invokeStaticMethod(String serverUrl, String contextName, String className, String methodName,Object[] args,boolean isStream) throws ORBClientException, ORBServerException {
		ClientUtil staticmessengerutil = new ClientUtil();
		InvokeHttpSessionRequest request = new InvokeHttpSessionRequest();
		if (isStream) {
			request.setHeader(new HashMap<Object, Object>());
			request.getHeader().put("com.curlap.orb.internal-stream-response",true);
		}
		request.setClassName(className);
		request.setMethodName(methodName);
		request.setArguments(args);
		return staticmessengerutil.requestToORB(request, serverUrl + contextName, isStream);
	}

	/**
	 * Invoke static method with your own ORB server URL and context name
	 * @param serverUrl     the ORB server URL
	 * @param contextName   the context for method invocation
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public static Object invokeStaticMethod(String serverUrl, String contextName, String className, String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeStaticMethod(serverUrl,contextName,className, methodName, args, false);
	}

	/**
	 * Invoke static method with default ORB server URL and context name 
	 * default URL: http://localhost:8080/curl-orb-server
	 * @param contextName   the context for method invocation
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public static Object invokeStaticMethod(String contextName, String className, String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeStaticMethod(ORBDefaultServerUrl.DEFAULT_SERVER_URL, contextName, className, methodName, args, false);
	}

	/**
	 * Invoke static method with default ORB server URL and your own context name receiving data with stream type
	 * default URL: http://localhost:8080/curl-orb-server
	 * @param contextName   the context for method invocation
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	static Object invokeStaticMethod(String contextName, String className, String methodName,Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		return invokeStaticMethod(ORBDefaultServerUrl.DEFAULT_SERVER_URL, contextName, className, methodName, args, isStream);
	}
	
	/**
	 * Invoke static method with default ORB server URL and context name
	 * default URL: http://localhost:8080/curl-orb-server
	 * default context name : /invoke-http-session
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeStaticMethod(ORBDefaultServerUrl.INVOKE_HTTPSESSION_NAME, className, methodName, args, false);
	}
	
	/**
	 * Invoke static method with default ORB server URL and context name receiving data with stream type
	 * default URL: http://localhost:8080/curl-orb-server
	 * default context name : /invoke-http-session
	 * @param className     the class name 
	 * @param methodName    the method name
	 * @param args          arguments needed for the method
 	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	static Object invokeStaticMethod(String className, String methodName,Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		return invokeStaticMethod(ORBDefaultServerUrl.INVOKE_HTTPSESSION_NAME, className, methodName, args, isStream);
	}
}
