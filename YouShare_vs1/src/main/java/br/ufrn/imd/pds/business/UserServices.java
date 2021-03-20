package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class UserServices implements FacadeUser {	

	UserDAO userDatabase; // database manager class

	public UserServices() throws DataException {		
		userDatabase = UserDAOMemory.getInstance();
		
		System.out.println("UserServices created!");
	}
	
	@Override
	public void createUser( User newUser ) throws DataException {
		
		// check if user is already in the systems
		if( !isRegistered( newUser.getTelegramUserName() ) ) {
			
			// create new user
			userDatabase.createUser( newUser );
			
		} else {
			throw new DataException();
		}
		
	}
	
	@Override
	public String readUser( String userName  ) throws DataException {
		
		// get user
		User userToString = userDatabase.readUser( userName );

		// check if user is registered in YouShare systems 
		if( userToString != null ) {			
			String userProfile = "";
			
			userProfile = "Name: " + userToString.getFirstName() + " " + userToString.getLastName() + "\n"
	    			+ "Grade: " + userToString.getUserGrade() + "\n"
	    			+ "Last review: \n" + userToString.getLastReview();
			
			return userProfile;
						
		} else {
			throw new DataException();
		}
	}
	
	@Override
	public void updateUser( String userName, String campo, String value ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser( String userName ) throws DataException {
		
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
	public void addUserReview( String userName, int grade, String review ) throws DataException {

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
	
	@Override
	public void validateUser( User user ) throws BusinessException {
		List<String> exceptionMessages = new ArrayList<String>();		
		boolean hasViolations = false;
		
		if( user.getFirstName() == null || user.getFirstName().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add( "FirstName is required. \n" );
		} 
		
		if ( user.getLastName() == null || user.getLastName().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add( "LastName is required. \n" );
		}
		
		if( user.getTelegramUserName() == null || user.getTelegramUserName().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add( "TelegramUserName is required. \n" );
		}
		
		if( hasViolations ) {
			throw new BusinessException( exceptionMessages );
		}
		
	}

	
}
