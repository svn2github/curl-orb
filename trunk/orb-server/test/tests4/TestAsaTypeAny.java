package tests4;

import java.awt.List;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("testAsaTypeAny")
@RemoteService

public class TestAsaTypeAny {
		
	// hello
    public Object hello(String name) {
    	 return "Hello " + name;
    }
    // Person
    public Object person() {
    	return "JAME";
    }
    
    // goodBye
    public Object goodBye(String str , int n) {
    	List arry = new List();
    	arry.add(str);
    	arry.add(str, n);
    	return arry;
    }
}
