package br.ufrn.imd.pds.data;

import java.util.List;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.exceptions.DataException;

public interface ItemDAO {
	
	/// Read all item database (for now, only toolDatabase) and load items into an hashmap of items.
	public void startDatabase( ) throws DataException;
	
	/// Register newItem in the database.
	/*
	 * @param newItem item to be saved in the database.
	 */
	public void createItem( Item newItem ) throws DataException;
	
	/// Check if item with id code is register in database and return it. If item is not found return null.
	/*
	 * @param code unique id code of the item.
	 */
	public Item readItem( String code ) throws DataException;
	
	/// Return all items regitered in the database.
	public List<Item> readAll( );	
	
	/// Return all Tools regitered in the database.
	public List<Tool> readAllTools( );	
		
	/// Update item in database.
	/*
	 * @param item item to be updated in the database.
	 * @param params list of parameter tal will be updated.
	 */
	public void updateItem(Item item) throws DataException;
	
	/// Remove Item from the database.
	/*
	 * @ item item to be removed.
	 */
	public void deleteItem(Item item) throws DataException;
	
	/// 
	//public void addReviewItem( String review, Float rating, Item item );
}
