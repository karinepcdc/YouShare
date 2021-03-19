package br.ufrn.imd.pds.exceptions;

public class UserAlreadyRegisteredException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserAlreadyRegisteredException() {
		super( "The user is already registered. \n" );
	}

}