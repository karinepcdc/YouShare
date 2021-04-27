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

		// define strategies
		userValidationStrategy = new ShareItemNeighborValidator();
	}
	
	@Override
	public void createUser( User newUser ) throws BusinessException, DataException {
		
		validateUser( newUser );
		
		if( newUser instanceof ShareItemNeighbor && !isRegistered( newUser.getTelegramUserName() ) ) {
			ShareItemNeighbor shareItemNeighborDB = new ShareItemNeighbor();

			// copy fields
			shareItemNeighborDB.setFirstName( ((ShareItemNeighbor)newUser).getFirstName() );
			shareItemNeighborDB.setLastName( ((ShareItemNeighbor)newUser).getLastName() );
			shareItemNeighborDB.setTelegramUserName( ((ShareItemNeighbor)newUser).getTelegramUserName() );
									
			// fill default review, grade and grade count
			shareItemNeighborDB.setLastReview("No reviews yet!");
			shareItemNeighborDB.setItemGrade(5);
			shareItemNeighborDB.setItemGradeCount(0);
			
			// require item registration in the database
			userDatabase.createUser( shareItemNeighborDB );
										
		} // TODO other users creation
		
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
		
		if( user instanceof ShareItemNeighbor ) {
			ShareItemNeighbor shareItemNeighborDB = new ShareItemNeighbor();

			// copy fields
			shareItemNeighborDB.setFirstName( ((ShareItemNeighbor)user).getFirstName() );
			shareItemNeighborDB.setLastName( ((ShareItemNeighbor)user).getLastName() );
			shareItemNeighborDB.setTelegramUserName( ((ShareItemNeighbor)user).getTelegramUserName() );
					
			// copy restricted fields
			shareItemNeighborDB.setLastReview( ((ShareItemNeighbor) user).getLastReview() );
			shareItemNeighborDB.setItemGrade( ((ShareItemNeighbor) user).getItemGrade() );
			shareItemNeighborDB.setItemGradeCount( ((ShareItemNeighbor) user).getItemGradeCount() );
					
			// require item registration in the database
			userDatabase.updateUser( user );
											
		} // TODO other users update		
		
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
		
		if( userToUpdate != null && userToUpdate instanceof ShareItemNeighbor ) {
			
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
