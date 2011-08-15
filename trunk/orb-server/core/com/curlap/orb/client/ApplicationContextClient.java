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

package com.curlap.orb.client;

import java.util.HashMap;

import com.curlap.orb.common.InvokeApplicationContextRequest;

/**
 * Client to invoke a method of object managed on ApplicationContext.
 * 
 * @author Akira Mori
 * @since 0.6
 */
public class ApplicationContextClient {

	private ClientUtil clientUtil;
	private String serverUrl;
	private String contextNameInvoke = ORBDefaultServerUrl.INVOKE_APPLICATIONCONTEXT_NAME;
	private String componentName;

	/**
	 * Create a new ApplicationContextClient with your own ORB server URL. <p>
	 * 
	 * @param serverUrl the ORB server URL
	 * @param componentName the component name you want to call
	 */
	public ApplicationContextClient(String serverUrl, String componentName) {
		clientUtil = new ClientUtil();
		this.serverUrl = serverUrl;
		this.componentName = componentName;
	}
	/**
	 * Create a new ApplicationContextClient with default URL. <p>
	 * 
	 * default URL: http://localhost:8080/curl-orb-server
	 *
	 * @param componentName the component name you want to call
	 */
	public ApplicationContextClient(
			String componentName
	) {
		this(ORBDefaultServerUrl.DEFAULT_SERVER_URL, componentName);
	}
	
	/**
	 * Create a new ApplicationContextClient with your own ORB server URL. <p>
	 * 
	 * @param orbSession the session to keep the connection with server.
	 * @param serverUrl the ORB server URL
	 * @param componentName the component name you want to call
	 */
	public ApplicationContextClient(
			ORBSession orbSession, 
			String serverUrl,
			String componentName
	) {
		clientUtil = new ClientUtil(orbSession);
		this.serverUrl = serverUrl;
		this.componentName = componentName;
	}
	/**
	 * Create a new ApplicationContextClient with default URL. <p>
	 * 
	 * default URL: http://localhost:8080/curl-orb-server
	 *
	 * @param orbSession the session to keep the connection with server.
	 * @param componentName the component name you want to call
	 */
	public ApplicationContextClient(
			ORBSession orbSession, 
			String componentName
	) {
		this(orbSession, ORBDefaultServerUrl.DEFAULT_SERVER_URL, componentName);
	}

	/**
	 * Invoke method with your own context name and receive the data with stream type. <p>
	 * 
	 * @param contextName   the context for context invocation
	 * @param methodName    the method name you want to call
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	Object invokeMethod(String contextName, String methodName, Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		InvokeApplicationContextRequest request = new InvokeApplicationContextRequest();
		if (isStream) {
			request.setHeader(new HashMap<Object, Object>());
			request.getHeader().put("com.curlap.orb.internal-stream-response", true);
		}
		request.setClassName(componentName);
		request.setMethodName(methodName);
		request.setArguments(args);
		return clientUtil.requestToORB(request, serverUrl + contextName, isStream);
	}

	/**
	 * Invoke method with your own context name. <p>
	 * 
	 * @param contextName   the context for INVOKE_APPLICATIONCONTEXT_NAME
	 * @param methodName    the method name you want to call
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public Object invokeMethod(String contextName, String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeMethod(contextNameInvoke, methodName, args, false);
	}

	/**
	 * Invoke method with default context name and receive the data with stream type. <p>
	 * default context name : /invoke-application-context
	 * 
	 * @param methodName    the method name you want to call
	 * @param args          arguments needed for the method
	 * @param isStream      set "true" for receiving data by Stream type, default is "false"
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	// Not support since 0.7 (public -> package)
	Object invokeMethod(String methodName, Object[] args, boolean isStream) throws ORBClientException, ORBServerException {
		return invokeMethod(contextNameInvoke, methodName, args, isStream);
	}
	
	/**
	 * Invoke method with default context name. <p>
	 * default context name : /invoke-application-context.
	 * 
	 * @param methodName    the method name you want to call
	 * @param args          arguments needed for the method
	 * @return response against the request
	 * @throws ORBClientException  Exception thrown during client side operation
	 * @throws ORBServerException  Exception thrown during server side operation
	 */
	public Object invokeMethod(String methodName, Object[] args) throws ORBClientException, ORBServerException {
		return invokeMethod(contextNameInvoke, methodName, args, false);
	}
}
