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

import com.curlap.orb.security.AccessException;
import com.curlap.orb.security.Environment;
import com.curlap.orb.security.RemoteService;
import com.curlap.orb.security.RemoteServiceAnnotationChecker;

/**
 * EnvironmentTest
 */
public class EnvironmentTest extends TestCase 
{
	// test production
	public void testProduction()
	{
		Environment production = Environment.PRODUCTION;
		assertTrue(production.contain(Environment.PRODUCTION));
		assertFalse(production.contain(Environment.TEST));
		assertFalse(production.contain(Environment.DEVELOPMENT));
		assertEquals(production.toString(), "production");
		try
		{
			RemoteServiceAnnotationChecker.check(ProductionService.class, Environment.PRODUCTION);
			RemoteServiceAnnotationChecker.check(ProductionService.class, Environment.TEST);
			RemoteServiceAnnotationChecker.check(ProductionService.class, Environment.DEVELOPMENT);
			RemoteServiceAnnotationChecker.check(ProductionService.class, null);
		} 
		catch (AccessException e)
		{
			fail("Cannot access to prodution.");
		}
	}

	// test test
	public void testTest()
	{
		Environment test = Environment.TEST;
		assertTrue(test.contain(Environment.PRODUCTION));
		assertTrue(test.contain(Environment.TEST));
		assertFalse(test.contain(Environment.DEVELOPMENT));
		assertEquals(test.toString(), "test");
		try
		{
			RemoteServiceAnnotationChecker.check(TestService.class, Environment.TEST);
			RemoteServiceAnnotationChecker.check(TestService.class, Environment.DEVELOPMENT);
			RemoteServiceAnnotationChecker.check(TestService.class, null);
		} 
		catch (AccessException e)
		{
			fail("Cannot access to test.");
		}
		try
		{
			RemoteServiceAnnotationChecker.check(TestService.class, Environment.PRODUCTION);
			fail("Cannot access to test");
		}
		catch (AccessException e)
		{ 
			assertTrue(true);
		}
	}

	// test development
	public void testDevelopement()
	{
		Environment development = Environment.DEVELOPMENT;
		assertTrue(development.contain(Environment.PRODUCTION));
		assertTrue(development.contain(Environment.TEST));
		assertTrue(development.contain(Environment.DEVELOPMENT));
		assertEquals(development.toString(), "development");
		try
		{
			RemoteServiceAnnotationChecker.check(DevelopmentService.class, Environment.DEVELOPMENT);
			RemoteServiceAnnotationChecker.check(DevelopmentService.class, null);
		} catch (AccessException e) { fail("Cannot access to test."); }
		try
		{
			RemoteServiceAnnotationChecker.check(DevelopmentService.class, Environment.PRODUCTION);
			fail("Cannot access to test");
		}
		catch (AccessException e) { assertTrue(true); }
		try
		{
			RemoteServiceAnnotationChecker.check(DevelopmentService.class, Environment.TEST);
			fail("Cannot access to test");
		} catch (AccessException e) { assertTrue(true); }
	}
	
	// test none
	public void testNone()
	{
		try
		{
			RemoteServiceAnnotationChecker.check(NoneService.class, null);
		} catch (AccessException e) { fail("Cannot access to none."); }
		try
		{
			RemoteServiceAnnotationChecker.check(NoneService.class, Environment.PRODUCTION);
			fail("Cannot access to test");
		} catch (AccessException e) { assertTrue(true); }
		try
		{
			RemoteServiceAnnotationChecker.check(NoneService.class, Environment.TEST);
			fail("Cannot access to test");
		} catch (AccessException e) { assertTrue(true); }
		try
		{
			RemoteServiceAnnotationChecker.check(NoneService.class, Environment.DEVELOPMENT);
			fail("Cannot access to test");
		} catch (AccessException e) { assertTrue(true); }
	}
	
	
	// - - Service classes - -
	
	@RemoteService(Environment.PRODUCTION)
	class ProductionService { }
	
	@RemoteService(Environment.TEST)
	class TestService {	}
	
	@RemoteService(Environment.DEVELOPMENT)
	class DevelopmentService { }

	// @RemoteService
	class NoneService {	}
}
