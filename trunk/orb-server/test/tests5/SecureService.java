package tests5;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@RemoteService
@Service("secure")
public class SecureService {
	
	public String doSomething() {
		return "Did something!!";
	}
}
