package tests4;

import com.curl.io.serialize.types.annotation.NotNull;

public class NonNullService1 {
	
	@NotNull
	public String genNonNull1() {
		return "";
	}
	@NotNull
	public Person getNonNull2() {
		return new Person("", 1);
	}
	
	public String genNull1() {
		return "";
	}
	public Person getNull2() {
		return new Person("", 1);
	}
}
