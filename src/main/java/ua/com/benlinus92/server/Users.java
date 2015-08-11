package ua.com.benlinus92.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users {
	public List<User> users;
    public Users() {
        this.users = new ArrayList<User>();
    }
    
    public void addUser(String uid, String username) {
    	Iterator<User> it = users.iterator();
    	while(it.hasNext()) {
    		if(it.next().isUsernameEquals(username))
    			return;
    	}
    	users.add(new User(uid, username));
    }
}
