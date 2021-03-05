package br.ufrn.imd.pds.util;

import java.util.List;
import java.util.HashMap;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

public class CSVToHashmap {
	private userHashmap userCSV;
	private List<User> listUsers;
	private itemHashmap itemCSV;
	private List<Item> listItems;
	
	
	public CSVToHashmap() {
		userCSV = new userHashmap();
		listUsers = CSVHandler.csvToUser();
		
		itemCSV = new itemHashmap();
		listItems = CSVHandler.csvToItem();
	}
	
	public void createUserHashmapFromCSV( int minLength ) {
		System.out.println("Criando HashMap de userDatabase.csv...");
		HashMap<String,User> temp = new HashMap<String,User>();
		
		for ( User user : listUsers ) {
			temp.put( user.getTelegramUserName(), user );
		}
		
		userCSV.setUsersMap(temp);
		System.out.println("HashMap userCSV criado com sucesso!");
	}
	
	public void createItemHashmapFromCSV( int minLength ) {
		System.out.println("Criando HashMap de itemDatabase.csv...");
		HashMap<String,Item> temp = new HashMap<String,Item>();
		
		for ( Item item : listItems ) {
			temp.put( String.valueOf( item.getCode() ), item );
		}
		
		itemCSV.setItemsMap(temp);
		System.out.println("HashMap itemCSV criado com sucesso!");
	}
	
	public void imprimeCSV( HashMap<String, Object> map ) {
		map.forEach( (key, value) -> System.out.println( key + ":" + value) );
	}

}
