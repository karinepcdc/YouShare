package br.ufrn.imd.pds.business;

import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public interface FacadeItem {
	
	/// Criate a YouShare system item and add it to the database
	/*
	 * By default, new item grade and gradeCount are zero and a default lastreview is setted
	 * 
	 */
	public void createItem( Item newItem ) throws BusinessException, DataException;
	
	/// Read a YouShare system item and return a string with it's relevant data
	public Item readItem( String code ) throws BusinessException, DataException;
	
	/// Return all items regitered.
	public List<Item> readAll( );	
		
	/// Return all Tools regitered.
	public List<Tool> readAllTools( );	
	
	/// Require that a YouShare item be updated from database
	public void updateItem( Item item ) throws BusinessException, DataException;
	
	/// Require that a YouShare item be removed from database
	public void deleteItem( Item item ) throws BusinessException, DataException;
	
	/// Validate item.
	/*
	 * Check if all item fields required are non null and not empty;
	 * Required fields:
	 *  - item: name, description, price, isAvailable;
	 *  - tool: termOfUse, voltage
	 * Check if price is a double
	 * TODO Check if already exist in the database.???
	 * Check if owner is register and already has alredy reach 10 items ads
	 * Check in any field has excess a characteres limmmit
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
	public void changeAvailability ( String code ) throws BusinessException, DataException;
	
}
