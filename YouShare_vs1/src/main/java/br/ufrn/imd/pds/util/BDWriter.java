package br.ufrn.imd.pds.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public static void toolHashMapToCSV ( HashMap<String, Tool> toolMap ) {
		
		ArrayList<Tool> toolList = new ArrayList<Tool>();
		
		// put tools from toolMap in a list
		for ( Map.Entry<String,Tool> pair : toolMap.entrySet() ) {
			toolList.add( pair.getValue() );
		}
		
		// transform Tools objects in a list of strings
		List<String[]> toolStrings = toolToStringList ( toolList );
		
		try {
			// write header
			FileWriter writer = new FileWriter( "src/main/csv/itemDatabase.csv");
			writer.write( "name,description,code,itemGrade,itemGradeCount,lastReview,isAvailable,price,termsOfUse,voltage\n" );
						
			// write database
			CSVWriter csvWriter = new CSVWriter( writer );
            csvWriter.writeAll( toolStrings );
            
            // close writers
            csvWriter.close();
            writer.close();
        } catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} catch ( IOException e1 ) {
			e1.printStackTrace();
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
	
	/// function that tells how to write a Tool into a CSV file
	public static List<String[]> toolToStringList ( List<Tool> toolList ){
		List<String[]> strings = new ArrayList<String[]>();
		
		// define what are the Tool's attributes to be read
		for ( Tool tool : toolList ) { // name, description, code, itemGrade, itemGradeCount, lastReview, isAvailable, price, termsOfUse, voltage
			String[] s = { 	tool.getName(),
							tool.getDescription(),
							tool.getCode(),
							tool.getItemGrade(),
							tool.getItemGradeCount(),
							tool.getLastReview(),
							tool.getIsAvailable(),
							tool.getPrice(),
							tool.getTermsOfUse(),
							tool.getVoltage()
							};
			strings.add(s);
		}
		
		return strings;
	}
	
}
