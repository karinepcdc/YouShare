package br.ufrn.imd.pds.DBHandlers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.SharedService;
import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.exceptions.DataException;


public class DBReader {
	
	public static HashMap<String,User> csvToUserHashMap () throws DataException {
		
		System.out.println( "Convertendo userDatabase.csv para instancias da classe User..." );
		
		File userDatabaseFile = new File( "src/main/csv/userDatabase.csv" );
		
		try {
			// check if database file exist, if not, create it
			if( userDatabaseFile.createNewFile() ) {				
				// write header
				try {
					FileWriter writer = new FileWriter( userDatabaseFile );
		            writer.write( "firstName,lastName,telegramUserName,userGrade,userGradeCount,lastReview\n");
		            writer.close();
		            
		            // writing header was successful
					System.out.println( "User database file created!\n" );
					
		        } catch ( IOException e1 ) {
		        	e1.printStackTrace();
					throw new DataException( "An error has occurred while trying to write the user database header. \n" );				
				} 		
			}
			
		} catch ( IOException e ) {
			e.printStackTrace();
			throw new DataException( "An error occurred while trying to create user database file. \n" );
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
		
		System.out.println( "Is user list empty? " + userList.isEmpty() + "\n" );		
		HashMap<String,User> userMap = new HashMap<String,User>();
		
		for ( User user : userList ) {
			userMap.put( user.getTelegramUserName(), user );
			System.out.println("user: " + user.getFirstName() + " read\n" );
		}
		
		System.out.println( "Users HashMap successfully created." );
		
		return userMap;
	}
	
	public static HashMap<String,Item> csvToItemHashMap () throws IOException {		
		// Items database
		// If items database files does not exist, create them
		File itemsDatabaseFile = new File("src/main/csv/sharedServiceDatabase.csv");
		if( itemsDatabaseFile.createNewFile() ) {
			System.out.println("Items database file created!\n");	
		}
		
		
		// Prepare to read the file	
		FileReader csvFile = null; 
		csvFile = new FileReader( itemsDatabaseFile );  	
		
		// read database
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<SharedService> itemList = new CsvToBeanBuilder( csvFile ).withType(SharedService.class).build().parse();
				
		// build hashmap
		HashMap<String,Item> itemMap = new HashMap<String,Item>();
		
		for ( Item item : itemList ) {
			itemMap.put( item.getCode(), item );
			//System.out.println("item: " + item.getName() + " (id: " + item.getCode() + ") read\n" );
		}
		
		return itemMap;		
	}

}
