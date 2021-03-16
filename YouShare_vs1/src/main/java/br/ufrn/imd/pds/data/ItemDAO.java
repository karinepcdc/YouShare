package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.Item;

public interface ItemDAO {
	
	/// Register newItem in the database
	public void createItem( Item newItem );
	
	/// Check if item is register in database and return it. If item is not found return null.
	public String readItem( Item item );
	
	/// Update item
	public void updateItem(Item item);
	
	/// Remove Item from the database
	public void deleteItem(Item item);
	
	/// 
	//public void addReviewItem( String review, Float rating, Item item );
}
