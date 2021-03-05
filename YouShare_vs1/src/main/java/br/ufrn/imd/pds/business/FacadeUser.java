package br.ufrn.imd.pds.business;

public interface FacadeUser {
	
	/// Criate a YouShare system user and add it to the user array... tá certo isso???.
	/*
	 * By default, new users grade are zero and also review and rating arrays are empty.
	 * 
	 */
	public void createUser( String firstName, String lastName , String userName );
	
	public String readUser( User user );
	
	public void reviewUser( String review, Float rating, User user );
	
	public void calculateUserGrade ( User user );

	public boolean verifyPassword(String userName, String password);
}
