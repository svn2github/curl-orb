package tests3;

import tests1.TestException1;

import com.curl.orb.security.RemoteService;

@RemoteService
public class WrapperClassTester {
	
	public Dto downloadDto() {
		return new Dto();
	}
	
	public void uploadDto(Dto dto) throws TestException1 {
		assertValue(dto.getI(), 1);
		assertValue(dto.getIi(), 10);
		assertValue(dto.getL(), 2L);
		assertValue(dto.getLl(), 20L);
		assertValue(dto.getD(), (double) 1.1);
		assertValue(dto.getDd(), (double) 10.1);
		assertValue(dto.getS(), (short) 3);
		assertValue(dto.getSs(), (short) 30);
		assertValue(dto.getC(), 'c');
		assertValue(dto.getCc(), 'C');
		assertValue(dto.getZ(), true);
		assertValue(dto.getZz(), false);
	}
	
	private void assertValue(Object o1, Object o2) throws TestException1 {
		if (!o1.equals(o2))
			throw new TestException1("Error in " + getClass().getName());
	}
}
