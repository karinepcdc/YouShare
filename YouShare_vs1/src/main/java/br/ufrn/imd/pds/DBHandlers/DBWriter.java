package br.ufrn.imd.pds.DBHandlers;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.SharedService;
import br.ufrn.imd.pds.business.User;

public class DBWriter {
	
	public static void userHashMapToCSV ( HashMap<String, User> userMap ) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		
		ArrayList<User> userList = new ArrayList<User>();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			userList.add( pair.getValue() );
		}
					
		File userDatabaseFile = new File( "src/main/csv/userDatabase.csv" );
		userDatabaseFile.createNewFile(); // if file already exists will do nothing
			
		// write database
		FileWriter writer = new FileWriter( userDatabaseFile );
		StatefulBeanToCsv<User> userToCsv = new StatefulBeanToCsvBuilder<User>(writer).build();
		userToCsv.write( userList );
			
		// close writers
		writer.close();
        	
	}
	
	/// Write current item hashMap in the database.
	/*
	 *  Write itemMap, a HashMap of items, into item database files: sharedServiceDatabase.csv; 
	 */
	public static void itemHashMapToCSV ( HashMap<String, Item> itemMap ) 
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		ArrayList<SharedService> itemsList = new ArrayList<SharedService>();
		
		// put items from itemMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			if( pair.getValue() instanceof SharedService ) {
				itemsList.add( (SharedService) pair.getValue() );
			}
		}

		// write database
		FileWriter writer = new FileWriter( "src/main/csv/sharedServiceDatabase.csv" );
		StatefulBeanToCsv<SharedService> itemToCsv = new StatefulBeanToCsvBuilder<SharedService>(writer).build();
		itemToCsv.write( itemsList );
		
		writer.close();
		
		}
}
	

