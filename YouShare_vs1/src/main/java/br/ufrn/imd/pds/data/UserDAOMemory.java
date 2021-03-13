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
		BDWriter.userHashMapToCSV( userMap );				
		
		// TODO: apagar
		System.out.println( "Usuário criado! \n" ); 
	}

	@Override
	public User readUser( String userName ) {
		
		boolean isRegistered = userMap.containsKey( userName );
		
		if ( isRegistered ) {
			return userMap.get( userName );
		} else {
			// TODO: apagar
			System.out.println( "Usuário não está registrado! \n" ); 
			return null;
		}	
	}
	
	@Override
	public void updateUser( User user ) {
		User aux;
		boolean isRegistered = userMap.containsKey( user.getTelegramUserName() );
		
		if ( isRegistered ) {
			aux = userMap.get( user.getTelegramUserName() );
			aux = user;
			BDWriter.userHashMapToCSV( userMap );
		} else {
			// TODO: apagar
			System.out.println( "Usuário não está registrado! \n" ); 
		}
	}
	
	@Override
	public void deleteUser( User user ) {
		// TODO: apagar
		System.out.println("Usuário deletado!\n");

	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
		// TODO
	}

}
