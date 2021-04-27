package br.ufrn.imd.pds.data;

import java.util.List;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.OfficeItems;
import br.ufrn.imd.pds.exceptions.DataException;

public interface ItemDAO {
	
	/// Read all item database (for now, only toolDatabase) and load items into an hashmap of items.
	public void startDatabase( ) throws DataException;
	
	/// Register newItem in the database.
	/*
	 * @param newItem item to be saved in the database.
	 * @return id of item created.
	 */
	public String createItem( Item newItem ) throws DataException;
	
	/// Check if item with id code is register in database and return it. If item is not found return null.
	/*
	 * @param code unique id code of the item.
	 */
	public Item readItem( String code );
	
	/// Return all items registered in the database.
	public List<Item> readAll( );	
	
	/// Return all items from user owner in the database.
	public List<Item> readAll( String owner );
	
	/// Return all items registered by name with filters.
	public List<Item> readAll( List<String> name, List<String> filters) throws DataException;
				
	/// Update item in database.
	/*
	 * @param item item to be updated in the database.
	 * @param params list of parameter tal will be updated.
	 * @return id of item updated.
	 */
	public String updateItem(Item item) throws DataException;
	
	/// Remove Item from the database.
	/*
	 * @ item item to be removed.
	 * @return id of item deleted.
	 */
	public String deleteItem(Item item) throws DataException;
	
	/// Remove all user's Items from the database.
	/*
	 * @param user username of the user 
	 * 
	 */
	public void deleteItem(String user) throws DataException;
	
	
	
	/// 
	//public void addReviewItem( String review, Float rating, Item item );
}
