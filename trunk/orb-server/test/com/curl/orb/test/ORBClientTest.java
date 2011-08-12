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

package com.curlap.orb.test;

import junit.framework.TestCase;
import tests1.Person;

import com.curlap.orb.client.ApplicationContextClient;
import com.curlap.orb.client.HttpSessionClient;
import com.curlap.orb.client.ORBClientException;

/**
 * ORBClientTest
 *   NOTE: Have to startup ORB server before testing.
 */
public class ORBClientTest extends TestCase {

	// test HttpSession 
	public void testHttpSession() throws Exception {
		// non static
		HttpSessionClient client = new HttpSessionClient("tests1.Foo", new Object[]{"abc"});
		for (int i = 0; i < 5; i++) {
			Person person = 
				(Person) client.invokeMethod("getPerson", new Object[]{"akira-mori", "akira", (double)10.0});
			checkPerson(person);
		}
		client.destroy();

		// static
		Integer i = (Integer) HttpSessionClient.invokeStaticMethod("tests1.HogeImpl", "getBarBar", new Object[]{});
		assertEquals(i, (Integer) 210);
	}

	// test ApplicationContext
	public void testApplicationContext() throws Exception {
		// non static
		ApplicationContextClient client = new ApplicationContextClient("foo1");
		for (int i = 0; i < 5; i++) {
			Person person = 
				(Person) client.invokeMethod("getPerson", new Object[]{"akira-mori", "akira", (double)10.0});
			checkPerson(person);
		}

		// static
		//  not support for now.
	}

	// test Exception
	public void testHttpSessionException() throws Exception {
		HttpSessionClient client = new HttpSessionClient("tests1.Foo", new Object[]{});
		try {
			client.invokeMethod("occurException2", new Object[]{});
			fail("unreachable due to exception");
			// TODO: ORBServerException?
		} catch (ORBClientException e) {
			e.printStackTrace();
			assertTrue(true);
		}
	}
	
	// test Exception
	public void testApplicationContextException() throws Exception {
		ApplicationContextClient client = new ApplicationContextClient("foo1");
		try {
			client.invokeMethod("occurException2", new Object[]{});
			fail("unreachable due to exception");
			// TODO: ORBServerException?
		} catch (ORBClientException e) { 
			assertTrue(true);
		}
	}

	// test some requests
	public void testSomeRequests() throws Exception {
		for (int i = 0; i < 10; i++) {
			testHttpSession();
			testApplicationContext();
		}
	}

	//  - - package - -
	void checkPerson(Person person) {
		assertEquals(person.getName(), "akira-mori");
		assertEquals(person.getNickname(), "akira");
		assertEquals(person.getBirthday(), 10.0);
	}
}
