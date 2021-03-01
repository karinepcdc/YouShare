package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class UserServices {
	
	ArrayList<User> users;
	
	
	public UserServices() {
		users = new ArrayList<User>();
	}
	
	public void createUser( String firstName, String lastName , String userName, String password ) {
		User user = new User( firstName, lastName, userName, password );
		users.add( user );
	}
	
	public String readUser( User user  ) {
		String userStats = "";
		
		userStats = "All" + user.getFirstName() + " " + user.getLastName() + " stats:"
    			+ "Do you have a login?\n\n"
    			+ "/login - login in YouShare.\n"
    			+ "/register - create a login.";
		
		return userStats;
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	public void reviewUser( String review, Float rating, User user ) {
		//user.setRatings(ratings);
	}
	
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
}
