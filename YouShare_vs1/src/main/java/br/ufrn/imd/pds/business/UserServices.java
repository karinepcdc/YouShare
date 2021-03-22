package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class UserServices implements FacadeUser {	

	UserDAO userDatabase;
	ItemDAO itemDatabase;


	public UserServices() throws DataException {		
		userDatabase = UserDAOMemory.getInstance();
		itemDatabase = ItemDAOMemory.getInstance();

		System.out.println( "UserServices created!" );
	}
	
	@Override
	public void createUser( User newUser ) throws BusinessException, DataException {
		
		validateUser( newUser );
		
		if( !isRegistered( newUser.getTelegramUserName() ) ) {
			
			userDatabase.createUser( newUser );
			
		} else {
			throw new BusinessException( "User is already registered. \n" );
		}
		
	}
	
	@Override
	public User readUser( String userName  ) throws BusinessException, DataException {
		
		User userToRead = userDatabase.readUser( userName );

		if( userToRead != null ) {			
			/*String userProfile = "";
			
			userProfile = "Name: " + userToString.getFirstName() + " " + userToString.getLastName() + "\n"
	    			+ "Grade: " + userToString.getUserGrade() + "\n"
	    			+ "Last review: \n" + userToString.getLastReview();*/
			
			return userToRead;
						
		} else {
			throw new BusinessException( "The user you were trying to access is not registered in the database. \n" );
		}
	}
	
	@Override
	public void updateUser( User user ) throws BusinessException, DataException {
		
		if ( !isRegistered( user.getTelegramUserName() ) ) {			
			userDatabase.updateUser( user );

		} else {

			throw new BusinessException( "The user you were trying to access is not registered in the database. \n" );
		}
		
	}

	@Override
	public void deleteUser( String userName ) throws BusinessException, DataException {
		
		User userToDelete = userDatabase.readUser( userName );
		
		if( userToDelete != null ) {
			userDatabase.deleteUser( userToDelete );
			
			// erase user items from database
			itemDatabase.deleteItem(userName);
			
		} else {
			throw new BusinessException( "The user you were trying to delete is not registered in the database. \n" );
		}
	}
	
	@Override
	public void addUserReview( String userName, int grade, String review ) throws BusinessException, DataException  {

		User userToUpdate = userDatabase.readUser( userName );
		
		if( userToUpdate != null ) {
			
			float currentUserGrade = Float.parseFloat( userToUpdate.getUserGrade() );
			
			userToUpdate.incrementUserGradeCount(); 
			float totalNumGrades = Float.parseFloat( userToUpdate.getUserGradeCount() );
			
			/*  compute progressive average: M_k = M_(k-1) + (x_k - M_(k-1))/k
			 */			
			float updatedUserGrade = currentUserGrade + ( grade - currentUserGrade ) / totalNumGrades;
					
			userToUpdate.setUserGrade( Float.toString( updatedUserGrade ) );
			
			if ( review == null || review.isBlank() ) {
				throw new BusinessException( "A written review is required. \n" );
			}
			else {
				userToUpdate.setLastReview( review );
			}
			
			userDatabase.updateUser( userToUpdate );
			
		} else {
			throw new BusinessException ( "The user you were trying to review is not registered in the database. \n" );
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
			String errorMsg = "";
			
			for( String error: exceptionMessages ) {
				errorMsg += error + "\n";
			}
			throw new BusinessException( errorMsg );
		}	
	}	
}
