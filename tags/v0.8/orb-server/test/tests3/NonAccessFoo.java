package tests3;

import org.springframework.stereotype.Service;

@Service("nonAccessFoo")
public class NonAccessFoo 
{
    public NonAccessFoo()
    {
        // do nothing
    }
    
    public String disableAccess()
    {
        return "tests3";
    }
    
    public static String disableStaticAccess()
    {
        return "test3-static";
    }
}
