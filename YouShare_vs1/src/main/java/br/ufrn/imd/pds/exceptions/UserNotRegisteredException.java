package br.ufrn.imd.pds.exceptions;

public class UserNotRegisteredException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserNotRegisteredException() {
		super( "The user is not registered. \n" );
	}

}
