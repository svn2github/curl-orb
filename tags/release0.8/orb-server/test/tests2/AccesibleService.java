package tests2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("accesibleService")
@RemoteService
public class AccesibleService {
	
	public void deserialize(NonAccesibleDataClass nadc) {
		// do nothing
	}

	public NonAccesibleDataClass serialize() {
		NonAccesibleDataClass v = new NonAccesibleDataClass();
		v.i = 100;
		v.s = "non-accessible";
		return v;
	}
	
	public void deserializeList(List<Object> nadc) {
		// do nothing
	}

	public List<NonAccesibleDataClass> serializeList() {
		List<NonAccesibleDataClass> list = new ArrayList<NonAccesibleDataClass>();
		for (int i = 0; i < 3; i++) {
			NonAccesibleDataClass v = new NonAccesibleDataClass();
			v.i = 100 * i;
			v.s = "non-accessible" + i;
			list.add(v);
		}
		return list;
	}
	
	public NonAccesibleDataClass echo(NonAccesibleDataClass nadc) {
		return nadc;
	}
}
