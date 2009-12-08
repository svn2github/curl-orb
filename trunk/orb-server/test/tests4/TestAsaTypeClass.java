package tests4;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("testAsaTypeClass")
@RemoteService
public class TestAsaTypeClass {

	// hello
    public String hello(String name){
    	 return "Hello " + name;
    }
    // Person
    public int goodBye(int i) {
    	return i;
    }
    
    // goodBye
    public Person person(String str , int n) {
    	 return new Person(str, n);
    }
}
