package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.UserAlreadyRegisteredException;
import br.ufrn.imd.pds.exceptions.UserDatabaseCreationException;
import br.ufrn.imd.pds.exceptions.UserHeaderException;
import br.ufrn.imd.pds.exceptions.UserNotRegisteredException;

public class UserServices implements FacadeUser {	

	UserDAO userDatabase; // database manager class

	public UserServices() throws UserHeaderException, UserDatabaseCreationException {		
		// instantiate database
		userDatabase = UserDAOMemory.getInstance();
		
		System.out.println("UserServices criated!");
	}
	
	@Override
	public void createUser( String firstName, String lastName , String userName ) throws UserAlreadyRegisteredException {
		
		// check if user is already in the systems
		if( !isRegistered( userName ) ) {
			
			// create new user
			User newUser = new User( firstName, lastName, userName, "0", "0", "No reviews yet!" );
			userDatabase.createUser( newUser );
			
		} else {
			throw new UserAlreadyRegisteredException();
		}
		
	}
	
	@Override
	public String readUser( String userName  ) throws UserNotRegisteredException {
		
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
			throw new UserNotRegisteredException();
		}
	}
	
	@Override
	public void updateUser( String userName, String campo, String value ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser( String userName ) throws UserNotRegisteredException {
		
		User userToDelete = userDatabase.readUser( userName );
		
		// if user is register in the database
		if( userToDelete != null ) {
			userDatabase.deleteUser( userToDelete );
			
		} else {
			// TODO throw exception
			// User not registered
		}
	}
	
	@Override
	public void addUserReview( String userName, int grade, String review ) throws UserNotRegisteredException {

		// get user from database
		User userToUpdate = userDatabase.readUser( userName );
		
		// if user is register in the database
		if( userToUpdate != null ) {
			
			// get current user grade data 
			float currentUserGrade = Float.parseFloat( userToUpdate.getUserGrade() );
			
			userToUpdate.incrementUserGradeCount(); 
			float totalNumGrades = Float.parseFloat( userToUpdate.getUserGradeCount() );
			
			/*  compute progressive average: 
			 *  M_k = M_(k-1) + (x_k - M_(k-1))/k
			 */
			
			float updatedUserGrade = currentUserGrade + ( grade - currentUserGrade )/totalNumGrades;
					
			// update user grade average
			userToUpdate.setUserGrade( Float.toString( updatedUserGrade ) );
			
			// add review - and if review is empty???
			userToUpdate.setLastReview( review );
			
			// update user
			userDatabase.updateUser( userToUpdate );
			
		} else {
			// TODO throw exception
			// User not registered
		}
	
	}
	

	@Override
	public boolean isRegistered( String userName ) {
		return userDatabase.readUser( userName ) != null ;
	}

	
}
