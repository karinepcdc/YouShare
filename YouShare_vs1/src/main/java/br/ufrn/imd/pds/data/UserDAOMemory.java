package br.ufrn.imd.pds.data;

import java.util.Map;
import java.util.HashMap;

import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.util.BDReader;
import br.ufrn.imd.pds.util.BDWriter;

public class UserDAOMemory implements UserDAO {
	
	private HashMap<String, User> userMap;
	
	private static UserDAOMemory uniqueInstance;
	
	private UserDAOMemory() {
		
		System.out.println( "Construtor UserDAOMemory \n" );
		
		userMap = BDReader.csvToUserHashMap();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			System.out.println("user: " + pair.getValue().getFirstName() + "\n" );
		}

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
		
		userMap.put( newUser.getTelegramUserName(), newUser );
		BD
				
		
	
		System.out.println( "Usuário criado!\n" ); // TODO: apagar
	}

	@Override
	public User readUser( String userName ) {
		
		boolean isRegistered = userMap.containsKey( userName );
		
		if ( isRegistered ) {
			return userMap.get( userName );
		} else {
			System.out.println("Usuário não está registrado!\n"); // TODO: apagar
			return null;
		}	
	}
	
	@Override
	public void updateUser( User user ) {
		// TODO
	}
	
	@Override
	public void deleteUser( User user ) {
		// TODO
		System.out.println("Usuário deletado!\n"); // temp

	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
		// TODO
	}

}
