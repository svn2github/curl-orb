package tests2;

import com.curl.orb.security.RemoteService;

import tests1.SuperFoo;

@RemoteService
public class HogeImpl extends SuperFoo implements Hoge {

	public String getHogeName() 
    {
        return "hoge hoge hoge.";
    }
}
