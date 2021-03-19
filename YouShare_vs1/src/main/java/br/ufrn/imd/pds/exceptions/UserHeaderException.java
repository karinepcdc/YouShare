package br.ufrn.imd.pds.exceptions;

public class UserHeaderException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserHeaderException() {
		super( "An error has occurred while trying to write the user database header. \n" );
	}
}
