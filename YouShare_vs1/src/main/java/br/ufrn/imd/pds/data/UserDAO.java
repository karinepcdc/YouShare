package br.ufrn.imd.pds.data;

import java.util.ArrayList;

import br.ufrn.imd.pds.business.User;

public interface UserDAO {
		
	public void createUser( User newUser );
	
	
	public String readUser( User user );
	
	public void updateUser(); // basta passar user id? no caso o telegramUserName ???
	
	public void deleteUser(); // basta passar user id? no caso o telegramUserName ???
	
	public void reviewUser( String review, Float rating, User user ); // não seria melhor insert user review ???
}
