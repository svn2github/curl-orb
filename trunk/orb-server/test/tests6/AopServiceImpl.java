package tests6;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@RemoteService
@Service("aopService")
public class AopServiceImpl implements AopService
{
	public String echo(String s)
	{
		return s;
	}
}
