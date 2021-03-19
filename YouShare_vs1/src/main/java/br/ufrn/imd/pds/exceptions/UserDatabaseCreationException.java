package br.ufrn.imd.pds.exceptions;

public class UserDatabaseCreationException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserDatabaseCreationException() {
		super( "An error occurred while trying to create user database file. \n" );
	}
}