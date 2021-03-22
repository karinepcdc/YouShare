package br.ufrn.imd.pds.data;

import java.util.Map;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.HashMap;

import br.ufrn.imd.pds.DBHandlers.DBReader;
import br.ufrn.imd.pds.DBHandlers.DBWriter;
import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.exceptions.DataException;

public class UserDAOMemory implements UserDAO {
	
	private HashMap<String, User> userMap;
	
	private static UserDAOMemory uniqueInstance;
	
	private UserDAOMemory() throws DataException {
		
		System.out.println( "UserDAOMemory - Constructor \n" );
				
		userMap = DBReader.csvToUserHashMap();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			System.out.println("user: " + pair.getValue().getFirstName() + "\n" );
		}
	}
	
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
	public void createUser( User newUser ) throws DataException {
		
		userMap.put( newUser.getTelegramUserName(), newUser );
		try {
			DBWriter.userHashMapToCSV( userMap );
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			throw new DataException( "Problem trying to write new user in the database." );
		}				
		
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
	
		aux = userMap.get( user.getTelegramUserName() );
		aux.setFirstName		( user.getFirstName() );
		aux.setLastName			( user.getLastName() );
		aux.setTelegramUserName	( user.getTelegramUserName() );
		aux.setUserGrade		( user.getUserGrade() );
		aux.setUserGradeCount	( user.getUserGradeCount() );			
		aux.setLastReview		( user.getFirstName() );
			
		try {
			DBWriter.userHashMapToCSV( userMap );
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			throw new DataException( "Problem trying to write user in the database." );
		}
		System.out.println( "Usuário atualizado com sucesso. \n" );		
	}
	
	@Override
	public void deleteUser( User user ) throws DataException {
		
		userMap.remove( user.getTelegramUserName() );
		try {
			DBWriter.userHashMapToCSV( userMap );
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			throw new DataException( "Problem trying to write user in the database." );
		}

	}

}
