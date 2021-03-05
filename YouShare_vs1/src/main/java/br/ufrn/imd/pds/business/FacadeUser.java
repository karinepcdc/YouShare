package br.ufrn.imd.pds.business;

public interface FacadeUser {
	
	public void createUser( String firstName, String lastName , String userName, String password );
	
	public String readUser( User user );
	
	public void reviewUser( String review, Float rating, User user );
	
	public void calculateUserGrade ( User user );

	public boolean verifyPassword(String userName, String password);
}
