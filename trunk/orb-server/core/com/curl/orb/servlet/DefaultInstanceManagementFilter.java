// Copyright (C) 1998-2009, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curl.orb.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.curl.orb.common.ExceptionContent;
import com.curl.orb.common.InstanceManagementException;
import com.curl.orb.common.InstanceManagementRequest;
import com.curl.orb.common.InstanceManagementResponse;
import com.curl.orb.io.AbstractSerializer;
import com.curl.orb.io.SerializerException;
import com.curl.orb.io.SerializerFactory;

/**
 * Default Filter of Curl ORB. This filter serialize and deserialize DTO (Data Transfer Object), and then, handle exceptions.
 * 
 * @author Hitoshi Okada
 * @since 0.5
 */
public class DefaultInstanceManagementFilter implements Filter
{
	private final Log log = LogFactory.getLog(getClass());
	private AbstractSerializer serializer = null;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException 
	{
		serializer = SerializerFactory.getInstance().getSerializer();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		// accept just HTTP.
		if (!((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)))
			throw new ServletException("The request is not HTTP protocol.");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		InstanceManagementRequest instanceManagementRequest = null;
		// serialize the request object and set to the attribute
		try
		{
			if (request.getContentLength() == -1)
			{
				// forward to requested page.
				request.getRequestDispatcher(httpRequest.getServletPath()).forward(request, response);
				return;
			}
			instanceManagementRequest = 
				(InstanceManagementRequest) serializer.deserialize(httpRequest.getInputStream());
			// debug request object (instanceManagementRequest.toString())
			log.debug("Curl ORB Request object [" + instanceManagementRequest + "]");
			httpRequest.setAttribute(InstanceManagementServlet.REQUEST_OBJECT, instanceManagementRequest);
		}
		catch (SerializerException e)
		{
			// NOTE: 
			//  Cannot handle the exception when "stream response" mode. 
			//  Clients cannot receive the error.
			setResponse(
					(HttpServletResponse) response,
					new InstanceManagementException("Could not deserialize the object from Curl.", e),
					null
			);
			return;
		}

		try
		{
			// do filters and servlets
			chain.doFilter(httpRequest, response);
			// deserialize the object and return the response
			setResponse(
					(HttpServletResponse) response,
					httpRequest.getAttribute(InstanceManagementServlet.RESPONSE_OBJECT),
					(Map<String, Object>) httpRequest.getAttribute(InstanceManagementServlet.RESPONSE_SUBORDINATE_OBJECT)
			);
		}
		catch (Exception e)
		{
			setResponse((HttpServletResponse) response, e, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() 
	{ // do nothing
	}


	// - - protected method - -
	protected void setResponse(
			HttpServletResponse response,
			Object obj, 
			Map<String, Object> options
	)
	{
		// debug response object (toString())
		log.debug("Curl ORB Response object: " + obj);
		if (obj instanceof InvocationTargetException)
			// NOTE: InvocationTragetException always happens with application errors.
			setFailedResponse(
					(HttpServletResponse) response,
					((InvocationTargetException) obj).getCause()
			);
		else if (obj instanceof Throwable)
			setFailedResponse((HttpServletResponse) response, (Throwable)obj);
		else
		{
			try
			{
				setSucceededResponse((HttpServletResponse) response, obj, options);
				log.debug("Curl ORB has returned serialized object.");
			}
			catch (InstanceManagementException e) 
			{
				setFailedResponse((HttpServletResponse) response, e);
			}
		}
	}

	// Response the succeeded response 
	protected void setSucceededResponse(
			HttpServletResponse response,
			Object obj,
			Map<String, Object> options
	) throws InstanceManagementException
	{
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream ostream = null;
		try
		{
			ostream = response.getOutputStream();
			// support Response header
			serializer.serialize(new InstanceManagementResponse(obj), options, ostream);
			response.setStatus(200);
		}
		catch (IOException e)
		{
			setFailedResponse(response, e);
		}
		catch (SerializerException e)
		{
			setFailedResponse(
					response,
					new InstanceManagementException("Could not serialize the response object.", e)
			);
		}
		finally 
		{
			if (ostream != null)
			{
				try 
				{
					ostream.close();
				}
				catch (IOException e)
				{
					// ignored
				}
			}
		}
	}

	// Response the failed response
	protected void setFailedResponse(
			HttpServletResponse response,
			Throwable exception
	)
	{
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream ostream = null;
		try
		{
			ostream = response.getOutputStream();
			serializer.serialize(
					new InstanceManagementResponse(new ExceptionContent(exception)),
					null,
					ostream
			);
			response.setStatus(200);
			log.info(exception.getMessage(), exception);
		}
		catch (InstanceManagementException e)
		{
			response.setStatus(500);
			log.debug(e.getMessage(), e);
		}
		catch (SerializerException e)
		{
			response.setStatus(500);
			log.debug(e.getMessage(), e);
		}
		catch (IOException e)
		{
			response.setStatus(500);
			log.debug(e.getMessage(), e);
		}
		finally 
		{
			if (ostream != null)
			{
				try 
				{
					ostream.close();
				}
				catch (IOException e)
				{
					// ignored
				}
			}
		}
	}
}