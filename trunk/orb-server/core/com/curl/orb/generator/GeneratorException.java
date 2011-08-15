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

package com.curl.orb.generator;

/**
 * GeneratorException
 */
public class GeneratorException extends Exception 
{
	private static final long serialVersionUID = 1L;

    /**
     * Create a new GeneratorException. 
     */
    public GeneratorException() 
    {
        super();
    }

    /**
     * Create a new GeneratorException with the specified detail message. 
     * 
     * @param message the detail message
     */
    public GeneratorException(String message) 
    {
        super(message);
    }

    /**
     * Create a new GeneratorException with the specified detail message and the given root cause. 
     * 
     * @param message the detail message
     * @param rootCause the root cause
     */
    public GeneratorException(String message, Throwable rootCause) 
    {
        super(message, rootCause);
    }

    /**
     * Create a new GeneratorException with the given root cause. 
     * 
     * @param rootCause the root cause
     */
    public GeneratorException(Throwable rootCause) 
    {
        super(rootCause);
    }
}
