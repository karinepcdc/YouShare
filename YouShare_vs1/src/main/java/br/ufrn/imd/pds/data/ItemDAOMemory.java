package br.ufrn.imd.pds.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.util.BDReader;

public class ItemDAOMemory implements ItemDAO {
	
	private HashMap<String, Item> itemMap;
	
	private static ItemDAOMemory uniqueInstance;
	
	/* Default constructor */
	public ItemDAOMemory() {
		System.out.println( "ItemDAOMemory's constructor \n" );
		
		// start user database
		itemMap = BDReader.csvToItemHashMap();
		
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			System.out.println("item: " + pair.getValue().getName() + "\n" );
		}
		
	}
	
	/* Singleton constructor */
	public static synchronized ItemDAOMemory getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new ItemDAOMemory();
		}
		return uniqueInstance;
	}

	
	public void createItem( String name, String description, int code, float itemGrade, ArrayList<Float> itemRatings, 
			ArrayList<String> itemReviews, boolean isAvailable, double price ) {
		
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
