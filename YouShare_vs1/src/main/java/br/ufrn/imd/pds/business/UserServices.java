package br.ufrn.imd.pds.business;

import java.util.ArrayList;

import br.ufrn.imd.pds.data.UserDAOMemory;

public class UserServices implements FacadeUser {
	

	UserDAOMemory userDatabase; /// database manager class

	public UserServices() {
		
		// instantiate database
		userDatabase = UserDAOMemory.getInstance();
	}
	
	@Override
	public void createUser( String firstName, String lastName , String userName ) {
		
		// cheack if user is already in the systems
		if( !isRegistered( userName ) ) {
			
			// create new user
			User newUser = new User( firstName, lastName, userName, 0, 0, "No reviews yet!" );
			userDatabase.createUser( newUser );
			
		} else {
			// TODO throw exception
		}
		
	}
	
	@Override
	public String readUser( String telegramUserName  ) {
		
		// check if user is register in YouShare systems 
		if( isRegistered( telegramUserName ) ) {
			// TODO substituir null por userDatabase.find( telegramUserName )
			return userDatabase.readUser( null );
		
		} else {
			// TODO tratar exceção
			return "User not registered.";
		}
	}
	
	@Override
	public void updateUser(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addUserReview( String review, Float rating, User user ) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void calculateUserGrade ( User user ) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean isRegistered(String userName) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
