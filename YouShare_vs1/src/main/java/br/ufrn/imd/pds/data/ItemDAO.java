package br.ufrn.imd.pds.data;

import br.ufrn.imd.pds.business.Item;

public interface ItemDAO {
	
	
	public void createItem( Item newItem );
	
	public String readItem( Item item );
	
	public void updateItem();
	
	public void deleteItem();
	
	public void reviewItem( String review, Float rating, Item item );
}
