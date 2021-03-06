package br.ufrn.imd.pds.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

public class HashmapToList {
	
	public static ArrayList<User> createListFromUserHashmap( HashMap<String,User> userMap ) {
		ArrayList<User> userList = new ArrayList<User>();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			userList.add( pair.getValue() );
		}
		
		return userList;
	}
	
	public static ArrayList<Item> createListFromItemHashmap( HashMap<String,Item> itemMap ) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			itemList.add( pair.getValue() );
		}
		
		return itemList;
	}

}
