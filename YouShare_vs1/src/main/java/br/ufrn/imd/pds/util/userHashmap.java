package br.ufrn.imd.pds.util;
import java.util.HashMap;

import br.ufrn.imd.pds.business.User;

public class userHashmap {
	public HashMap<String, User> usersMap;
	
	public userHashmap() {
		usersMap = new HashMap<String, User>();
	}

	public HashMap<String, User> getMapaNoticias() {
		return usersMap;
	}

	public void setMapaNoticias(HashMap<String, User> uMap) {
		this.usersMap = uMap;
	}
}
