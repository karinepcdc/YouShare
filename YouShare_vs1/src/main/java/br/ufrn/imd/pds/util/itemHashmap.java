package br.ufrn.imd.pds.util;

import java.util.HashMap;

import br.ufrn.imd.pds.business.Item;

public class itemHashmap {
	public HashMap<String, Item> itemsMap;
	
	public itemHashmap() {
		itemsMap = new HashMap<String, Item>();
	}

	public HashMap<String, Item> getMapaNoticias() {
		return itemsMap;
	}

	public void setMapaNoticias(HashMap<String, Item> iMap) {
		this.itemsMap = iMap;
	}
}
