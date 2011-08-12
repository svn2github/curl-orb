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

package com.curlap.orb.servlet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

import com.curlap.orb.common.InstanceManagementRequest;
import com.curlap.orb.io.DoNotShare;

/**
 * InstanceManagement Utility
 * 
 * @author Hitoshi Okada
 * @since 0.7
 */
public class InstanceManagementUtil 
{

	/* --- request/response utilities --- */
	
	/**
	 * Get InstanceManagementRequest.
	 */
	public static InstanceManagementRequest getRequest(HttpServletRequest httpRequest)
	{
		return (InstanceManagementRequest) httpRequest.getAttribute(InstanceManagementServlet.REQUEST_OBJECT);
	}
	
	/**
	 * Set InstanceManagementResponse.
	 */
	public static void setResponse(
			HttpServletRequest httpRequest,
			Object responseData,
			Map<String, Object> subordinateData
	)
	{
		httpRequest.setAttribute(
				InstanceManagementServlet.RESPONSE_OBJECT,
				responseData
		);
		httpRequest.setAttribute(
				InstanceManagementServlet.RESPONSE_SUBORDINATE_OBJECT, 
				subordinateData
		);
	}
	
	/**
	 * Get Subordinate Object
	 */
	public static Map<String, Object> getSurborinateObject(Method method) 
	{
        Map<String, Object> subordinateObject = new HashMap<String, Object>();
        subordinateObject.put("do-not-share", InstanceManagementUtil.isDoNotShare(method));
        return subordinateObject;
	}
	
	
	/* --- Instance utilities. (new, invoke ...etc) --- */
	
	/**
	 * Create new instance.
	 */
	public static Object newInstance(Class<?> cls, Object[] arguments) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException 
	{
		return ConstructorUtils.invokeConstructor(cls, arguments);
	}

	/**
	 * Invoke the method.
	 *  NOTE: MethodUtils doesn't support primitive arguments. e.g) echo(long[] v)
	 */
	public static Object invokeMethod(Object obj, String methodName, Object[] arguments) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException 
	{
		return MethodUtils.invokeMethod(obj, methodName, arguments);
	}
	public static Object invokeMethod(Method method, Object obj, Object[] arguments) throws IllegalAccessException, InvocationTargetException 
	{
		return method.invoke(obj, arguments);
	}

	/**
	 * Get Method.
	 */
	public static Method getMethod(Object obj, String methodName, Object[] arguments) throws NoSuchMethodException 
	{
		Method method =
			MethodUtils.getMatchingAccessibleMethod(obj.getClass(), methodName, getArgumentClasses(arguments));
        if (method == null)
        	throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object");
        return method;
	}
	
	/**
	 * Invoke the static method.
	 */
	public static Object invokeStaticMethod(Class<?> cls, String methodName, Object[] arguments) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException 
	{
		return MethodUtils.invokeStaticMethod(cls, methodName, arguments);
	}
	public static Object invokeStaticMethod(Method method, Class<?> cls, Object[] arguments) throws IllegalAccessException, InvocationTargetException 
	{
		return method.invoke(cls, arguments);
	}

	/**
	 * Get static Method.
	 */
	public static Method getStaticMethod(Class<?> cls, String methodName, Object[] arguments) throws NoSuchMethodException 
	{
		Method method =
			MethodUtils.getMatchingAccessibleMethod(cls, methodName, getArgumentClasses(arguments));
        if (method == null)
        	throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object");
        return method;
	}
	
	/** 
	 * check @DoNotShare annotation
	 */
	public static boolean isDoNotShare(Method method) 
	{
		return method.isAnnotationPresent(DoNotShare.class);
	}
	
	
	static Class<?>[] getArgumentClasses(Object[] arguments) 
	{
		if (arguments == null)
			return new Class[0];
		int len = arguments.length;
		Class<?>[] classes = new Class[len];
		for (int i = 0; i < len; i++)
		{
			classes[i] = arguments[i].getClass();
		}
		return classes;
	}

	
    /* --- getters/setters/fields utilities --- */
	
	/* (non-Javadoc) */
    public static boolean isGetter(Method method)
    {
        return (method.getName().startsWith("get") &&
                Character.isUpperCase(method.getName().charAt(3)) &&
                method.getParameterTypes().length == 0 &&
                method.getReturnType() != void.class);
    }
    
	/* (non-Javadoc) */
    public static boolean isSetter(Method method)
    {
        return (method.getName().startsWith("set") && 
                Character.isUpperCase(method.getName().charAt(3)) &&
                method.getParameterTypes().length == 1 && 
                method.getReturnType() == void.class);
    }

	/* (non-Javadoc) */
    public static String toFieldName(String name)
    {
        // from getter/setter to field name
    	StringBuilder builder = new StringBuilder(name.substring(3, 4).toLowerCase());
        builder.append(name.substring(4, name.length()));
        return builder.toString();
    }
    
    
    /* --- Generics utilities --- */
    
    /**
     * Get Generic types of fields.
     */
	public static Class<?>[] getGenericFieldTypes(Field field)
	{
		Type genericType = field.getGenericType();
		return getGenericTypes(genericType);
	}

    /**
     * Get Generic types of constructor's parameters.
     */
	public static Class<?>[][] getGenericConstructorParameterTypes(Constructor<?> constructor) 
	{
		Type[] genericType = constructor.getGenericParameterTypes();
		int len = genericType.length;
		Class<?>[][] genericTypes = new Class<?>[len][];
		for (int i = 0; i < len; i++)
			genericTypes[i] = getGenericTypes(genericType[i]);
		return genericTypes;
	}
	
    /**
     * Get Generic types of method's return value.
     */
	public static Class<?>[] getGenericMethodReturnValue(Method method) 
	{
		Type genericType = method.getGenericReturnType();
		return getGenericTypes(genericType);
	}

    /**
     * Get Generic types of method's parameters.
     */
	public static Class<?>[][] getGenericMethodParameterTypes(Method method) 
	{
		Type[] genericType = method.getGenericParameterTypes();
		int len = genericType.length;
		Class<?>[][] genericTypes = new Class<?>[len][];
		for (int i = 0; i < len; i++)
			genericTypes[i] = getGenericTypes(genericType[i]);
		return genericTypes;
	}

	
	static Class<?>[] getGenericTypes(Type type)
	{
		if(type instanceof ParameterizedType)
		{
			Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
			int len = typeArguments.length;
			Class<?>[] genericTypes = new Class<?>[len];
			// TODO 
			//   support nested generic types such as List<List<String>>.
			//   Should recursive geGenericType.
			for(int i = 0; i < len; i++) 
				genericTypes[i] = (Class<?>) typeArguments[i]; 
			return genericTypes;
		}
		return null;
	}
}
