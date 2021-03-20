package br.ufrn.imd.pds.data;

import java.util.Map;
import java.util.HashMap;

import br.ufrn.imd.pds.DBHandlers.DBReader;
import br.ufrn.imd.pds.DBHandlers.DBWriter;
import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.exceptions.DataException;

public class UserDAOMemory implements UserDAO {
	
	private HashMap<String, User> userMap;
	
	private static UserDAOMemory uniqueInstance;
	
	/* Default constructor */
	private UserDAOMemory() throws DataException {
		
		System.out.println( "UserDAOMemory's constructor \n" );
				
		// start user database
		userMap = DBReader.csvToUserHashMap();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			System.out.println("user: " + pair.getValue().getFirstName() + "\n" );
		}

	}
	
	/* Singleton constructor */
	public static synchronized UserDAOMemory getInstance() throws DataException {
		try {
			if( uniqueInstance == null ) {
				uniqueInstance = new UserDAOMemory();
			}
		} catch ( DataException e ) {
			e.printStackTrace();
		}
		return uniqueInstance;
	}
	
	@Override
	public void createUser( User newUser ) {
		
		userMap.put( newUser.getTelegramUserName(), newUser );
		DBWriter.userHashMapToCSV( userMap );				
		
		System.out.println( "Usuário criado! \n" );
	}

	@Override
	public User readUser( String userName ) {
		
		boolean isRegistered = userMap.containsKey( userName );
		
		if ( isRegistered ) {
			return userMap.get( userName );
		} else {
			return null;
		}	
	}
	
	@Override
	public void updateUser( User user ) throws DataException {
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
			
			DBWriter.userHashMapToCSV( userMap );
			System.out.println( "Usuário atualizado com sucesso. \n" ); 
		} else {

			throw new DataException();
		}
		
	}
	
	@Override
	public void deleteUser( User user ) throws DataException {
		boolean isRegistered = userMap.containsKey( user.getTelegramUserName() );
		
		if ( isRegistered ) {
			userMap.remove( user.getTelegramUserName() );
			DBWriter.userHashMapToCSV( userMap );
		} else {
			// TODO: exception?
			System.out.println( "Usuário não está registrado! \n" ); 
		}	

	}
	
	@Override
	public void addUserReview( String review, Float rating, User user ) throws DataException {
		if ( userMap.containsKey( user.getTelegramUserName() ) ) {
			
		}
		else {
			
		}
	}

}
