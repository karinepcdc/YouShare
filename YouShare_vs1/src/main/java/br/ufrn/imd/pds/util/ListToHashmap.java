package br.ufrn.imd.pds.util;

import java.util.List;
import java.util.HashMap;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

public class ListToHashmap {
	
	public static HashMap<String, User> createHashmapFromUserList( List<User> listUsers ) {
		System.out.println("Criando HashMap de userDatabase.csv...");
		HashMap<String,User> userMap = new HashMap<String,User>();
		
		for ( User user : listUsers ) {
			userMap.put( user.getTelegramUserName(), user );
		}
		
		return userMap;
	}
	
	public static HashMap<String, Item> createHashmapFromItemList( List<Item> listItems ) {
		System.out.println("Criando HashMap de itemDatabase.csv...");
		HashMap<String,Item> itemMap = new HashMap<String,Item>();
		
		for ( Item item : listItems ) {
			itemMap.put( String.valueOf( item.getCode() ), item );
		}
		
		return itemMap;
	}

}
