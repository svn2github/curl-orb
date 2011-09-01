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

package com.curl.orb.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.curl.orb.servlet.InstanceManagementUtil;

/**
 * ReflectionUtilTest
 */
public class ReflectionUtilTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public static byte [] string2byte(String from){
        int i;
        byte [] to = new byte [from.length() / 2];
        for (i = 0; i < from.length() / 2; i++) {
            to[i] = (byte) (Integer.parseInt(from.substring(2 * i, 2 * (i + 1)), 16) & 0xFF);
        }
        return to;
    }
    
    // Generics 
    public void testGenerics() throws Exception
    {
//    	System.out.println(string2byte("Jefe"));
    	for (byte b : "Jefe".getBytes())
    		System.out.println(b);
		// Constructor's parameters
//		Constructor<?> constructor = MyClass.class.getConstructor(List.class);
//		Class<?>[][] classes = InstanceManagementUtil.getGenericConstructorParameterTypes(constructor);
//		assertTrue(classes[0][0].equals(String.class));

		// Fields
		Field field = MyClass.class.getField("map");
		Class<?>[] classes1 = InstanceManagementUtil.getGenericFieldTypes(field);
		assertTrue(classes1[0].equals(Integer.class));
		assertTrue(classes1[1].equals(String.class));

		// Method's parameters
		Method method1 = MyClass.class.getMethod("setStringList", List.class, List.class, Map.class);
		Class<?>[][] classes2 = InstanceManagementUtil.getGenericMethodParameterTypes(method1);
		assertTrue(classes2[0][0].equals(String.class));
		assertTrue(classes2[1][0].equals(Integer.class));
		assertTrue(classes2[2][0].equals(Double.class));
		assertTrue(classes2[2][1].equals(Long.class));
		
		// Method's return-val
		Method method2 = MyClass.class.getMethod("getStringList", new Class[]{});
		Class<?>[] classes3 = InstanceManagementUtil.getGenericMethodReturnValue(method2);
		assertTrue(classes3[0].equals(String.class));
    }
    
    // isGetter
    public void testIsGetter() throws Exception
    {
        Class<?> cls = GetterTest.class;
        assertFalse("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getMethod0")));
        assertTrue("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getMethod1")));
        assertTrue("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getMethod2")));
        assertFalse("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getMethod3", int.class)));
        assertFalse("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getMethod4", int.class)));
        assertFalse("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("getmethod5")));
        assertFalse("isGetter test", InstanceManagementUtil.isGetter(cls.getMethod("noGetMethod")));
    }
    
    // isSetter
    public void testIsSetter() throws Exception
    {
        Class<?> cls = SetterTest.class;
        assertFalse("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setMethod0")));
        assertTrue("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setMethod1", int.class)));
        assertTrue("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setMethod2", String.class)));
        assertFalse("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setMethod3")));
        assertFalse("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setMethod4", int.class)));
        assertFalse("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("setmethod5", int.class)));
        assertFalse("isSetter test", InstanceManagementUtil.isSetter(cls.getMethod("noSetMethod", int.class)));
    }
    
    // toFieldName
    public void testToFieldName() throws Exception
    {
        assertEquals("toFieldName test", InstanceManagementUtil.toFieldName("getField1"), "field1");
        assertEquals("toFieldName test", InstanceManagementUtil.toFieldName("setField2"), "field2");
        assertEquals("toFieldName test", InstanceManagementUtil.toFieldName("setfield3"), "field3");
    }
    
    //
    class GetterTest
    {
        public void getMethod0(){} // no return value
        public int getMethod1(){return 0;}
        public String getMethod2(){return null;}
        public void getMethod3(int i){} // has args and no return value
        public int getMethod4(int i){return 0;} // has args
        public int getmethod5(){return 0;} // no upper case in third char.
        public int noGetMethod(){return 0;} // no "get"
    }
    
    class SetterTest
    {
        public void setMethod0(){} // no args
        public void setMethod1(int i){}
        public void setMethod2(String s){}
        public int setMethod3(){return 0;} // has return value and no args
        public int setMethod4(int i){return 0;} // has return value
        public void setmethod5(int i){} // no upper case in third char.
        public void noSetMethod(int i){} // no "get"
    }

    class MyClass {
    	
    	public List<String> list = new ArrayList<String>();
    	public Map<Integer, String> map = new HashMap<Integer, String>();
    	public List<List<String>> nestList = new ArrayList<List<String>>();
    	
    	public MyClass(List<String> strs) {
    		
    	}
    	
    	public List<String> getStringList() {
    		return list;
    	}
    	
    	public void setStringList(
    			List<String> strs1,
    			List<Integer> strs2, 
    			Map<Double, Long> map) {
    	}
    	
    	public Map<Integer, String> getMap() {
    		return map;
    	}
    }
}

