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

			// instanciate user ratings array and user review array
			ArrayList<Float> userRatings = new ArrayList<Float>();
			ArrayList<String> userReview = new ArrayList<String>();
			
			// create new user
			User newUser = new User( firstName, lastName, userName, 0, userRatings, userReview );
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
		user.getUserReviews().add( review );
		user.getUserRatings().add( rating );
	}
	
	@Override
	public void calculateUserGrade ( User user ) {
		float average = 0;
		float counter = 0;
		
		for( Float rating : user.getUserRatings() ) {
			average += rating;
			counter++;
		}
		
		average = average/counter;
		
		user.setUserGrade( average );
	}


	@Override
	public boolean isRegistered(String userName) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
