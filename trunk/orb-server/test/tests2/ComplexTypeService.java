package tests2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("complexTypeService")
@RemoteService
public class ComplexTypeService 
{
	public ComplexTypeDto echoComplexType(ComplexTypeDto dto) {
		Map<String, Map<String, String>> hashInHash = dto.getHashInHash();
		for (Map.Entry<String, Map<String, String>> e : hashInHash.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		return dto;
	}
	
	public BigInteger echoBigInteger(BigInteger bigInteger) {
		return bigInteger;
	}

	public BigDecimal echoBigDecimal(BigDecimal bigDecimal) {
		return bigDecimal;
	}

	public java.util.Date echoDate(java.util.Date date) {
		return date;
	}

	public Date echoCDate(Date sqlDate) {
		return sqlDate;
	}
	
	public Time echoCTime(Time sqlTime) {
		return sqlTime;
	}

	public Timestamp echoCTimestamp(Timestamp sqlTimestamp) {
		return sqlTimestamp;
	}
	
	public Blob echoBlob(Blob blob) {
		return blob;
	}
	
	public Clob echoClob(Clob clob) {
		return clob;
	}
	
	public List<BigDecimal> echoBigDecimals(List<BigDecimal> bigDecimals) {
		return bigDecimals;
	}
	
	public List<Blob> echoBlobs(List<Blob> blobs) {
		return blobs;
	}
	
	public Blob[] echoArrayOfBlob(Blob[] blobs) {
		return blobs;
	}
	
	public Blob[][] echoArray2OfBlob(Blob[][] blobs) {
		return blobs;
	}
	
	/* NOTE:
	 *  The array of primitive arguments isn't supported. 
	 *  (the return value is OK.)
	 */
	public long[] echoPrimitiveArray(String[] arg1, Long[] arg2) {
		long[] result = new long[arg2.length];
		for (int i = 0; i < arg2.length; i++)
		{
			result[i] = (long)arg2[i];
		}
		return result;
	}
}
