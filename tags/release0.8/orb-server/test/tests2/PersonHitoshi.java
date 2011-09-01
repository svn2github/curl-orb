package tests2;

import tests1.Person;

public class PersonHitoshi extends Person implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String hobby;
   
    public String getHobby()
    {
        return hobby;
    }
    
    public void setHobby(String hobby)
    {
        this.hobby = hobby;
    }
}
