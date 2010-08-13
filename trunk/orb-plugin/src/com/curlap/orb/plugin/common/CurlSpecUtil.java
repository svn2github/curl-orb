package com.curlap.orb.plugin.common;

import java.util.List;


public class CurlSpecUtil 
{
	private static boolean equalsOneOf(String type, String ... types)
	{
		for (String t : types)
			if (type.equals(t))
				return true;
		return false;
	}
	
	// marshal Curl data type
	//  e.g) boolean --> bool, int[] --> #{FastArray-of int}
	public static String marshalCurlType(
			String javaType,
			boolean isAllowNull, 
			boolean isCurlCodingStyle)
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
		if (v.startsWith("[L"))
			result = "{FastArray-of " + marshalCurlType(v.substring(2, v.length() - 1), isAllowNull, isCurlCodingStyle) + "}";
		else if (v.startsWith("["))
			result = "{FastArray-of " + marshalCurlType(v.substring(1), isAllowNull, isCurlCodingStyle) + "}";
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
		else if (equalsOneOf(v, "java.util.Map", "java.util.HashMap", "java.util.Hashtable", "Map", "HashMap", "Hashtable"))
			result = "HashTable";
		else if (equalsOneOf(v, "java.util.HashSet", "java.util.HashSet", "HashSet", "HashSet"))
			result = "Set";
		else 
			result = v;
		return (isAllowNull ? "#" : "") + result;
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
	
	
	
	
	/* test */
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
				List[].class,
				java.math.BigInteger.class
		};
		for (Class<?> c : types)
			print(c.getName(), "-->", marshalCurlType(c.getName(), true, true));
		
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
	}
}
