package tests5;

/*
 * AuthException 
 *   NOTE: extends RuntimeException due to AOP
 */
public class AuthException extends RuntimeException {

	private static final long serialVersionUID = -4769503408097572247L;

	public AuthException()
	{
		super();
	}

	public AuthException(String message)
	{
		super(message);
	}

	public AuthException(String message, Throwable rootCause)
	{
		super(message, rootCause);
	}

	public AuthException(Throwable rootCause)
	{
		super(rootCause);
	}
}
