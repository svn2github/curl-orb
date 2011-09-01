package tests1;

//value class
public class PersonAkira extends Person implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

	private String hobby;
    
    // constructor.1
    public PersonAkira()
    {
        // do nothing
    }
    
    // constructor.2
    public PersonAkira(String hobby)
    {
        this.hobby = hobby;
    }
    
    public String getHobby()
    {
        return hobby;    
    }
    
    public void setHobby(String hobby)
    {
        this.hobby = hobby;
    }
}
