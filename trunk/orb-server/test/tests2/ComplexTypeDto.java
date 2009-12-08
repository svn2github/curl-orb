package tests2;

public class ComplexTypeDto implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private java.math.BigInteger bigInteger;
	private java.math.BigDecimal bigDecimal;
	private java.util.Date date;
	private java.sql.Date sqlDate;
	private java.sql.Time sqlTime;
	private java.sql.Timestamp sqlTimestamp;
	private java.sql.Blob blob;
	private java.sql.Clob clob;
	
	public java.math.BigInteger getBigInteger() {
		return bigInteger;
	}
	public void setBigInteger(java.math.BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}
	public java.math.BigDecimal getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(java.math.BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public java.sql.Date getSqlDate() {
		return sqlDate;
	}
	public void setSqlDate(java.sql.Date sqlDate) {
		this.sqlDate = sqlDate;
	}
	public java.sql.Time getSqlTime() {
		return sqlTime;
	}
	public void setSqlTime(java.sql.Time sqlTime) {
		this.sqlTime = sqlTime;
	}
	public java.sql.Timestamp getSqlTimestamp() {
		return sqlTimestamp;
	}
	public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
		this.sqlTimestamp = sqlTimestamp;
	}
	public java.sql.Blob getBlob() {
		return blob;
	}
	public void setBlob(java.sql.Blob blob) {
		this.blob = blob;
	}
	public java.sql.Clob getClob() {
		return clob;
	}
	public void setClob(java.sql.Clob clob) {
		this.clob = clob;
	}
}