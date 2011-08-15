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

/**
 * Environment, such as PRODUCTION, TEST and DEVELOPMENT.
 * 
 * @author Hitoshi Okada
 * @since 0.6
 */
public enum Environment 
{

	/**
	 * The production environment.
	 */
	PRODUCTION("production")
	{
		public boolean contain(Environment environment)
		{
			return (environment == Environment.PRODUCTION || environment == null);
		}
	},

	/**
	 * The test environment.
	 */
	TEST("test")
	{
		public boolean contain(Environment environment)
		{
			if (environment == null) 
				return true;
			switch(environment)
			{
			case PRODUCTION:
			case TEST:
				return true;
			}
			return false;
		}
	},

	/**
	 * The development environment.
	 */
	DEVELOPMENT("development")
	{
		public boolean contain(Environment environment)
		{
			return true;
		}
	};


	private String name;

	private Environment(String name)
	{
		this.name = name;
	}

	/**
	 * PRODCTION <p>
	 *  return true if the environment is Environment.PRODCTION. <p>
	 * TEST <p>
	 *  return true if the environment is Environment.PRODCTION, Environment.TEST. <p>
	 * DEVELOPMENT <p>
	 *  return true. <p>
	 * 
	 * @param environment
	 * @return true or false
	 */
	public abstract boolean contain(Environment environment);

	public String toString()
	{
		return name;
	}
}
