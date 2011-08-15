package tests1;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("fuga")
@RemoteService
public class FugaImpl implements Fuga 
{

    public String method1() 
    {
        return "implements interface";
    }

    public String method2(String str, int i, boolean b) 
    {
        return str + i + b;
    }

    public String method3()
    {
        return "not override";
    }
}
