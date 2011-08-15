package tests3;

import com.curl.orb.security.RemoteService;

@RemoteService
public class Sleeper 
{
	public void sleep(long s)
	{
		try
		{
			Thread.sleep(s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
