package br.ufrn.imd.pds.data;

import java.util.ArrayList;

import br.ufrn.imd.pds.business.Item;

public class ItemDAOMemory implements ItemDAO {
	
	ArrayList<Item> items;
	String fileName;
	
	public ItemDAOMemory() {
		this.fileName = "itemDatabase.txt";
		items = new ArrayList<Item>();
		startDatabase();
	}
	
	public void startDatabase (){
		
		
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
