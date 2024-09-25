package app;

public class User {
	public String Username;
	public String userType;
	public int ID;
	public boolean canType;
	
	
	public User (String username, String type, int id) {
		this.Username = username;
		this.userType = type;
		this.ID = id;
		checkIfCanType();
	}
	
	public void checkIfCanType() {
		if(userType == "Moderator" || userType == "Registered") {
			this.canType = true;
		}
		else {
			this.canType = false;
		}
	}
	
	public void setCanUserType() {
		this.canType = true;
	}
	
	public void setCantUserType() {
		this.canType = false;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return Username;
	}
	
	public void setName(String name) {
		this.Username = name;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String type) {
		this.userType = type;
	}
	
	
}
