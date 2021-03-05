package br.ufrn.imd.pds.business;

public interface FacadeUser {
	
	/// Criate a YouShare system user and add it to the database
	/*
	 * By default, new users grade are zero and also review and rating arrays are empty.
	 * 
	 */
	public void createUser( String firstName, String lastName , String userName );
	
	/// Create a YouShare system user and require it is added to the database
	public String readUser( String userName );
	
	/// Require that a YouShare user be updated from database
	public void updateUser( String userName );
	
	/// Require that a YouShare user be removed from database
	public void deleteUser( String userName );
	
	
	public void addUserReview( User user, float rating, String review );
	
	/// Calculate user Grade doing a progressive average
	public void updateUserGrade ( String userName, int grade );
	
	
	/// Return true if user is already registered in the database
	public boolean isRegistered( String userName );
	
}
