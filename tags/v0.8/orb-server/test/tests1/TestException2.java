package tests1;

public class TestException2 extends Exception
{
	private static final long serialVersionUID = 1L;

    public TestException2()
    {
        super();
    }

    public TestException2(String message)
    {
        super(message);
    }

    public TestException2(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }

    public TestException2(Throwable rootCause)
    {
        super(rootCause);
    }

}
