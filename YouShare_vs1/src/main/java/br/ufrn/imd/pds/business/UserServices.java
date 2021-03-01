package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class UserServices implements FacadeUser {
	
	ArrayList<User> users;	
	
	public UserServices() {
		users = new ArrayList<User>();
	}
	
	@Override
	public void createUser( String firstName, String lastName , String userName, String password ) {
		User user = new User( firstName, lastName, userName, password );
		users.add( user );
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
		user.getRatings().add( rating );
	}
	
	@Override
	public void calculateUserGrade ( User user ) {
		float average = 0;
		float counter = 0;
		
		for( Float rating : user.getRatings() ) {
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
