package br.ufrn.imd.pds.exceptions;

import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}

	public BusinessException( String message ) {
		super( message );
	}

	public BusinessException( List<String> exeptionMessages ) {
		for( String e: exeptionMessages ) {
			System.out.println(e);
		}		
	}	
}
