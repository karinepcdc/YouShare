package br.ufrn.imd.pds.business;

public interface FacadeUser {
	
	/// Criate a YouShare system user and add it to the database
	/*
	 * By default, new users grade are zero and also review and rating arrays are empty.
	 * 
	 */
	public void createUser( String firstName, String lastName , String userName );
	
	/// Read a YouShare system user and return a string with it's relevant data
	public String readUser( String userName );
	
	/// Require that a YouShare user be updated from database
	public void updateUser( String userName, String campo, String value );
	
	/// Require that a YouShare user be removed from database
	public void deleteUser( String userName );
	
	/// Register a user review and grade in the database.
	/* 
	 * User Grade is calculated doing a progressive average.
	 * 
	 * Formula:
	 *  M_k = M_(k-1) + (x_k - M_(k-1))/k
	 *  
	 *  where:
	 *  M_k = average with k terms
	 *  x_k = kth term 
	 *  
	 */
	public void addUserReview( String userName, int grade, String review );
		
	/// Return true if user is already registered in the database
	public boolean isRegistered( String userName );
	
}
