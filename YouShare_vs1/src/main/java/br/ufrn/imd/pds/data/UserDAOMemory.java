package br.ufrn.imd.pds.data;

import java.util.List;
import java.util.HashMap;

import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.util.CSVtoList;
import br.ufrn.imd.pds.util.ListToHashmap;

public class UserDAOMemory implements UserDAO {
	
	private List<User> listUsers;
	private HashMap<String, User> userMap;
	
	private static UserDAOMemory uniqueInstance;
	
	private UserDAOMemory() {
		listUsers.addAll( CSVtoList.csvToUserList() );
		userMap = ListToHashmap.createHashmapFromUserList( listUsers );
	}
	
	/* Singleton constructor */
	public static synchronized UserDAOMemory getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new UserDAOMemory();
		}
		
		return uniqueInstance;
	}
	
	
	@Override
	public void createUser( User newUser ) {
		
		listUsers.add( newUser );
		
		// TODO atualizar database
		
	}

	@Override
	public User readUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateUser( User user ) {
		// TODO
	}
	
	@Override
	public void deleteUser( User user ) {
		// TODO
	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
		// TODO
	}

}
