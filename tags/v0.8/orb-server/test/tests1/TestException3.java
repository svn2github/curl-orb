package tests1;

public class TestException3 extends Exception
{
	private static final long serialVersionUID = 1L;

	public TestException3()
    {
        super();
    }

    public TestException3(String message)
    {
        super(message);
    }

    public TestException3(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }

    public TestException3(Throwable rootCause)
    {
        super(rootCause);
    }

}
