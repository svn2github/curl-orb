package tests1;

public class TestException1 extends Exception
{
	private static final long serialVersionUID = 1L;

	private String exString = "ex-ex-ex";
	private int exInt = 1234;
	private String exNull = null;
	
    public String getExString() {
		return exString;
	}

	public void setExString(String exString) {
		this.exString = exString;
	}

	public int getExInt() {
		return exInt;
	}

	public void setExInt(int exInt) {
		this.exInt = exInt;
	}

	public String getExNull() {
		return exNull;
	}

	public void setExNull(String exNull) {
		this.exNull = exNull;
	}

	public TestException1()
    {
        super();
    }

    public TestException1(String message)
    {
        super(message);
    }

    public TestException1(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }

    public TestException1(Throwable rootCause)
    {
        super(rootCause);
    }

}
