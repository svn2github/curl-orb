package tests1;

//value class
public class Phone implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
    private String number;
    
    // constructors
    public Phone()
    {
        // do nothing
    }

    // setters/getters
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }
}
