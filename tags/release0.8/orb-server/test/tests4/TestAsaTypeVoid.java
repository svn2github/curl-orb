package tests4;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("testAsaTypeVoid")
@RemoteService
public class TestAsaTypeVoid {
	
	private static final long serialVersionUID = 1L;

	// hello
    public void hello() {
    	System.out.print("hello");
    }
    // Person
    public void person() {
    	System.out.print("person");
    }
    
    // goodBye
    public void goodBye() {
    	System.out.print("goodBye");
    }
}
