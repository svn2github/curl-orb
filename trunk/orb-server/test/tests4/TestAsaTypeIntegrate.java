package tests4;

import java.awt.List;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("testAsaTypeIntegrate")
@RemoteService
public class TestAsaTypeIntegrate {
	
	// hello
    public String hello(String name){
    	 return "Hello " + name;
    }
    // goodBye
    public int goodBye(int i) {
    	return i;
    }
    
    // Person
    public Person person(String str , int n) {
    	 return new Person(str, n);
    }
    
	// demo
    public void demo(){
    	System.out.print("hello");
    }
    
 // saly
    public Object saly(String str , int n) {
    	 
    	List arry = new List();
    	arry.add(str);
    	arry.add(str, n);
    	return arry;
    }
}
