package tests4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.curlap.orb.type.SerializableRecordSet;

import curl.language.containers.ByteArray;
import curl.language.date_time.DateTime;

//import curl.serialize.types.data_access.base.DefaultRecordData;

public class Foo implements Serializable {

	// skip
	private static final long serialVersionUID = 1L;

	// primitive
	public byte b;
	public short s;
	public int i;
	public long l;
	public float f;
	public double d;
	public boolean x;
	public char c;

	// skip
	public transient String transientValue;
	public static String staticValue;

	public Byte ob;
	public Short os;
	public Integer oi;
	public Long ol;
	public Float of;
	public Double od;
	public Boolean ox;
	public Character oc;

	// String
	public String str;

	// DateTime
	public DateTime date1;
	public DateTime date2;

	// any 
	public Object object;

	// FastArray-of 
	public String[]strs;
	public int[] ints;
	public Foo[] fooArr;
	public Object[] objects;
	public int[][] array_2;

	// Array-of 
	public ArrayList<Object> list;
	public ArrayList<Object> list_2;
	public List<Object> pure_list;
	
	// HashTable-of any, any
	public HashMap<Object, Object> map1;
	public HashMap<Object, Object> map2;
	public Hashtable<Object, Object> table;
	public Map<Object, Object> pure_map;

	// HashTable-of in FastArray-of
	public HashMap<Object, Object>[] array_map;

	// HashTable-of in Array-of
	public ArrayList<Object> list_map;

	// FastArray-of in HashTable-of
	public HashMap<Object, Object> map_array;

	// HashTable-of in HashTable-of
	public HashMap<Object, Object> map_map;

	// zero data
	public String[] zero_array;
	public ArrayList<Object> zero_list;
	public HashMap<Object, Object> zero_map;

	// null
	public String str_null;
	public ArrayList<Object> list_null;
	public Object[] array_null;

    // ByteArray
	public ByteArray bytes;

	public String[][][] string_Array;
	
	public SerializableRecordSet recordset;
	public SerializableRecordSet[] recordsetArrayOne;
	public SerializableRecordSet[][] recordsetArrayTwo;
	public HashMap<Object, Object> recordsetHash;
	public ArrayList<Object> recordsetArrayList;
	public HashSet<Object> recordsetSet;
 
	public Foo() {}
}
