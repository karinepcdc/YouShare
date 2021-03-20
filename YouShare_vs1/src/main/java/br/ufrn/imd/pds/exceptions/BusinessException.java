package br.ufrn.imd.pds.exceptions;

import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		super( message );
		// TODO Auto-generated constructor stub
	}

	public BusinessException(List<String> exeptionMessages) {
		// TODO temp
		for( String e: exeptionMessages ) {
			System.out.println(e);
		}
		
	}

	
	
	
}
