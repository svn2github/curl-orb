package tests1;

import com.curl.orb.security.RemoteService;

@SuppressWarnings("serial")
@RemoteService
public class StreamTest implements java.io.Serializable 
{
	// Not support since 0.7
//    public StreamTest()
//    {
//        // do nothing
//    }
//
//    public void method1(String str, int i, SerializableStreamWriter writer)
//    {
//        try
//        {
//            writer.write(str);
//            writer.write(i);
//            //writer.write(new java.util.Date());
//            writer.close();
//        }
//        catch (SerializerException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void method2(SerializableStreamWriter writer)
//    {
//        try
//        {
//            writer.write("non-arguments' method");
//            writer.write(10000);
//            writer.write(20000);
//            //writer.write(new java.util.Date());
//            writer.close();
//        }
//        catch (SerializerException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static void method3(String str, SerializableStreamWriter writer)
//    {
//        try
//        {
//            writer.write("static " + str);
//            writer.write(30000);
//            writer.close();
//        }
//        catch (SerializerException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    // download
//    public void download(SerializableStreamWriter writer)
//    {
//        try
//        {
//            BinaryFileStreamWriter.write("test.jpg", 8192, writer);
////            BinaryFileStreamWriter.write("test.jpg", writer); // no use the buffer
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
}
