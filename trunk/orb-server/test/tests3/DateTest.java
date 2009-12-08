package tests3;

import com.curlap.orb.security.RemoteService;

@RemoteService
public class DateTest {

	public DateDto getDto() {
		return new DateDto();
	}
}
