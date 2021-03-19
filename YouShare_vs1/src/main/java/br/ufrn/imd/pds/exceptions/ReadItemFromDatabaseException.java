package br.ufrn.imd.pds.exceptions;

public class ReadItemFromDatabaseException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ReadItemFromDatabaseException() {
		super( "An error occurred trying to read item database file. \n" );
	}

}
