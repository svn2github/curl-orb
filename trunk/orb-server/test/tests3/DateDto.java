package tests3;

import com.curl.io.serialize.types.DateTime;

public class DateDto {
	public java.util.Date date = new java.util.Date();
	public java.sql.Date cdate = new java.sql.Date(10000);
	public java.sql.Time ctime = new java.sql.Time(10000);
	public java.sql.Timestamp ctimestamp = new java.sql.Timestamp(10000);
	public java.util.Date date1 = new java.sql.Date(10000);
	public java.util.Date date2 = new java.sql.Time(10000);
	public java.util.Date date3 = new java.sql.Timestamp(10000);
	public DateTime datetime = new DateTime();
}
