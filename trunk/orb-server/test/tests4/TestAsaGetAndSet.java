package tests4;

import java.awt.List;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("testAsaGetAndSet")
@RemoteService
public class TestAsaGetAndSet {
	
	private String testStr;
	private int testInt;
	private Person testPerson;
	private String testStr1;
	private int testInt1;
	private Person testPerson1;
	
	public String getTestStr() {
		return testStr;
	}
	public void setTestStr1(String testStr1) {
		this.testStr1 = testStr1;
	}
	public int getTestInt() {
		return testInt;
	}
	public void setTestInt1(int testInt1) {
		this.testInt1 = testInt1;
	}
	public Person getTestPerson() {
		return testPerson;
	}
	public void setTestPerson1(Person testPerson1) {
		this.testPerson1 = testPerson1;
	}
	
	// demo
    public void demo() {
    	String str = "hello" +  testStr1 + " " + testInt1 + " " + testPerson1;
    	System.out.print(str);
    }
    
    // saly
    public Object saly(String str , int n) {
    	 
    	List arry = new List();
    	arry.add(str);
    	arry.add(str, n);
    	return arry;
    }
    
    // goodBye
    public int goodBye(int i) {
    	return i;
    }
}
