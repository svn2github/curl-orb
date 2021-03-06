// Copyright (C) 1998-2010, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curlap.orb.plugin.common;

import java.io.IOException;

import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.corext.javadoc.JavaDocCommentReader;

/**
 * Curl specification utility.
 * 
 * @author Yohsuke Sugahara, Hitoshi Okada
 * @since 0.8
 */
@SuppressWarnings("restriction")
public class CurlSpecUtil 
{
	private static boolean equalsOneOf(String str, String ... strs)
	{
		for (String t : strs)
			if (str.equals(t))
				return true;
		return false;
	}
	
	private static boolean startsWithOneOf(String str, String ... strs)
	{
		for (String t : strs)
			if (str.startsWith(t))
				return true;
		return false;
	}
	
	private static int indexOfGenericsComma(String generics)
	{
		char comma = ',';
		int index = generics.indexOf(comma);
		if (index != -1 && index != generics.lastIndexOf(comma))
		{
			boolean skip = false;
			for (int i = 0; i < generics.length(); i++)
			{
				char c = generics.charAt(i);
				if (c == '<') 
					skip = true;
				else if (c == '>') 
					skip = false;
				else
					if (!skip && c == comma) 
						return i; 
			}
		}
		return index;
	}
	
	// marshal Curl data type
	//  e.g) boolean --> bool, int[] --> #{FastArray-of int}
	public static String marshalCurlType(
			String javaType,
			boolean isAllowNull,
			boolean generateGenerics)
	{
		String v = javaType;
		if (equalsOneOf(v, null, "null", "void", "V"))
			return "void";
		else if (equalsOneOf(v, "java.lang.Object", "Object"))
			return "any";
		else if (equalsOneOf(v, "java.lang.Character", "Character", "char", "C"))
			return "char";
		else if (equalsOneOf(v, "java.lang.Boolean", "Boolean", "boolean", "Z"))
			return "bool";
		else if (equalsOneOf(v, "java.lang.Byte", "Byte", "byte", "B"))
			return "int8";
		else if (equalsOneOf(v, "java.lang.Short", "Short", "short", "S"))
			return "int16";
		else if (equalsOneOf(v, "java.lang.Integer", "Integer", "int", "I"))
			return "int";
		else if (equalsOneOf(v, "java.lang.Long", "Long", "long", "J"))
			return "int64";
		else if (equalsOneOf(v, "java.lang.Double", "Double", "double", "D"))
			return "double";
		else if (equalsOneOf(v, "java.lang.Float", "Float", "float", "F"))
			return "float";

		String result;
		if (v.endsWith("[]"))
			result = "{FastArray-of " + marshalCurlType(v.substring(0, v.length() - 2), isAllowNull, generateGenerics) + "}";
		else if (v.startsWith("[L"))
			result = "{FastArray-of " + marshalCurlType(v.substring(2, v.length() - 1), isAllowNull, generateGenerics) + "}";
		else if (v.startsWith("["))
			result = "{FastArray-of " + marshalCurlType(v.substring(1), isAllowNull, generateGenerics) + "}";
		else if (equalsOneOf(v, "java.lang.String", "String"))
			result = v;
		else if (equalsOneOf(v, "java.util.Date", "Date"))
			result = v;
		else if (equalsOneOf(v, "java.sql.Date"))
			result = "COM.CURLAP.ORB.TYPE.CDate";
		else if (equalsOneOf(v, "Time"))
			result = "COM.CURLAP.ORB.TYPE.CTime"; 
		else if (equalsOneOf(v, "Timestamp"))
			result = "COM.CURLAP.ORB.TYPE.CTimestamp";
		else if (equalsOneOf(v, "CLOB"))
			result = "COM.CURLAP.ORB.TYPE.CLOB";
		else if (equalsOneOf(v, "BLOB"))
			result = "COM.CURLAP.ORB.TYPE.BLOB";
		else if (equalsOneOf(v, "java.math.BigInteger", "BigInteger"))
			result = "COM.CURLAP.ORB.TYPE.BigInteger";
		else if (equalsOneOf(v, "java.math.BigDecimal", "BigDecimal"))
			result = "COM.CURLAP.ORB.TYPE.BigDecimal";
		else if (equalsOneOf(v, "java.util.List", "java.util.ArrayList", "List", "ArrayList"))
			result = "Array";
		else if (startsWithOneOf(v, "java.util.List", "java.util.ArrayList", "List", "ArrayList"))
		{
			// generics
			if (generateGenerics)
			{
				String generics = v.substring(v.indexOf('<') + 1, v.lastIndexOf('>'));
				result = "{Array-of "  + marshalCurlType(generics, isAllowNull, generateGenerics) + "}";
			}
			else
				result = "Array";
		}
		else if (equalsOneOf(v, "java.util.Map", "java.util.HashMap", "java.util.Hashtable", "Map", "HashMap", "Hashtable"))
			result = "HashTable";
		else if (startsWithOneOf(v, "java.util.Map", "java.util.HashMap", "java.util.Hashtable", "Map", "HashMap", "Hashtable"))
		{
			// generics
			if (generateGenerics)
			{
				String generics = v.substring(v.indexOf('<') + 1, v.lastIndexOf('>'));
				int comma = indexOfGenericsComma(generics);
				String key = marshalCurlType(generics.substring(0, comma).trim(), isAllowNull, generateGenerics);
				String val = marshalCurlType(generics.substring(comma + 1).trim(), isAllowNull, generateGenerics);
				result = "{HashTable-of "  + key + ", " + val + "}";
			}
			else
				result = "HashTable";
		}
		else if (equalsOneOf(v, "java.util.Set", "java.util.HashSet", "Set", "HashSet"))
			result = "Set";
		else if (startsWithOneOf(v, "java.util.Set", "java.util.HashSet", "Set", "HashSet"))
		{
			// generics
			if (generateGenerics)
			{
				String generics = v.substring(v.indexOf('<') + 1, v.lastIndexOf('>'));
				result = "{Set-of "  + marshalCurlType(generics, isAllowNull, generateGenerics) + "}";
			}
			else
				result = "Set";
		}
		else 
			result = v;
		return (isAllowNull ? "#" : "") + result;
	}
	public static String marshalCurlType(String javaType)
	{
		return marshalCurlType(javaType, true, false);
	}
	
	// marshal Curl data type from jdt style signature
	//  e.g) Z --> bool, [I --> #{FastArray-of int}
	public static String marshalCurlTypeWithSignature(
			String javaType,
			boolean isAllowNull,
			boolean generateGenerics) throws IllegalArgumentException
	{
		String v = (javaType != null ? Signature.toString(javaType) : "void");
		return marshalCurlType(v, isAllowNull, generateGenerics);
	}
	public static String marshalCurlTypeWithSignature(String javaType)
	{
		return CurlSpecUtil.marshalCurlTypeWithSignature(javaType, true, false);
	}
	
	// parameterized type or not
	public static boolean isCurlGenericsType(String curlType)
	{
		return equalsOneOf(curlType, "Array", "HashTable", "Set", "#Array", "#HashTable", "#Set");
	}
	
	// marshal curl package 
	//   e.g. com.curl.test.Foo --> COM.CURL.TEST
	public static String marshalCurlPackage(
			String javaTypeName,
			boolean isCurlCodingStype)
	{
		String v = javaTypeName;
		
		int index = v.lastIndexOf('.');
		if (index != -1)
		{
			String s = v.substring(0, index);
			return (isCurlCodingStype ? s.toUpperCase() : s);
		}
		return null;
	}
	
	// get class name from package name
	//   e.g. com.curl.test.Foo or COM.CURL.TEST.Foo --> Foo
	public static String getClassNameFromPackageName(String javaTypeName)
	{
		String v = javaTypeName;
		
		int index = v.lastIndexOf('.');
		if (index != -1)
		{
			String s = v.substring(index + 1);
			return s;
		}
		return v;
	}
	
	public static String getDefaultValue(String type)
	{
		String v = type;
		if (equalsOneOf(v, "char"))
			return "0x0000";
		else if (equalsOneOf(v, "bool"))
			return "false";
		else if (equalsOneOf(v, "int8", "int16", "int", "int32", "int64", "byte"))
			return "0";
		else if (equalsOneOf(v, "double", "float"))
			return "0.0";
		return "null";
	}
	
	// marshal curl name for method and property
	//   e.g. helloWorld --> hello-world
	public static String marshalCurlName(
			String javaName, 
			boolean isCurlCodingStyle)
	{
		String v = javaName;
		
		if (!isCurlCodingStyle)
			return v;
		
		StringBuffer buf = new StringBuffer();
		char pc = ' ';
		for (int i = 0; i < v.length(); i++)
		{
			char c = v.charAt(i);
			if (c >= 'A' && c <= 'Z' && i != 0)
				if (!((pc >= 'A' && pc <= 'Z') || (pc == '_' && i == 1)))
					buf.append(("-" + c).toLowerCase());
				else
					buf.append((c + "").toLowerCase());
			else
				buf.append((c + "").toLowerCase());
			pc = c;
			
		}
		return buf.toString();
	}
	
	
	// isGetter
	public static boolean isGetter(String methodName)
	{
		String v = methodName;
		return 
		(v.length() > 2 && startsWithOneOf(v, "is") && Character.isUpperCase(v.charAt(2))) || 
		(v.length() > 3 && startsWithOneOf(v, "has", "get") && Character.isUpperCase(v.charAt(3)));
	}
	
	// isSetter
	public static boolean isSetter(String methodName)
	{
		String v = methodName;
		return 
		(v.length() > 3 && startsWithOneOf(v, "set") && Character.isUpperCase(v.charAt(3)));
	}
	
	// get getter's or setter's name
	public static String getGetterOrSetterName(String name)
	{
		String v = name;
		if (isSetter(name))
			return Character.toLowerCase(v.charAt(3)) + v.substring(4);
		if (isGetter(name))
		{
			int index = (v.startsWith("is") ? 2 : 3);
			return Character.toLowerCase(v.charAt(index)) + v.substring(index + 1);
		}
		return null;
	}
	
	// get modifier
	public static String getCurlModifier(String modifier)
	{
		if (modifier.indexOf("private") >= 0)
			return "private";
		else if (modifier.indexOf("protected") >= 0)
			return "protected";
		else if (modifier.indexOf("public") >= 0)
			return "public";
		return "package";
	}
	
	// get curl doc-string
	public static String getCurlDocString(IMember iMember) throws JavaModelException
	{
		ISourceRange range = iMember.getJavadocRange();
		if (range == null)
			return null;

		IBuffer buf = iMember.getOpenable().getBuffer();
		JavaDocCommentReader reader =
			new JavaDocCommentReader(
					buf, 
					range.getOffset(), 
					range.getOffset() + range.getLength() - 1
			);
		StringBuffer javadocBuf = new StringBuffer();
		char[] buffer = new char[1024];
		int count;
		try 
		{
			while ((count= reader.read(buffer)) != -1)
				javadocBuf.append(buffer, 0, count);
		}
		catch (IOException e)
		{
			return null;
		}
		return javadocBuf.toString();
	}
	
	/* test */
	/*
	static void print(String ...strings)
	{
		for (String s : strings)
			System.out.print(s + " ");
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		Class<?>[] types = {
				int.class,
				boolean.class,
				Character.class,
				int[].class,
				List.class,
				List[].class,
				java.math.BigInteger.class
		};
		for (Class<?> c : types)
			print(c.getName(), "-->", marshalCurlType(c.getName(), true));

		print("-----------------------");
		
		for (Class<?> c : types) {
			String typeName = c.getCanonicalName();
			String typeSign = Signature.createTypeSignature(typeName, false);
			print(typeName, "-->", marshalCurlTypeWithSignature(typeSign, true));
		}
		
		print("-----------------------");
		
		String[] packageNames = {
				"com.curl.test.ClassName",
				"ClassName"				
		};
		for (String p : packageNames)
			print(p, "-->", marshalCurlPackage(p, true));
		
		print("-----------------------");
		
		String[] variables = {
				"hello",
				"HELLO",
				"helloWorld",
				"HelloWorld",
				"helloWorldCup",
				"DBUtil",
				"myDBUtil"
		};
		for (String v : variables)
			print(v, "-->", marshalCurlName(v, true));
		
		print("-----------------------");
		
		String[] getters = {
				"getHello",
				"getHelloHello",
				"get_hello", // NG
				"gethello", // NG
				"isHello",
				"hasHello",
				"get", // NG
				"is" // NG
		};
		for (String v : getters)
			print(v, "-->", isGetter(v) + "", getGetterOrSetterName(v));
		String[] setters = {
				"setHello",
				"setHelloHello",
				"getHello", // NG
				"set_hello", // NG
				"sethello", // NG
				"isHello", // NG
				"hasHello", // NG
				"set" // NG
		};
		for (String v : setters)
			print(v, "-->", isSetter(v) + "", getGetterOrSetterName(v));	

		print("-----------------------");
		
		print(getClassNameFromPackageName("COM.CURL.TEST.Foo"));
		print(getClassNameFromPackageName("Foo"));
		
		print("-----------------------");
		String[] genericsTypes = {
				"List<long>",
				"List<List<long>>",
				"List<List<List<Foo>>>",
				"Set<Foo>",
				"Set<Set<String>>",
				"Set<Set<Set<short>>>",
				"List<Set<List<String>>>",
			
				"Map<String, long>",
				"Map<String, HashMap<int, short>>",
				"Map<HashMap<int, short>, long>",
				"Map<HashMap<Foo, short>, HashMap<Foo, short>>",
				"Map<Set<Foo>, List<Foo>>",
				"Map<HashMap<Foo, HashMap<Foo, short>>, HashMap<Foo, HashMap<Foo, short>>>"
		};
		for (String v : genericsTypes)
			print(v, "-->", marshalCurlType(v, true));
	}
	*/
}
