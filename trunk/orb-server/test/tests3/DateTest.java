package tests3;

import com.curl.orb.security.RemoteService;

@RemoteService
public class DateTest {

	public DateDto getDto() {
		return new DateDto();
	}
}
