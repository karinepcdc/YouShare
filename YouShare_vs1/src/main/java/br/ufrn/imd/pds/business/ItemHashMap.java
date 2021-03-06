package br.ufrn.imd.pds.business;

import java.util.HashMap;

public class ItemHashMap {
	public HashMap<String, Item> itemsMap;
	
	public ItemHashMap() {
		itemsMap = new HashMap<String, Item>();
	}

	public HashMap<String, Item> getItemsMap() {
		return itemsMap;
	}

	public void setItemsMap( HashMap<String, Item> itemsMap ) {
		this.itemsMap = itemsMap;
	}
	
}
