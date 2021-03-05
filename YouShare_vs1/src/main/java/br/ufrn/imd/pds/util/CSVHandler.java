package br.ufrn.imd.pds.util;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

public class CSVHandler {
	
	public static List<User> csvToUser (){
		System.out.println("Convertendo userDatabase.csv para instancias da classe User...");

		CSVReader csvReader = null; 
		try { 
			csvReader = new CSVReader( new FileReader( "././csv/userDatabase.csv" ) ); 
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<User> userList = 
			new CsvToBeanBuilder( csvReader ).withType(User.class).build().parse();
		
		return userList;
	}
	
	public static List<Item> csvToItem (){
		System.out.println("Convertendo itemDatabase.csv para instancias da classe Item...");

		CSVReader csvReader = null; 
		try { 
			csvReader = new CSVReader( new FileReader( "././csv/itemDatabase.csv" ) ); 
		} 
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Item> itemList = 
			new CsvToBeanBuilder( csvReader ).withType(Item.class).build().parse();
		
		return itemList;
	}

}
