package br.ufrn.imd.pds.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.business.User;

public class BDWriter {
	
	public static void userHashMapToCSV ( HashMap<String, User> userMap ){
		
		ArrayList<User> userList = new ArrayList<User>();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			userList.add( pair.getValue() );
		}
		
		// transform user objects in a list of strings
		List<String[]> userStrings = userToStringList ( userList );
		try {
			// write header
			FileWriter writer = new FileWriter( "src/main/csv/userDatabase.csv");
			writer.write( "firstName,lastName,telegramUserName,userGrade,userGradeCount,lastReview\n");
			
			// write database
			CSVWriter csvWriter = new CSVWriter( writer ); 
			csvWriter.writeAll( userStrings );
			
			// close writers
			csvWriter.close();
			writer.close();
        } catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} catch ( IOException e1 ) {
			e1.printStackTrace();
		} 		
	}
	
	public static void itemHashMapToCSV ( HashMap<String, Item> itemMap ) {
		ArrayList<Tool> toolList = new ArrayList<Tool>();
		
		// put tools from toolMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			if( pair.getValue() instanceof Tool ) {
				toolList.add( (Tool) pair.getValue() );
			}
		}

		try {						
			// write database
			FileWriter writer = new FileWriter( "src/main/csv/toolDatabase.csv" );
			StatefulBeanToCsv<Tool> toolToCsv = new StatefulBeanToCsvBuilder<Tool>(writer).build();
			toolToCsv.write( toolList );
			
            writer.close();
        } catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} catch ( IOException e1 ) {
			e1.printStackTrace();
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e2) {
			e2.printStackTrace();
		}
		
	}
	
	/// function that tells how to write a User into a CSV file
	public static List<String[]> userToStringList ( List<User> userList ){
		List<String[]> strings = new ArrayList<String[]>();
		
		// define what are the User's attributes to be read
		for ( User user : userList ) {
			String[] s = { 	user.getFirstName(), 
							user.getLastName(), 
							user.getTelegramUserName(),
							user.getUserGrade(),
							user.getUserGradeCount(),
							user.getLastReview()
							};
			strings.add(s);
		}
		
		return strings;
	}
		
}
