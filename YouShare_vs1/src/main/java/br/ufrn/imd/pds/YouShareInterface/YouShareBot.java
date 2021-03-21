package br.ufrn.imd.pds.YouShareInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class YouShareBot {
	
	private String TOKEN;
	private String USERNAME;

	public YouShareBot () {	
		
		try {
			File botConfiguration = new File( "src/main/conf/confBot" );
			Scanner myReader = new Scanner( botConfiguration );
			
			TOKEN = myReader.nextLine();
			myReader.close();
			
		} catch ( FileNotFoundException e ) {
			System.out.println( "Error reading file. Bot could not be configured." );
			e.printStackTrace();
		}
		
		USERNAME="YouShareBot";
		
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN( String tOKEN ) {
		TOKEN = tOKEN;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME( String uSERNAME ) {
		USERNAME = uSERNAME;
	}

}
