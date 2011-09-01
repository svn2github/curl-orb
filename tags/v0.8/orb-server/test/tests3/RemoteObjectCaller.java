package tests3;

import com.curl.orb.security.RemoteService;

@RemoteService
public class RemoteObjectCaller
{
	private RemoteObjectCallee callee;
	private RemoteObjectCallee[] callees;
	private String name;
	private int id;
	
	public RemoteObjectCaller(RemoteObjectCallee callee)
	{
		this.callee = callee;
	}
	
	public void setRemoteObjectCallee1(RemoteObjectCallee callee)
	{
		this.callee = callee;
	}

	public void setRemoteObjectCallee2(String name, RemoteObjectCallee callee, int id)
	{
		this.callee = callee;
		this.name = name;
		this.id = id;
	}

	public void setRemoteObjectCallee3(RemoteObjectCallee callee1, RemoteObjectCallee callee2)
	{
		callees = new RemoteObjectCallee[2];
		callees[0] = callee1;
		callees[1] = callee2;
	}
	
	// NOTE: No support 
//	public void setRemoteObjectCallee4(RemoteObjectCallee[] callees)
//	{
//		this.callees = new RemoteObjectCallee[callees.length];
//		for (int i = 0; i < callees.length; i++)
//			this.callees[i] = callees[i];
//	}
	
	public String[] getAllStatus()
	{
		String[] allStatus = new String[callees.length];
		for (int i = 0; i < callees.length; i++)
			allStatus[i] = callees[i].getStatus();
		return allStatus;
	}
	
	public String getStatus()
	{
		return callee.getStatus();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int id()
	{
		return id;
	}
}
