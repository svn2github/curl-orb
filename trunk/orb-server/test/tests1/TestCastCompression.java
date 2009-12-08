package tests1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("testCastCompression")
@RemoteService
public class TestCastCompression {

	private String fieldName;
	private Person person ;

	public Person getPerson() {
		person = new Person();
		person.setName("Mr.CMP");
		person.setBirthday(20.5);
		return person;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Map<Object, Object> testHashMap() {
		List<Object> testList = new ArrayList<Object>();
		testList.add("O.K.");
		testList.add(1);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("Test", testList);
		map.put("Test1", "end");
		map.put("Person", new Person());
		map.put("Person1", new Person());
		map.put("bool",false);
		return map;
	}
	public List<Object> testList() {
		List<Object> testList = new ArrayList<Object>();
		testList.add("LUCK");
		testList.add(1);
		testList.add(new Person());
		testList.add(new Person());
		testList.add(true);
		return testList;	
	}

	public  Double[] testdoubleArray() {
		Double[] DoubleArr = new Double[3];
		DoubleArr[0] = Double.valueOf((double) 0.0);
		DoubleArr[1] = Double.valueOf((double) -0.06667);
		DoubleArr[2] = Double.valueOf((double) 3.22255666);
		return DoubleArr;
	}

	public Set<Object> testSet() {
		Set<Object> set = new HashSet<Object>();
		set.add(1);
		set.add(null);
		set.add("sfsgfl121!!@");
		set.add(true);
		return set;
	}

	public String[][] testTwoString() {
		String[][] reString = {{"1","123"},{"hah","xixi"}};
		return reString;
	}
	
	public void throwTestException1() throws TestException1 {
		throw new TestException1("error!!");
	}
}
