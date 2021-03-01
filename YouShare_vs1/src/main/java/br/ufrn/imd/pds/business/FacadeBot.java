package br.ufrn.imd.pds.business;

public interface FacadeBot {
	
	public void log( String userFirstName, String userLastName, String userId, String userMsgTxt, String botAnswer );
	
	public void processRegister( String userFirstName, String userLastName, String userId, String passwd );
	
	public void processLogin( String userFirstName, String userLastName, String userId, String passwd );	
	
}
