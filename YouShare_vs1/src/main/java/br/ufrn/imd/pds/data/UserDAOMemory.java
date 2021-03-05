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
	
	@Override
	public void createUser( User newUser ) {
		
		users.add( newUser );
		
		// TODO atualzar database
		
	}

	@Override
	public User readUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateUser( User user ) {
		// TODO
	}
	
	@Override
	public void deleteUser( User user ) {
		// TODO
	}
	
	@Override
	public void reviewUser( String review, Float rating, User user ) {
		// TODO
	}




}
