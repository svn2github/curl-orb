package tests6;

import org.springframework.stereotype.Service;

import com.curl.io.serialize.types.annotation.NotNull;
import com.curl.orb.security.RemoteService;

@Service
@RemoteService
public class ConcurrentService {

	@NotNull
	public String[] search(int from, int to) {
		String[] array = new String[to - from];
		for (int i = from; i < to; i++) {
			array[i - from] = "concurrent-tests " + i;
		}
		return array;
	}
}
