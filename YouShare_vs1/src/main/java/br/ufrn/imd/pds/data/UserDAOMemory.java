package br.ufrn.imd.pds.data;

import java.util.ArrayList;

import br.ufrn.imd.pds.business.User;

public class UserDAOMemory implements UserDAO {
	ArrayList<User> users;
	String fileName;
	
	private static UserDAOMemory uniqueInstance;
	
	private UserDAOMemory() {
		this.fileName = "userDatabase.txt";
		users = new ArrayList<User>();
		startDatabase();
	}
	
	/* Singleton constructor */
	public static synchronized UserDAOMemory getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new UserDAOMemory();
		}
		
		return uniqueInstance;
	}
	
	public void startDatabase (){
		
	 }
	
	public void createUser( User newUser ) {
		
		users.add( newUser );
		
		// TODO atualzar database
		
	}
	
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
	
	public void reviewUser( String review, Float rating, User user ) {
		
	}
	
	public void calculateUserGrade ( User user ) {
		
	}

}
