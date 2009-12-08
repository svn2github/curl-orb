package tests1;

import java.util.*;

import com.curlap.orb.security.RemoteService;
import com.curlap.orb.type.*;
import com.curl.io.serialize.types.*;

@RemoteService
public class GenerateTest 
{
    public GenerateTest()
    {
        // do nothing
    }
    
    public HashMap<Object, Object> method1(
    		HashMap<Object, Object> map1, 
    		Map<Object, Object> map2, 
    		Hashtable<Object, Object> map3
    )
    {
        return null;
    }
    
    public List<Object> method2(ArrayList<Object> list1, List<Object> list2)
    {
        return null;
    }
    
    public DateTime method3(DateTime date, ByteArray bytes)
    {
        return null;
    }
    
    public SerializableRecordSet method4(SerializableRecordSet rs, SerializableBinaryFile file)
    {
        return null;
    }
    
    public String[] method5(int[] a1, boolean[] a2, byte[] a3, double[] a4, float[] a5, 
            long[] a6, short[] a7, char[] a8, int[][] a9, String[] b1, DateTime[] b2, Object[] b3, Person[] b4)
    {
        return null;
    }
    
    public Object[] method6(Object[][] a1, Object[] a2, Object a3)
    {
        return null;
    }

    public DateTime[] method7(Object[][] a1, Object[] a2, Object a3)
    {
        return null;
    }
}
