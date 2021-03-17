package br.ufrn.imd.pds.data;

import java.util.Map;
import java.util.HashMap;

import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.util.BDReader;
import br.ufrn.imd.pds.util.BDWriter;

public class UserDAOMemory implements UserDAO {
	
	private HashMap<String, User> userMap;
	
	private static UserDAOMemory uniqueInstance;
	
	/* Default constructor */
	private UserDAOMemory() {
		
		System.out.println( "UserDAOMemory's constructor \n" );
				
		// start user database
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
			// TODO: exception?
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
			aux.setFirstName		( user.getFirstName() );
			aux.setLastName			( user.getLastName() );
			aux.setTelegramUserName	( user.getTelegramUserName() );
			aux.setUserGrade		( user.getUserGrade() );
			aux.setUserGradeCount	( user.getUserGradeCount() );			
			aux.setLastReview		( user.getFirstName() );
			
			BDWriter.userHashMapToCSV( userMap );
			System.out.println( "Usuário atualizado com sucesso. \n" ); 
		} else {
			// TODO: exception?
			System.out.println( "Usuário não está registrado! \n" ); 
		}
		
	}
	
	@Override
	public void deleteUser( User user ) {
		boolean isRegistered = userMap.containsKey( user.getTelegramUserName() );
		
		if ( isRegistered ) {
			userMap.remove( user.getTelegramUserName() );
			BDWriter.userHashMapToCSV( userMap );
		} else {
			// TODO: exception?
			System.out.println( "Usuário não está registrado! \n" ); 
		}	

	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
		// TODO
	}

}
