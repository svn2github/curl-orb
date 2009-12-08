package tests1;

import java.io.Serializable;

public class NullIndexDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long data;

	public NullIndexDto() {}
	public NullIndexDto(long data) {
		this.data   = data;
	}
	
	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
}
