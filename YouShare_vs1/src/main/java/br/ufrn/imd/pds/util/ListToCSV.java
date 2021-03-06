package br.ufrn.imd.pds.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.business.User;

public class ListToCSV {
	
	public static void userListToCSV ( List<User> userList ) throws IOException{
		
		List<String[]> userStrings = userToStringList ( userList );
		
		try ( CSVWriter writer = new CSVWriter( new FileWriter( "src/main/csv/userDatabase.csv" ) ) ) {
            writer.writeAll( userStrings );
        }
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} 		
	}
	
	public static List<String[]> userToStringList ( List<User> userList ){
		List<String[]> strings = new ArrayList<String[]>();
		
		for ( User user : userList ) {
			String[] s = { 	user.getFirstName(), 
							user.getLastName(), 
							user.getTelegramUserName(),
							String.valueOf( user.getUserGrade() ),
							user.getLastReview()
							};
			strings.add(s);
		}
		
		return strings;
	}
	
	public static void toolListToCSV ( List<Tool> itemList ) throws IOException{
		
		List<String[]> itemStrings = toolToStringList ( itemList );
		
		try ( CSVWriter writer = new CSVWriter( new FileWriter( "src/main/csv/userDatabase.csv" ) ) ) {
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
