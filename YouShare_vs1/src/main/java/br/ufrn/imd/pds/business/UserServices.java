package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import com.vdurmont.emoji.EmojiParser;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class UserServices implements FacadeUser {	

	UserDAO userDatabase;
	ItemDAO itemDatabase;
	UserValidator userValidationStrategy; // validation strategy for different subclasses of users


	public UserServices() throws DataException {		
		userDatabase = UserDAOMemory.getInstance();
		itemDatabase = ItemDAOMemory.getInstance();

		System.out.println( "UserServices created!" );
	}
	
	@Override
	public void createUser( User newUser ) throws BusinessException, DataException {
		
		validateUser( newUser );
		
		if( newUser instanceof DepartmentUser && !isRegistered( newUser.getTelegramUserName() ) ) {
			DepartmentUser departmentUserDB = new DepartmentUser();

			// copy fields
			departmentUserDB.setFirstName( ((DepartmentUser)newUser).getFirstName() );
			departmentUserDB.setLastName( ((DepartmentUser)newUser).getLastName() );
			departmentUserDB.setTelegramUserName( ((DepartmentUser)newUser).getTelegramUserName() );
									
			// restricted field
			departmentUserDB.setDepartment( ( (DepartmentUser)newUser).getDepartment() );
			
			// require item registration in the database
			userDatabase.createUser( departmentUserDB );										
		} 
		
		else {
			throw new BusinessException( "User is already registered or user type isn't right for this instance of the framework. \n" );
		}
		
	}
	
	@Override
	public User readUser( String userName  ) throws BusinessException, DataException {

		User userToRead = userDatabase.readUser( userName );

		if( userToRead != null ) {				
			return userToRead;						
		} else {
			throw new BusinessException( "The user you were trying to access is not registered in the database. \n" );
		}
	}
	
	@Override
	public void updateUser( User user ) throws BusinessException, DataException {
		
		// validate item
		validateUser( user );
					
		// check if user is registered
		if ( !isRegistered( user.getTelegramUserName() ) ) {			
			throw new BusinessException( "The user you were trying to access is not registered in the database. \n" );
		} 
		
		if( user instanceof DepartmentUser ) {
			DepartmentUser departmentUserDB = new DepartmentUser();

			// copy fields
			departmentUserDB.setFirstName( ((DepartmentUser)user).getFirstName() );
			departmentUserDB.setLastName( ((DepartmentUser)user).getLastName() );
			departmentUserDB.setTelegramUserName( ((DepartmentUser)user).getTelegramUserName() );
			
			// restricted field
			departmentUserDB.setDepartment( ( (DepartmentUser)user).getDepartment() );
					
			// require item registration in the database
			userDatabase.updateUser( departmentUserDB );											
		}
		
		else {
			throw new BusinessException( "User type isn't right for this instance of the framework. \n" );
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

		/*User userToUpdate = userDatabase.readUser( userName );
		
		if( userToUpdate != null && userToUpdate instanceof ShareItemNeighbor ) {
			
			double currentUserGrade = ( (ShareItemNeighbor)userToUpdate ).getUserGrade();
			
			( (ShareItemNeighbor)userToUpdate ).incrementUserGradeCount(); 
			double totalNumGrades = ( (ShareItemNeighbor)userToUpdate ).getUserGradeCount();
			
			/*  compute progressive average: M_k = M_(k-1) + (x_k - M_(k-1))/k
			 */			
			/* double updatedUserGrade = currentUserGrade + ( grade - currentUserGrade ) / totalNumGrades;
					
			( (ShareItemNeighbor)userToUpdate ).setUserGrade( updatedUserGrade );
			
			if ( review == null || review.isBlank() ) {
				throw new BusinessException( "A written review is required. \n" );
			}
			else {
				( (ShareItemNeighbor)userToUpdate ).setLastReview( review );
			}
			
			userDatabase.updateUser( userToUpdate );
			
		} else {*/
			throw new BusinessException ( "This method is not available in this instance of the framework. \n" );
		//}
	
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
		
		List<String> exceptionMessagesSpecific = this.userValidationStrategy.userValidator(user);
		if( !(exceptionMessagesSpecific.isEmpty()) ) {
			hasViolations = true;
			exceptionMessages.addAll( exceptionMessagesSpecific );
		}
		
		if( hasViolations ) {
			String errorMsg = "";
			
			for( String error: exceptionMessages ) {
				errorMsg += EmojiParser.parseToUnicode(":warning: ") + error + "\n";
			}
			
			throw new BusinessException( errorMsg );
		}
	}	
}
