package br.ufrn.imd.pds.data;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.util.BDReader;
import br.ufrn.imd.pds.util.BDWriter;

public class ItemDAOMemory implements ItemDAO {
	
	private HashMap<String, Item > itemMap;
	
	private static ItemDAOMemory uniqueInstance;
	
	/* Default constructor */
	public ItemDAOMemory() {
		System.out.println( "ItemDAOMemory's constructor started\n" );
		
		// start user database
		itemMap = BDReader.csvToItemHashMap();
		
	}
	
	/* Singleton constructor */
	public static synchronized ItemDAOMemory getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new ItemDAOMemory();
		}
		return uniqueInstance;
		
	}

	
	public void createItem( Item newItem ) {
		
		// add Item to item map
		itemMap.put( newItem.getCode() ,  newItem );
			
		// update database
		BDWriter.itemHashMapToCSV( itemMap );
		
		
	}
	
	public String readItem( Item item ) {
		return null;
		
	}
	
	public void updateItem() {
		
	}
	
	public void deleteItem() {
		
	}
	
	public void reviewItem( String review, Float rating, Item item ) {
		
	}

}
