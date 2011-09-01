package tests5;

import java.io.Serializable;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = -765246322793384526L;

	private String userId;
	
	private boolean hasLogined;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isHasLogined() {
		return hasLogined;
	}
	public void setHasLogined(boolean hasLogined) {
		this.hasLogined = hasLogined;
	}
}
