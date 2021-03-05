package br.ufrn.imd.pds.business;

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
			// User not registered
		}
		
	}
	
	@Override
	public String readUser( String userName  ) { // tá certo ???
		
		// get user
		User userToString = userDatabase.readUser( userName );

		// check if user is register in YouShare systems 
		if( userToString != null ) {			
			String userProfile = "";
			
			userProfile = "Name: " + userToString.getFirstName() + " " + userToString.getLastName() + "\n"
	    			+ "Grade: " + userToString.getUserGrade() + "\n"
	    			+ "Last review: \n" + userToString.getLastReview();
			
			return userProfile;
						
		} else {
			// TODO tratar exceção
			return "User not registered.";
		}
	}
	
	@Override
	public void updateUser(String userName, String campo, String value ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userName) {
		
		User userToDelete = userDatabase.readUser(userName);
		
		// if user is register in the database
		if( userToDelete != null ) {
			userDatabase.deleteUser( userToDelete );
			
		} else {
			// TODO throw exception
			// User not registered
		}
	}
	
	@Override
	public void addUserReview( String userName, int grade, String review ) {

		// get user from database
		User userToUpdate = userDatabase.readUser( userName );
		
		// if user is register in the database
		if( userToUpdate != null ) {
			
			// get current user grade data 
			float currentUserGrade = userToUpdate.getUserGrade();
			
			userToUpdate.incrementUserGradeCount(); 
			float totalNumGrades = userToUpdate.getUserGradeCount();
			
			/*
			 *  compute progressive average: 
			 *  M_k = M_(k-1) + (x_k - M_(k-1))/k
			 */
			float updatedUserGrade = currentUserGrade + ( grade - currentUserGrade )/totalNumGrades;
					
			// update user grade average
			userToUpdate.setUserGrade( updatedUserGrade );
			
			// add review - and if review is empty???
			userToUpdate.setLastReview(review);
			
			// update user
			userDatabase.updateUser( userToUpdate );
			
		} else {
			// TODO throw exception
			// User not registered
		}
	
	}
	

	@Override
	public boolean isRegistered(String userName) {
		return userDatabase.readUser( userName ) != null;
	}

	
}
