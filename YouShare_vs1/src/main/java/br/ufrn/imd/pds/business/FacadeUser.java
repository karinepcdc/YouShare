package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public interface FacadeUser {
	
	/// Create a YouShare system user and add it to the database
	public void createUser( User newUser ) throws BusinessException, DataException;
	
	/// Read a YouShare system user and return a string with it's relevant data
	public String readUser( String userName ) throws BusinessException, DataException;
	
	/// Require that a YouShare user be updated from database
	public void updateUser( User user ) throws BusinessException, DataException;
	
	/// Require that a YouShare user be removed from database
	public void deleteUser( String userName ) throws BusinessException, DataException;
	
	/// Register a user review and grade in the database.
	public void addUserReview( String userName, int grade, String review ) throws BusinessException, DataException;
		
	/// Return true if user is already registered in the database
	public boolean isRegistered( String userName );
	
	public void validateUser( User user ) throws BusinessException;
	
}
