package br.ufrn.imd.pds.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.business.User;

public class BDWriter {
	
	public static void UserHashMapToCSV ( HashMap<String, User> userMap ){
		
		ArrayList<User> userList = new ArrayList<User>();
		
		for ( Map.Entry<String,User> pair : userMap.entrySet() ) {
			userList.add( pair.getValue() );
		}
		
		List<String[]> userStrings = userToStringList ( userList );
		
		try ( CSVWriter writer = new CSVWriter( new FileWriter( "src/main/csv/userDatabase.csv" ) ) ) {
            writer.writeAll( userStrings );
        }
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} catch ( IOException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 		
	}
	
	public static List<String[]> userToStringList ( List<User> userList ){
		List<String[]> strings = new ArrayList<String[]>();
		
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
	
	public static void toolListToCSV ( List<Tool> itemList ) throws IOException{
		
		List<String[]> itemStrings = toolToStringList ( itemList );
		
		try ( CSVWriter writer = new CSVWriter( new FileWriter( "src/main/csv/itemDatabase.csv" ) ) ) {
            writer.writeAll( itemStrings );
        }
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
	}
	
	public static List<String[]> toolToStringList ( List<Tool> toolList ){
		List<String[]> strings = new ArrayList<String[]>();
		
		for ( Tool tool : toolList ) { // description, code, itemGrade, lastReview, isAvailable, price, termsOfUse, voltage
			String[] s = { 	tool.getDescription(),
							String.valueOf( tool.getCode() ),
							String.valueOf( tool.getItemGrade() ),
							tool.getLastReview(),
							String.valueOf( tool.getIsAvailable() ),
							String.valueOf( tool.getPrice() ),
							tool.getTermsOfUse(),
							String.valueOf( tool.getVoltage() )
							};
			strings.add(s);
		}
		
		return strings;
	}
	
	

}
