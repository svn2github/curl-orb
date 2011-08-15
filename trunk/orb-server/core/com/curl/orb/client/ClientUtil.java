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

package com.curl.orb.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

import com.curl.orb.common.ExceptionContent;
import com.curl.orb.common.InstanceManagementRequest;
import com.curl.orb.common.InstanceManagementResponse;
import com.curl.orb.io.AbstractSerializer;
import com.curl.orb.io.CurlSerializableStreamReader;
import com.curl.orb.io.SerializableStreamReader;
import com.curl.orb.io.SerializerException;
import com.curl.orb.io.SerializerFactory;

/**
 * Client utility.
 * 
 * @author Akira Mori
 */
class ClientUtil {

	private HttpClient httpClient;

	/* constructors */
	ClientUtil() {
		httpClient = new HttpClient();
	}
	ClientUtil(ORBSession orbSession) {
		httpClient = orbSession.getHttpClient();
	}


	/* methods */
	Object requestToORB(
			InstanceManagementRequest request,
			String uri
	) throws ORBClientException, ORBServerException {
		return requestToORB(request, uri, false);
	}

	Object requestToORB(
			InstanceManagementRequest request, 
			String uri, 
			boolean hasManyResult
	) throws ORBClientException, ORBServerException {
		AbstractSerializer serializer = SerializerFactory.getInstance().getSerializer();
		OutputStream outputStream = new ByteArrayOutputStream();
		Object returnedObject = null;
		InputStream inputStream = null;
		PostMethod method = null;
		try {
			serializer.serialize(request, null, outputStream);
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			method = new PostMethod(uri);
			method.setRequestHeader(
					"Content-Length", 
					((ByteArrayOutputStream) outputStream).size() + ""
			);
			method.setRequestEntity(
					new ByteArrayRequestEntity(((ByteArrayOutputStream) outputStream).toByteArray(),
					"application/octet-stream; charset=UTF-8")
			);
			httpClient.executeMethod(method);
			method.getResponseBody();
			inputStream = (ByteArrayInputStream) method.getResponseBodyAsStream();
			SerializableStreamReader deserializer = new CurlSerializableStreamReader(inputStream);
			returnedObject = ((InstanceManagementResponse) deserializer.read()).getBody();			
			if (hasManyResult) {
				List<Object> resultList = new ArrayList<Object>();
				resultList.add(returnedObject);
				Object innerresult = null;
				while ((innerresult = deserializer.read()) != null)
					resultList.add(innerresult);
				return resultList;
			}
			if (returnedObject instanceof ExceptionContent) {
				throw new ORBServerException((ExceptionContent) returnedObject);
			}
		} catch (SerializerException e) {
			throw new ORBClientException(e);
		} catch (HttpException e) {
			throw new ORBClientException(e);
		} catch (IOException e) {
			throw new ORBClientException(e);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					throw new ORBClientException(e);
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							throw new ORBClientException(e);
						}
					}
					method.releaseConnection();
				}
			}
		}
		return returnedObject;
	}
}
