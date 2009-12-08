package tests2;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

// package accessor class
@Service("nonAccesibleService")
@RemoteService
class NonAccesibleService {
	public void doSomething() {
		// do nothing
	}
}
