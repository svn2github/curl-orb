package tests5;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@RemoteService
@Service("secure")
public class SecureService {
	
	public String doSomething() {
		return "Did something!!";
	}
}
