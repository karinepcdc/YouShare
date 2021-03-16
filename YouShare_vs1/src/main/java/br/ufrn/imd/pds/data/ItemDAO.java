package br.ufrn.imd.pds.data;

import java.util.List;

import br.ufrn.imd.pds.business.Item;

public interface ItemDAO {
	
	/// Register newItem in the database.
	/*
	 * @param newItem item to be saved in the database.
	 */
	public void createItem( Item newItem );
	
	/// Check if item with id code is register in database and return it. If item is not found return null.
	/*
	 * @param code unique id code of the item.
	 */
	public Item readItem( String code );
	
	/// Return all items regitered in the database.
	public List<Item> readAll( );	
	
	/// Update item in database.
	/*
	 * @param item item to be updated in the database.
	 * @param params list of parameter tal will be updated.
	 */
	public void updateItem(Item item, String[] params);
	
	/// Remove Item from the database.
	/*
	 * @ item item to be removed.
	 */
	public void deleteItem(Item item);
	
	/// 
	//public void addReviewItem( String review, Float rating, Item item );
}
