package tests4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.curlap.orb.io.DoNotShare;
import com.curlap.orb.security.RemoteService;

@Service("perfTestService")
@RemoteService
public class PerfTestService {

	final Log log = LogFactory.getLog(getClass());
	
	@DoNotShare
	public List<LargeDto> getLargeDto(int num) {
		long startTime = System.currentTimeMillis();
		List<LargeDto> dtoList = new ArrayList<LargeDto>();
		for (int i = 0; i < num; i++) {
			int var = i % 5;
			LargeDto v = new LargeDto();
			v.id = var;
			v.name = "large dto" + var;
			v.b1 = (byte) var;
			v.b2 = Byte.valueOf((byte) var);
			v.c1 = (char) var;
			v.c2 = Character.valueOf((char) var);
			v.bd1 = new BigDecimal("10000000.0000001" + var);
			v.bd2 = (var % 2 == 0 ? null : new BigDecimal("20000000.0000002" + var));
			v.bd3 = new BigDecimal("30000000.0000003" + var);
			v.bd4 = (var % 2 == 0 ? null : new BigDecimal("40000000.0000004" + var));
			v.bd5 = new BigDecimal("50000000.0000005" + var);
			v.d1 = 10.1 + var;
			v.d2 = 10.2 + var;
			v.date = (var % 3 == 0 ? null : new Date()); 
			v.f1 = (float) 0.1 + var;
			v.f2 = Float.valueOf((float) 0.2 + var);
			v.i1 = var + 1;
			v.i2 = var + 2;
			v.i3 = var + 3;
			v.i4 = var + 4;
			v.i5 = var + 5;
			v.i6 = var + 6;
			v.i7 = var + 7;
			v.i8 = var + 8;
			v.i9 = var + 9;
			v.i10 = (var % 2 == 0 ? null : var + 10);
			v.l1 = var + 1;
			v.l2 = var + 2;
			v.l3 = var + 3;
			v.l4 = var + 4;
			v.l5 = var + 5;
			v.l6 = Long.valueOf(var + 1);
			v.l7 = Long.valueOf(var + 2);
			v.l8 = Long.valueOf(var + 3);
			v.l9 = (var % 2 == 0 ? null : Long.valueOf(var + 4));
			v.l10 = (var % 2 == 0 ? null : Long.valueOf(var + 5));
			v.str1 = "ABCDEFGHIJKL" + var + 1;
			v.str2 = "ABCDEFGHIJKL" + var + 2;
			v.str3 = (var % 2 == 0 ? null : "ABCDEFGHIJKL" + var + 3);
			v.str4 = "ABCDEFGHIJKL" + var + 4;
			v.str5 = "ABCDEFGHIJKL" + var + 5;
			v.str6 = "ABCDEFGHIJKL" + var + 6;
			v.str7 = "ABCDEFGHIJKL" + var + 7;
			v.str8 = (var % 2 == 0 ? null : "ABCDEFGHIJKL" + var + 8);
			v.str9 = "ABCDEFGHIJKL" + var + 9;
			v.str10 = "ABCDEFGHIJKL" + var + 10;
			v.z1 = true;
			v.z2 = false;
			dtoList.add(v);
		}
		log.fatal("[Elapsed time(LargeDto)]: " + (System.currentTimeMillis() - startTime));
		return dtoList;
	}
	
	public List<SmallDto> getSmallDto(int num) {
		long startTime = System.currentTimeMillis();
		List<SmallDto> dtoList = new ArrayList<SmallDto>();
		for (int i = 0; i < num; i++) {
			SmallDto v = new SmallDto();
			int var = i % 5;
			v.id = var;
			v.name = "Jusper" + var;
			v.content = "This is a contents.";
			v.number = var * 0.1;
			v.yes = true;
			dtoList.add(v);
		}
		log.fatal("[Elapsed time(SmallDto)]: " + (System.currentTimeMillis() - startTime));
		return dtoList;
	}
}
