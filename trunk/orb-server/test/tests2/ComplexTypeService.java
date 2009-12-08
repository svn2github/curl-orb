package tests2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("complexTypeService")
@RemoteService
public class ComplexTypeService 
{
	public ComplexTypeDto echoComplexType(ComplexTypeDto dto) {
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
}
