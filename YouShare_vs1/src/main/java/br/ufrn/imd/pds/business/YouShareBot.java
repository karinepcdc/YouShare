package br.ufrn.imd.pds.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/// Class that create the YoushareBot object with token and username.
public class YouShareBot {
	
	private String TOKEN;
	private String USERNAME;

	
	/* Default constructor */	
	public YouShareBot () {		
		// set bot token
		try {
			File botConfiguration = new File("src/main/conf/confBot");
			Scanner myReader = new Scanner(botConfiguration);
			
			TOKEN= myReader.nextLine();
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file. Bot could not be configured.");
			e.printStackTrace();
		}
		
		// set bot user name
		USERNAME="YouShareBot";
		
	}


	public String getTOKEN() {
		return TOKEN;
	}


	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}


	public String getUSERNAME() {
		return USERNAME;
	}


	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	
	
	

}
