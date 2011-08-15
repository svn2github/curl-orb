package tests1;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("nest")
@RemoteService
public class NestTester {
	
	public NestDto get() {
		NestDto dto = new NestDto();
		GrandChildDto[] gcs = new GrandChildDto[]{new GrandChildDto(), new GrandChildDto()};
		gcs[0].str = "grand-child-1";
		gcs[1].str = "grand-child-2";
		
		ChildDto cd = new ChildDto();
		cd.grandChilds = gcs;
		dto.childDto = cd;
		return dto;
	}
	
	public void set(NestDto dto) throws Exception {
		GrandChildDto[] gcs = dto.childDto.grandChilds;
		if (!(gcs[0].str.equals("grand-child-1-from-curl") && gcs[1].str.equals("grand-child-2-from-curl")))
			throw new Exception("Nested dto is wrong!!");
	}
}
