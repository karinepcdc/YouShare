package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.exceptions.DataException;

public interface UserDAO {		
	public void createUser( User newUser );	

	public User readUser( String userName );

	public void updateUser( User user ) throws DataException;
	
	public void deleteUser( User user ) throws DataException;
	
	public void addUserReview( String review, Float rating, User user ) throws DataException;	
}
