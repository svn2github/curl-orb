package tests1;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;
import com.curl.orb.type.CTimestamp;
import com.curl.orb.type.DataTypeException;
import com.curl.orb.type.SerializableRecordData;
import com.curl.orb.type.SerializableRecordField;
import com.curl.orb.type.SerializableRecordSet;

import curl.language.date_time.DateTime;

//application context or session class
@Service("foo1")
@RemoteService
public class Foo extends SuperFoo {

	Person person;
	//PersonAkira akira;
	private String fieldName;
	public String publicTestField;
	public static String staticTestField = "static field";

	// constructor.1
	public Foo() {
		fieldName = "test field";
		person = new Person();
	}

	// constructor.2
	public Foo(String name) {
		fieldName = "test field";
		person = new Person();
		person.setName(name);
	}

	// constructor.3
	public Foo(String name, double birthday) {
		fieldName = "test field";
		person = new Person();
		person.setName(name);
		person.setBirthday(birthday);
	}
	
	// get data test
	public String getFieldName() {
		return fieldName;
	}

	// update data test
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	// user defined class test
	public Person getPerson() {
		person.setName("amori");
		person.setBirthday(123456);
		Phone phones[] = {new Phone(), new Phone()};
		phones[0].setName("home-phone");
		phones[0].setNumber("03-0000-0000");
		phones[1].setName("cell-phone");
		phones[1].setNumber("090-000-0000");
		person.setPhoneNumbers(phones);
		return person;
	}

	// user defined class test (same name)
	public Person getPerson(String name, String nickname, Double birthday) {
		person.setName(name);
		person.setNickname(nickname);
		person.setBirthday(birthday.doubleValue());
		return person;
	}

	// user defined class test (return subclass)
	public PersonAkira getPersonAkira(String name, String hobby) {
		PersonAkira akira = new PersonAkira();
		akira.setName(name);
		akira.setHobby(hobby);
		return akira;
	}

	// RecordSet test
	public SerializableRecordSet echoRecordSet(SerializableRecordSet recordSet) {
		return recordSet;
	}
	
	// RecordSet test
	// RecordSet test
	public SerializableRecordSet createRecordSet() throws DataTypeException
	{
		// define table information manually.
		SerializableRecordSet records = new SerializableRecordSet(
				new SerializableRecordField[]{
						new SerializableRecordField("name", String.class),
						new SerializableRecordField("age", int.class),
						new SerializableRecordField("money", double.class),
						new SerializableRecordField("dt", DateTime.class),
						new SerializableRecordField("ts", CTimestamp.class),
						new SerializableRecordField("bigint", com.curl.orb.type.BigInteger.class)
				}
		);
		records.addRecord(
				new SerializableRecordData(
						new String[]{"name", "age", "money", "dt", "ts", "bigint"},
						new Object[]{"hokada", 32, 10.1, new DateTime(), new Timestamp(0), new BigInteger("100")}
				)
		);
		records.addRecord(
				new SerializableRecordData(
						new String[]{"name", "age", "money", "dt", "ts", "bigint"},
						new Object[]{"amori", 27, 20000.01, new DateTime(), new Timestamp(0), new BigInteger("200")})
		);
		records.addRecord(
				new SerializableRecordData(
						new String[]{"name", "age", "money", "dt", "ts", "bigint"},
						new Object[]{"tyamamoto", 30, 0.0001, new DateTime(), new Timestamp(0), new BigInteger("300")})
		);
		records.addRecord(
				new SerializableRecordData(
						new String[]{"name", "age", "money", "dt", "ts", "bigint"},
						new Object[]{"foo", 20, 2.2, new DateTime(), new Timestamp(0), new BigInteger("400")})
		);
		return records;
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
			for (int i = 0; i < count; i++)
				records.addRecord(
						new SerializableRecordData(
								new String[]{"name", "nichname", "money", "dt"},
								(Object[])values.get(i)
						)
				);
		}
		catch (Exception e)
		{
			throw new TestException1(e);
		}
		return records;
	}

	// record data
	public List<Object> createTestLargeData(int count) throws TestException1 {
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < count; i++)
			values.add(new Object[]{"namae" + i, "nich" + i, i, new DateTime(new Date())});
		return values;
	}

	// sleep
	public boolean sleeping(int s) {
		try {
			Thread.sleep(s);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// echo/parrot
	public String echo(String s) {
		return s;
	}
	public DateTime echoDate(DateTime d) {
		return d;
	}
	
	// surrogate
	public String checkSurrogate(String s) throws Exception {
		String surrogateStr1 = new String(Character.toChars(0x0002000B));
		String surrogateStr2 = new String(Character.toChars(0x0002A6B2));
		String stStr = surrogateStr1 + "A" + surrogateStr2 + "‚ ";
		if (!stStr.equals(s))
			throw new Exception("Not change to surrogate pair!!");
		return stStr;
	}
	
	// exception
	public void occurException1() throws TestException1	{
		throw new TestException1("This is test exception1.");    
	}
	public void occurException2() throws TestException2	{
		throw new TestException2("This is test exception2.");    
	}
	public void occurException3() throws TestException3	{
		throw new TestException3("This is test exception3.");    
	}
	// not generate
	void noAccessorMethod() { }

	// for security tests
	protected void protectedMethod() { 
		packageMethod();
	}
	void packageMethod() {
		privateMethod();
	}
	private void privateMethod() { 
		// do nothing
	}
}
