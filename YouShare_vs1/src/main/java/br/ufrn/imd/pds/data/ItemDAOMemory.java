package br.ufrn.imd.pds.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.pds.business.Item;

public class ItemDAOMemory implements ItemDAO {
	
	ArrayList<Item> items;
	String fileName;
	
	public ItemDAOMemory() {
		this.fileName = "itemDatabase.txt";
		items = new ArrayList<Item>();
		startDatabase();
	}
	
	public void startDatabase (){
		
		boolean fileAlreadyExists = false;
		
	    try {
	      File databaseFile = new File( fileName );
	      
	      if ( databaseFile.createNewFile() ) {
	    	  System.out.println( "File created: " + databaseFile.getName() );
	      
	      } else {
	    	  System.out.println( "File already exists." );
	    	  fileAlreadyExists = true;
	      }
	    
	    } catch ( IOException e ) {
	    	System.out.println( "An error occurred while trying to create the file." );
	    	e.printStackTrace();
	    }
	    
	    if ( fileAlreadyExists ) {
	    	fillListFromFile();
	    }	    
	}
	
	public void fillListFromFile () {
		try {
            FileReader reader = new FileReader( fileName );
            BufferedReader bufferedReader = new BufferedReader( reader );
            
            String line;
            int lineCounter = 1;
            
            String firstNameTemp = new String("void");
            String lastNameTemp = new String("void");
            String userNameTemp = new String("void");
            
            while ( ( line = bufferedReader.readLine()) != "---" ) {
                if ( lineCounter == 1 ) {
                	firstNameTemp = line;
                } else if ( lineCounter == 2 ) {
                	lastNameTemp = line;
                } else if ( lineCounter == 3 ) {
                	userNameTemp = line;
                }
                // TODO: throw exceptions when Strings are "void"
                User user = new User( firstNameTemp, lastNameTemp, userNameTemp );
                users.add( user );
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void createItem( String name, String description, int code, float itemGrade, ArrayList<Float> itemRatings, 
			ArrayList<String> itemReviews, boolean isAvailable, double price ) {
		
	}
	
	public String readItem( Item item ) {
		
	}
	
	public void updateItem() {
		
	}
	
	public void deleteItem() {
		
	}
	
	public void reviewItem( String review, Float rating, Item item ) {
		
	}

}
