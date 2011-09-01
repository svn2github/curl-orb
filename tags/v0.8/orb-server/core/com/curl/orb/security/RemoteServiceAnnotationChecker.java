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

package com.curl.orb.security;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Checker the RemoteService annotation.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public class RemoteServiceAnnotationChecker
{
	/**
	 * Check the PublicService annotation. Throw AccessException if false.
	 * 
	 * @param cls the class
	 * @throws AccessException
	 */
	public static void check(Class<?> cls, Environment environment) throws AccessException 
	{
		// ignore security
		if (environment == null)
			return;
			
		RemoteService remoteServiceAnnotation = 
			(RemoteService) cls.getAnnotation(RemoteService.class);
		if (!Modifier.isPublic(cls.getModifiers()) ||
				remoteServiceAnnotation == null || 
				!environment.contain(remoteServiceAnnotation.value()))
		{
			Log log = LogFactory.getLog(RemoteServiceAnnotationChecker.class);
			log.debug("Cannot allow to access the class [" + cls.getName() + "]");
			throw new AccessException("Cannot allow to access the class [" + cls.getName() + "]");
		}
		// TODO: Cache the class(cls). Which is faster, cache or annotation?
	}
}
