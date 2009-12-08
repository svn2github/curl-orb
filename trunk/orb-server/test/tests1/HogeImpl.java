package tests1;

import org.springframework.stereotype.Service;

import com.curlap.orb.security.RemoteService;

@Service("hoge1")
@RemoteService
public class HogeImpl implements Hoge
{
    public Person newPerson()
    {
        Person p = new Person();
        p.setName("hoge!!");
        p.setBirthday(1234);
        return p;
    }
    
    public static int getBarBar()
    {
        return 210;
    }
    
    private Phone phone = null; 
    public void setPhone(Phone p)
    {
        phone = p;
    }
    
    public Phone getPhone()
    {
        return phone;
    }
}
