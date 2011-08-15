package tests6;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@RemoteService
@Service("aopService")
public class AopServiceImpl implements AopService
{
	public String echo(String s)
	{
		return s;
	}
}
