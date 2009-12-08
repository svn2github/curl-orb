package tests1;

import java.io.Serializable;

public class DoubleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private double data;

	public DoubleDto() {
	}
	public DoubleDto(double data) {
		this.data   = data;
	}

	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
}
