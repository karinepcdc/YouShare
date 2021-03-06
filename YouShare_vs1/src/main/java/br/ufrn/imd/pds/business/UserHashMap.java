package br.ufrn.imd.pds.business;
import java.util.HashMap;

public class UserHashMap {
	public HashMap<String, User> usersMap;
	
	public UserHashMap() {
		usersMap = new HashMap<String, User>();
	}

	public HashMap<String, User> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(HashMap<String, User> usersMap) {
		this.usersMap = usersMap;
	}

}
