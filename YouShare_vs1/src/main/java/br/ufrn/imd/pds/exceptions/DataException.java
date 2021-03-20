package br.ufrn.imd.pds.exceptions;

import java.util.List;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}

	public DataException( String message ) {
		super( message );
	}
	
	public DataException( List<String> exceptionMessages ) {
		for( String e: exceptionMessages ) {
			System.out.println(e);
		}		
	}	
}
