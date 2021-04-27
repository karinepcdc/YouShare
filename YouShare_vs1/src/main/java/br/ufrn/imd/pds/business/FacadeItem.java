package br.ufrn.imd.pds.business;

import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public interface FacadeItem {
	
	/// Create a YouShare system item and add it to the database
	/*
	 * By default, new item grade and gradeCount are zero and a default lastreview is setted
	 * @return id of item created.
	 */
	public String createItem( Item newItem ) throws BusinessException, DataException;
	
	/// Read a YouShare system item and return a string with it's relevant data
	public Item readItem( String code ) throws BusinessException, DataException;
	
	/// Return all items registered.
	public List<Item> readAll( );	
		
	/// Return all items registered by user owner.
	public List<Item> readAll( String owner ) throws BusinessException;	
	
	/// Return all items registered by name with filters.
	public List<Item> readAll( List<String> name, List<String> filters) throws BusinessException, DataException;	
	
	/// Require that a YouShare item be updated from database
	/*
	 * @return id of item updated.
	 */
	public String updateItem( Item item ) throws BusinessException, DataException;
	
	/// Require that a YouShare item be removed from the database
	/*
	 * @return id of item deleted.
	 */
	public String deleteItem( Item item ) throws BusinessException, DataException;

	/// Require that YouShare remove all items from user from the database
	/*
	 * @return id of item deleted.
	 */
	public void deleteItem( String user ) throws BusinessException, DataException;

	/// Validate item.
	/*
	 * Check if all item fields required are non null and not empty;
	 * Required fields:
	 *  - item: name, description, price, isAvailable;
	 *  - SharedService: termOfUse, voltage
	 * Check if price is a double
	 * TODO Check if already exist in the database.???
	 * Check if owner is register and already has already reach 10 items ads
	 * Check in any field has excess a characters limit
	 * Check if voltage is 110, 220 or none
	 * 
	 * 
	 * TODO todos os checks
	 */
	public void validateItem ( Item item ) throws BusinessException;
	
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
	//public void addItemReview( String code, int grade, String review );

	/// If true, change to false and vice-versa.
	/*
	 * @return id of item created.
	 */
	public String changeAvailability ( String code ) throws BusinessException, DataException;

	public void validadeSearch(List<String> name, List<String> filters) throws BusinessException, DataException;
	
	/// Check if the item id is valid and if it registered to the user.
	public void validateId ( String code, String user ) throws BusinessException;

}
