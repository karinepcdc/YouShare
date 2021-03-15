package br.ufrn.imd.pds.business;

public interface FacadeItem {
	
	/// Criate a YouShare system item and add it to the database
	/*
	 * By default, new item grade and gradeCount are zero and a default lastreview is setted
	 * 
	 */
	public void createItem( String name, String description , String code, String isAvailable, String price );
	
	/// Read a YouShare system item and return a string with it's relevant data
	public String readItem( String code );
	
	/// Require that a YouShare item be updated from database
	public void updateUser( String code, String campo, String value );
	
	/// Require that a YouShare item be removed from database
	public void deleteUser( String code );
	
	/// Register a item review and grade in the database.
	/* 
	 * Item Grade is calculated doing a progressive average.
	 * 
	 * Formula:
	 *  M_k = M_(k-1) + (x_k - M_(k-1))/k
	 *  
	 *  where:
	 *  M_k = average with k terms
	 *  x_k = kth term 
	 *  
	 */
	public void addItemReview( String code, int grade, String review );

	
}
