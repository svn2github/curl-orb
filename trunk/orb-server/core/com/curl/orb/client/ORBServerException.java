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

import com.curl.orb.common.ExceptionContent;

/**
 * Exception thrown during server side operation.
 * @author Akira Mori
 * @since 0.6
 */
public class ORBServerException extends Exception {

	private static final long serialVersionUID = 1L;

	private ExceptionContent exceptionContent = null;
	
	/**
	 * Return content of exception
	 * @return the content of exception
	 */
	public ExceptionContent getExceptionContent() {
		return exceptionContent;
	}

	/**
	 * Create a new ORBServerException with the specified content of exception.
	 * @param exceptionContent content of exception
	 */
	public ORBServerException(ExceptionContent exceptionContent) 
	{
		super("Unexpected exception.[" + exceptionContent.getMessage() + "]");
		this.exceptionContent = exceptionContent;
	}

	/**
	 * Create a new ORBServerException.
	 */
	public ORBServerException() 
	{
		super();
	}

	/**
	 * Create a new ORBServerException with specified message.
	 * @param message the detailed message
	 */
	public ORBServerException(String message) 
	{
		super(message);
	}

	/**
	 * Create a new ORBServerException with specified message and given root cause.
	 * @param message the detailed message
	 * @param rootCause the root cause
	 */
	public ORBServerException(String message, Throwable rootCause) 
	{
		super(message, rootCause);
	}

	/**
	 * Create a new ORBServerException with given root cause.
	 * @param rootCause the root cause
	 */
	public ORBServerException(Throwable rootCause) 
	{
		super(rootCause);
	}
}
