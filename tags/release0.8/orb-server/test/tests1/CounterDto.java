package tests1;

import java.io.Serializable;

public class CounterDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long data;

	public CounterDto() {}
	public CounterDto(long data) {
		this.data   = data;
	}

	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
}
