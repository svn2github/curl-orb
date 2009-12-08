package tests4;

public class Hoge implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	String f1;
    private int f2;
    protected double f3;
    public boolean f4;
    transient public String f5; //not serialize
    
    public Hoge()
    { 
        // do nothing
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public int getF2() {
        return f2;
    }

    public void setF2(int f2) {
        this.f2 = f2;
    }

    public double getF3() {
        return f3;
    }

    public void setF3(double f3) {
        this.f3 = f3;
    }

    public boolean getF4() {
        return f4;
    }

    public void setF4(boolean f4) {
        this.f4 = f4;
    }
}

