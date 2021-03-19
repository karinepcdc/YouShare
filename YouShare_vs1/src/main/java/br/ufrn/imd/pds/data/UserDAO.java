package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.exceptions.UserNotRegisteredException;

public interface UserDAO {
		
	public void createUser( User newUser );	

	public User readUser( String userName );

	public void updateUser( User user ) throws UserNotRegisteredException;
	
	public void deleteUser( User user ) throws UserNotRegisteredException;
	
	public void addUserReview( String review, Float rating, User user ) throws UserNotRegisteredException;	
	
}
