package tests1;

import java.io.Serializable;

public class StringDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String data;

	public StringDto() {}
	public StringDto(String data) {
		this.data   = data;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
