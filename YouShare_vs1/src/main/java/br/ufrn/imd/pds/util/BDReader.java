package br.ufrn.imd.pds.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

public class BDReader {
	
	public static HashMap<String,User> csvToUserHashMap (){
		
		System.out.println("Convertendo userDatabase.csv para instancias da classe User...");

		// check if database file exist, if not, create it
		File userDatabaseFile = new File("src/main/csv/userDatabase.csv");
		try {
		if( userDatabaseFile.createNewFile() ) {
			
			// write header
			try {
				FileWriter writer = new FileWriter( userDatabaseFile );
	            writer.write( "firstName,lastName,telegramUserName,userGrade,userGradeCount,lastReview\n");
	            writer.close();
				System.out.println("User database file created!\n");
	        } catch ( IOException e1 ) {
				System.out.println("An error has occurred trying to write the user database header.\n");
				e1.printStackTrace();
			} 		
		}
		} catch ( IOException e ) {
			System.out.println("An error occurred trying to create user database file.\n");
			e.printStackTrace();
		}
		
		// Prepare to read the file
		FileReader csvFile = null;	
		try { 
			csvFile = new FileReader( userDatabaseFile );
			System.out.println("File read!\n");
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<User> userList = new CsvToBeanBuilder( csvFile ).withType(User.class).build().parse();
		
		System.out.println("User list is empty? " + userList.isEmpty() + "\n" );
		System.out.println("Lista de usuários lida com sucesso.");
		
		HashMap<String,User> userMap = new HashMap<String,User>();
		
		for ( User user : userList ) {
			userMap.put( user.getTelegramUserName(), user );
			System.out.println("user: " + user.getFirstName() + "registered \n" );
		}
		
		System.out.println("HashMap de usuários criado com sucesso.");
		
		return userMap;
	}
	
	public static HashMap<String,Item> csvToItemHashMap (){
		System.out.println("Convertendo itemDatabase.csv para instancias da classe Item...");

		CSVReader csvReader = null; 
		try { 
			csvReader = new CSVReader( new FileReader( "src/main/csv/itemDatabase.csv" ) ); 
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Item> itemList = new CsvToBeanBuilder( csvReader ).withType(Item.class).build().parse();
		
		System.out.println("Lista de itens lida com sucesso.");
		
		HashMap<String,Item> itemMap = new HashMap<String,Item>();
		
		for ( Item item : itemList ) {
			itemMap.put( item.getCode(), item );
		}
		
		System.out.println("HashMap de usuários criado com sucesso.");
		
		return itemMap;
	}

}
