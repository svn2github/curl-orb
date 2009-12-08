package tests1;

// value class
public class Person implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
    private double birthday;
    private String nickname;
    private Double clothes[];
    private Phone mainPhoneNumber;
    private Phone phoneNumbers[];
    public transient String transientField;
    public String publicField;

    // constructor.1
    public Person()
    {
        // do nothing
    }

    // constructor.2
    public Person(String name, double birthday, String nickname, Double[] clothes, Phone mainPhoneNumber, Phone[] phoneNumbers)
    {
        this.name = name;
        this.birthday = birthday;
        this.nickname = nickname;
        this.clothes = clothes;
        this.mainPhoneNumber = mainPhoneNumber;
        this.phoneNumbers = phoneNumbers;
    }

    // setter/getter
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setBirthday(double birthday)
    {
        this.birthday = birthday;
    }

    public double getBirthday()
    {
        return birthday;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setClothes(Double clothes[])
    {
        this.clothes = clothes;
    }

    public Double[] getClothes()
    {
        return clothes;
    }

    public void setMainPhoneNumber(Phone mainPhoneNumber)
    {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public Phone getMainPhoneNumber()
    {
        return mainPhoneNumber;
    }

    public void setPhoneNumbers(Phone phoneNumbers[])
    {
        this.phoneNumbers = phoneNumbers;
    }

    public Phone[] getPhoneNumbers()
    {
        return phoneNumbers;
    }
    
    public String getXxxx()
    {
        return "";
    }
    
    public void setXxxx(String v)
    {
        // do nothing       
    }
}
