package tests4;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import org.springframework.stereotype.Service;

import tests1.TestException1;

import com.curl.io.serialize.types.ByteArray;
import com.curl.io.serialize.types.DateTime;
import com.curlap.orb.security.RemoteService;
import com.curlap.orb.type.CTimestamp;
import com.curlap.orb.type.SerializableRecordData;
import com.curlap.orb.type.SerializableRecordField;
import com.curlap.orb.type.SerializableRecordSet;

//This class is special for SerializableRecordSet test.
@Service("RecordSetTest")
@RemoteService
public class RecordSetTest 
{
	// constructor.1
	public RecordSetTest() { }

	// RecordSet test
	public SerializableRecordSet createRecordSet()
	{
		SerializableRecordSet records = new SerializableRecordSet(
				new SerializableRecordField[]{
						new SerializableRecordField("string", String.class),
						new SerializableRecordField("int", int.class),
						new SerializableRecordField("double", double.class),
						new SerializableRecordField("datetime", DateTime.class),
						new SerializableRecordField("timestamp", CTimestamp.class),
						new SerializableRecordField("bigint", com.curlap.orb.type.BigInteger.class),
						new SerializableRecordField("bigdec", com.curlap.orb.type.BigDecimal.class),
						new SerializableRecordField("bool", boolean.class),
						new SerializableRecordField("float", float.class),
						new SerializableRecordField("short", short.class),
						new SerializableRecordField("long",long.class),
						new SerializableRecordField("byte", byte.class),
						new SerializableRecordField("char", char.class),
						new SerializableRecordField("cdate",com.curlap.orb.type.CDate.class),
						new SerializableRecordField("date",com.curlap.orb.type.Date.class),
						new SerializableRecordField("blob",com.curlap.orb.type.Blob.class),
						new SerializableRecordField("clob",com.curlap.orb.type.Clob.class),
						new SerializableRecordField("time",com.curlap.orb.type.CTime.class)

				}
		);
		try{
			records.addRecord(
					new SerializableRecordData(
							new String[]{"string", "int", "double", "datetime", "timestamp", "bigint", "bigdec", "bool", "float", "short", "long", "byte", "char", "cdate", "date", "blob", "clob", "time"},
							new Object[]{"hokada", 32, 10.1, new DateTime(new Date(1258653800000L)), new Timestamp(0), new BigInteger("100"),new BigDecimal("23"),true,12.3,123,222222222222L,1,'c',new java.sql.Date(1238774400000L),new Date(1241452800000L),new SerialBlob(new byte[]{'a','b',1}),new SerialClob(new char[]{'-','r','%'}),new java.sql.Time(1238774400000L)}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"string", "int", "double", "datetime", "timestamp", "bigint", "bigdec", "bool", "float", "short", "long", "byte", "char", "cdate", "date", "blob", "clob", "time"},
							new Object[]{"amori", 27, 20000.01, new DateTime(new Date(1258653800000L)), new Timestamp(0), new BigInteger("200"),new BigDecimal("23"),true,12.3,123,222222222222L,1,'c',new java.sql.Date(1238774400000L),new Date(1241452800000L),new SerialBlob(new byte[]{'a','b',1}),new SerialClob(new char[]{'-','r','%'}),new java.sql.Time(1238774400000L)}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"string", "int", "double", "datetime", "timestamp", "bigint", "bigdec", "bool", "float", "short", "long", "byte", "char", "cdate", "date", "blob", "clob", "time"},
							new Object[]{"tyamamoto", 30, 0.0001, new DateTime(new Date(1258653800000L)), new Timestamp(0), new BigInteger("300"),new BigDecimal("23"),true,12.3,123,222222222222L,1,'c',new java.sql.Date(1238774400000L),new Date(1241452800000L),new SerialBlob(new byte[]{'a','b',1}),new SerialClob(new char[]{'-','r','%'}),new java.sql.Time(1238774400000L)}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"string", "int", "double", "datetime", "timestamp", "bigint", "bigdec", "bool", "float", "short", "long", "byte", "char", "cdate", "date", "blob", "clob", "time"},
							new Object[]{"foo", 20, 2.2, new DateTime(new Date(1258653800000L)), new Timestamp(0), new BigInteger("400"),new BigDecimal("23"),true,12.3,123,222222222222L,1,'c',new java.sql.Date(1238774400000L),new Date(1241452800000L),new SerialBlob(new byte[]{'a','b',1}),new SerialClob(new char[]{'-','r','%'}),new java.sql.Time(1238774400000L)}
					)
			);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return records;
	}

	public SerializableRecordSet[] createRecordSetArrayOne(){
		return new SerializableRecordSet[]{createRecordSet(),createRecordSet()};
	}

	public SerializableRecordSet[][] createRecordSetArrayTwo(){
		return new SerializableRecordSet[][]{createRecordSetArrayOne(),createRecordSetArrayOne()};
	}

	@SuppressWarnings("unchecked")
	public ArrayList createRecordSetList(){
		ArrayList recordsetList = new ArrayList();
		recordsetList.add(1);
		recordsetList.add(null);
		recordsetList.add(false);
		recordsetList.add("string");
		recordsetList.add(createRecordSet());
		return recordsetList;
	}

	@SuppressWarnings("unchecked")
	public HashMap createRecordSetHash(){
		ArrayList recordsetList = new ArrayList();
		recordsetList.add(1);
		recordsetList.add(null);
		recordsetList.add(false);
		recordsetList.add("string");
		recordsetList.add(createRecordSet());

		HashMap recordsetMap = new HashMap();
		recordsetMap.put(1,1);
		recordsetMap.put(2,true);
		recordsetMap.put(3,null);
		recordsetMap.put(4,"O.K");
		recordsetMap.put(5,createRecordSet());
		recordsetMap.put(6,recordsetList);
		return recordsetMap;
	} 

	@SuppressWarnings("unchecked")
	public HashSet createRecordSetSet(){
		ArrayList recordsetList = new ArrayList();
		recordsetList.add(1);
		recordsetList.add(null);
		recordsetList.add(false);
		recordsetList.add("string");
		recordsetList.add(createRecordSet());

		HashSet recordsetset = new HashSet();
		recordsetset.add(1);
		recordsetset.add(true);
		recordsetset.add(null);
		recordsetset.add("O.K");
		recordsetset.add(createRecordSet());
		recordsetset.add(recordsetList);
		return recordsetset;
	}

	@SuppressWarnings("unchecked")
	public Foo createIncludedRecordSetObject(){
		Foo foo = new Foo();
		// primitive
		foo.b = (byte) 1;
		foo.s = (short) 2;
		foo.i = (int) 3;
		foo.l = (long) 4;
		foo.f = (float) 5.0;
		foo.d = (double) 6.0;
		foo.x = true;
		foo.c = 'a';

		// java object to curl primitive
		foo.ob = new Byte((byte) 7);
		foo.os = new Short((short) 8);
		foo.oi = new Integer((int) 9);
		foo.ol = new Long((long) 10);
		foo.of = new Float((float) 11.0);
		foo.od = new Double((double) 12.0);
		foo.ox = false;
		foo.oc = 'b';

		// String
		foo.str = "string";

		// DateTime
		java.util.Calendar cal1 = java.util.Calendar.getInstance();
		cal1.set(2000, 7, 8);
		java.util.Calendar cal2 = java.util.Calendar.getInstance();
		cal2.set(2000, 7, 8, 1, 2, 3);
		foo.date1 = new DateTime(cal1.getTime());
		foo.date2 = new DateTime(cal2.getTime());

		// any
		foo.object = newHogeWithData();

		// FastArray-of
		foo.strs = new String[] { "string1", "string2", "string3" };
		foo.ints = new int[] { 1, 2, 3 };
		foo.fooArr = new Foo[] { new Foo(), new Foo(),
				new Foo() };
		foo.objects = new Object[] { 1, "string1", newHogeWithData() };
		foo.array_2 = new int[][] { { 0, 10 }, { 1, 11 }, { 2, 12 } };

		// Array-of
		foo.list = new ArrayList();
		foo.list.add(1);
		foo.list.add("string1");
		foo.list.add(newHogeWithData());

		foo.list_2 = new ArrayList();
		List l1 = new ArrayList();
		l1.add(1);
		l1.add("string1");
		l1.add(newHogeWithData());
		List l2 = new ArrayList();
		l2.add(2);
		l2.add("string2");
		l2.add(newHogeWithData());
		List l3 = new ArrayList();
		l3.add(3);
		l3.add("string3");
		l3.add(newHogeWithData());
		foo.list_2.add(l1);
		foo.list_2.add(l2);
		foo.list_2.add(l3);

		foo.pure_list = new ArrayList();
		foo.pure_list.add(1);
		foo.pure_list.add(2);
		foo.pure_list.add(3);

		// HashTable-of
		foo.map1 = new HashMap();
		foo.map1.put("key1-1", 11);
		foo.map1.put("key1-2", newHogeWithData());

		foo.map2 = new HashMap();
		foo.map2.put("key2-1", 21);
		foo.map2.put("key2-2", newHogeWithData());

		foo.table = new Hashtable();
		foo.table.put("key1-1", 11);
		foo.table.put("key1-2", newHogeWithData());

		foo.pure_map = new HashMap();
		foo.pure_map.put("key4-1", "str1");
		foo.pure_map.put("key4-2", "str2");

		// HashTable-of in FastArray-of
		foo.array_map = new HashMap[3];
		foo.array_map[0] = new HashMap();
		foo.array_map[0].put("key1-1", true);
		foo.array_map[1] = new HashMap();
		foo.array_map[1].put("key1-2", 123);
		foo.array_map[1].put("key2-2", 987);
		foo.array_map[2] = new HashMap();
		foo.array_map[2].put("key1-3", "string");

		// HashTable-of in Array-of
		Map m1 = new HashMap();
		m1.put("key1-1", true);
		Map m2 = new HashMap();
		m2.put("key1-2", 123);
		m2.put("key2-2", 987);
		Map m3 = new HashMap();
		m3.put("key1-2", "string");
		foo.list_map = new ArrayList();
		foo.list_map.add(m1);
		foo.list_map.add(m2);
		foo.list_map.add(m3);

		// FastArray-of in HashTable-of
		foo.map_array = new HashMap();
		foo.map_array.put("key1-1", new int[] { 1, 2, 3 });
		foo.map_array.put("key1-2", new boolean[] { true, false, true });
		foo.map_array.put("key1-3", new String[] { "str1", "str2", "str3" });

		// HashTable-of in HashTable-of
		foo.map_map = new HashMap();
		Map mm1 = new HashMap();
		mm1.put("key1-1", true);
		Map mm2 = new HashMap();
		mm2.put("key1-2", 123);
		Map mm3 = new HashMap();
		mm3.put("key1-3", "string");
		foo.map_map.put("k1", mm1);
		foo.map_map.put("k2", mm2);
		foo.map_map.put("k3", mm3);

		// zero data
		foo.zero_array = new String[0];
		foo.zero_list = new ArrayList();
		foo.zero_map = new HashMap();

		// null
		foo.str_null = null;
		foo.list_null = null;
		foo.array_null = null;

		// ByteArray
		foo.bytes = new ByteArray(new byte[] { (byte) 'f', (byte) 'o',
				(byte) 'o' });

		foo.string_Array = new String[][][] { { { "1", "2", "3" } },
				{ { "1", "2", "3" } } };

		//recordset 
		foo.recordset = createRecordSet();
		foo.recordsetArrayOne = new SerializableRecordSet[]{
				createRecordSet(),createRecordSet()
		};
		foo.recordsetArrayTwo = new SerializableRecordSet[][]{
				{createRecordSet(),createRecordSet()},
				{createRecordSet(),createRecordSet()}
		};
		foo.recordsetArrayList = createRecordSetList();
		foo.recordsetHash = createRecordSetHash();
		foo.recordsetSet = createRecordSetSet();


		return foo;
	}
	// RecordSet test to test large data
	public SerializableRecordSet createRecordSetToTestLargeData(int count) throws TestException1
	{
		// define table information from Class
		SerializableRecordSet records = new SerializableRecordSet(
				new SerializableRecordField[]{
						new SerializableRecordField("name", String.class),
						new SerializableRecordField("nichname", String.class),
						new SerializableRecordField("money", int.class),
						new SerializableRecordField("dt", DateTime.class)
				}        
		);
		//SerializableRecordField[] fields = records.getFields();
		List<Object> values = createTestLargeData(count);
		try
		{
			for (int i = 0; i < count; i++) {
				records.addRecord(
						new SerializableRecordData(
								new String[]{"name", "nichname", "money", "dt"},
								(Object[])values.get(i)
						)
				);
			}
		}
		catch (Exception e)
		{
			throw new TestException1(e);
		}
		return records;
	}

	// record data
	public List<Object> createTestLargeData(int count) throws TestException1
	{
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < count; i++)
			values.add(new Object[]{"namae" + i, "nich" + i, i, new DateTime(new Date())});
		return values;
	}	

	public Object echoFromClient(Object obj){
		return obj;
	}

	Hoge newHogeWithData() {
		Hoge hoge = new Hoge();
		hoge.setF1("string1");
		hoge.setF2(1);
		hoge.setF3(2.0);
		hoge.setF4(true);
		hoge.f5 = "TRANSIENT";
		return hoge;
	}
}
