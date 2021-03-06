package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.User;

public interface UserDAO {
		
	public void createUser( User newUser );	

	/// Check if user is register in database and return it. If user is not found return null.
	public User readUser( String userName );

	public void updateUser( User user );
	
	public void deleteUser( User user ); // basta passar user id? no caso o telegramUserName ???
	
	public void reviewUser( String review, Float rating, User user ); // não seria melhor add user review ???
	
	
}
