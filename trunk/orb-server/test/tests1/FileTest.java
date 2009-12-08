package tests1;

import com.curlap.orb.security.RemoteService;
import com.curlap.orb.type.SerializableBinaryFile;
import com.curlap.orb.type.SerializableTextFile;

@RemoteService
public class FileTest 
{
    public FileTest()
    {
        // do nothing
    }

    public SerializableBinaryFile getBinaryFile()
    {
        try
        {
            return new SerializableBinaryFile("c:/test.jpg");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setBinaryFile(SerializableBinaryFile bytes)
    {
        try 
        {
            bytes.write("c:/test2.jpg");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public SerializableTextFile getTextFile()
    {
        try
        {
            return new SerializableTextFile("c:/test.csv", "SJIS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setTextFile(SerializableTextFile text)
    {
        try 
        {
            text.write("c:/test2.csv", "SJIS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
