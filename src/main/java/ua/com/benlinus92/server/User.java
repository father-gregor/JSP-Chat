package ua.com.benlinus92.server;

public class User {
	private String user;
	private String id;
	public User(String id,String user) {
		this.id = id;
		this.user = user;
	}
	
	public boolean isUsernameEquals(String name) {
		if(this.user.equals(name))
			return true;
		return false;
	}
	
    @Override
    public boolean equals(Object obj) {
    	if(obj == null)
    		return false;
    	if(getClass() != obj.getClass())
    		return false;
    	final User us = (User) obj;
    	if(this.user.equals(us.user) && this.id.equals(us.id))
    		return true;
    	return false;
    }
}
