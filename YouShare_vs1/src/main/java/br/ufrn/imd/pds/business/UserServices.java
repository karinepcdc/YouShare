package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class UserServices implements FacadeUser {
	
	ArrayList<User> users;	
	
	public UserServices() {
		users = new ArrayList<User>();
	}
	
	@Override
	public void createUser( String firstName, String lastName , String userName ) {
		
		// cheack if user is already in the systems
		
		// if it is, throw exception
		
		// if not, create user  and add it to the data base
		
		// instanciate user ratings array and user review array
		ArrayList<Float> userRatings = new ArrayList<Float>();
		ArrayList<String> userReview = new ArrayList<String>();
		
		// create new user
		User newUser = new User( firstName, lastName, userName, 0, userRatings, userReview );
		users.add( newUser );
	}
	
	@Override
	public String readUser( User user  ) {
		String userStats = "";
		
		userStats = "All" + user.getFirstName() + " " + user.getLastName() + " stats:"
    			+ "\n"
    			+ "\n"
    			+ "";
		
		return userStats;
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
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
	public boolean verifyPassword(String userUserName, String passwd) {
		// TODO Auto-generated method stub
		
		return true;
		
	}

	
}
