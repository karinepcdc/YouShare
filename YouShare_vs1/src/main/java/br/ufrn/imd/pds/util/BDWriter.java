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
	
	public static void userHashMapToCSV ( HashMap<String, User> userMap ){
		
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
			e1.printStackTrace();
		} 		
	}
	
	public static void toolHashMapToCSV ( HashMap<String, Tool> toolMap ) {
		
		ArrayList<Tool> toolList = new ArrayList<Tool>();
		
		for ( Map.Entry<String,Tool> pair : toolMap.entrySet() ) {
			toolList.add( pair.getValue() );
		}
		
		List<String[]> toolStrings = toolToStringList ( toolList );
		
		try ( CSVWriter writer = new CSVWriter( new FileWriter( "src/main/csv/itemDatabase.csv" ) ) ) {
            writer.writeAll( toolStrings );
        }
		catch ( FileNotFoundException e ) { 
			e.printStackTrace(); 
		} catch ( IOException e1 ) {
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
	
	public static List<String[]> toolToStringList ( List<Tool> toolList ){
		List<String[]> strings = new ArrayList<String[]>();
		
		for ( Tool tool : toolList ) { // description, code, itemGrade, lastReview, isAvailable, price, termsOfUse, voltage
			String[] s = { 	tool.getDescription(),
							tool.getCode(),
							tool.getItemGrade(),
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
