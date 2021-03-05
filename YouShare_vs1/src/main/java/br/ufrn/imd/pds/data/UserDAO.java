package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.User;

public interface UserDAO {	
	public void createUser( String firstName, String lastName , String password );
	public String readUser( User user  );
	public void updateUser();
	public void deleteUser();
	public void reviewUser( String review, Float rating, User user );
}
