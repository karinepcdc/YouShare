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
import com.opencsv.bean.CsvToBeanFilter;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.Tool;
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
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<User> userList = new CsvToBeanBuilder( csvFile ).withType(User.class).build().parse();
		
		System.out.println("Is user list empty? " + userList.isEmpty() + "\n" );		
		HashMap<String,User> userMap = new HashMap<String,User>();
		
		for ( User user : userList ) {
			userMap.put( user.getTelegramUserName(), user );
			System.out.println("user: " + user.getFirstName() + " read\n" );
		}
		
		System.out.println("Users HashMap successfully created.");
		
		return userMap;
	}
	
	public static HashMap<String,Item> csvToItemHashMap (){
		// TODO only Tool database inplemented. Do the others
		
		// Tool database
		// check if database files exist, if not, create them
		File toolDatabaseFile = new File("src/main/csv/toolDatabase.csv");
		try {
			if( toolDatabaseFile.createNewFile() ) {
				System.out.println("Tool database file created!\n");	
			}
		} catch ( IOException e ) {
			System.out.println("An error occurred trying to create item database file.\n");
			e.printStackTrace();
		}
		
		// Prepare to read the file	
		FileReader csvFile = null; 
		try { 
			csvFile = new FileReader( toolDatabaseFile ); 
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 	
		
		// read database
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Tool> itemList = new CsvToBeanBuilder( csvFile ).withType(Tool.class).build().parse();
				
		// build hashmap
		HashMap<String,Item> itemMap = new HashMap<String,Item>();
		
		// TODO delete prints latter
		for ( Item item : itemList ) {
			itemMap.put( item.getCode(), item );
			System.out.println("item: " + item.getName() + " R$" + item.getPrice() + " read\n" );
		}
		
		return itemMap;		
	}

}
