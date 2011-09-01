package tests1;

import org.springframework.stereotype.Service;

import com.curl.orb.security.RemoteService;

@Service("piyo")
@RemoteService
public class PiyoImpl implements Piyo
{

    public String piyoField = "piyo field";
    
    // constructor
    public PiyoImpl()
    {
        // do nothing
    }
    
    public int interfaceMethod(String str, int i, boolean b, Hoge hoge)
    {
        return 0;
    }

    public void interfaceMethod1() 
    {
        System.out.println("Do interface Method1");
    }

    public String interfaceMethod2() 
    {
        return "run interfaceMethod2";
    }

    public int interfaceMethod3()
    {
        return 3;
    }
}
