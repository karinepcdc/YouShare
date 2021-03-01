package br.ufrn.imd.pds.data;

import java.io.FileWriter;
import java.io.File; 
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;

import br.ufrn.imd.pds.business.User;

public class UserDAO {
	
	ArrayList<User> users;
	String fileName;
	
	public UserDAO() {
		this.fileName = "database.txt";
		users = new ArrayList<User>();
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
	    	System.out.println( "An error occurred." );
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
 
            while ( ( line = bufferedReader.readLine()) != "---" ) {
                if ( lineCounter == 1 ) {
                	//User user = new User();
                	//user.setFirstName( line );
                } else if ( lineCounter == 2 ) {
                	
                }
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*public void createUser( String firstName, String lastName , String password ) {
		User user = new User( firstName, lastName, password );
		users.add( user );
	}
	
	public String readUser( User user  ) {
		String userStats = "";
		
		userStats = "All" + user.getFirstName() + " " + user.getLastName() + " stats:"
    			+ "\n"
    			+ "\n"
    			+ "";
		
		return userStats;
	}
	
	public void updateUser() {
		
	}
	
	public void deleteUser() {
		
	}
	
	public void reviewUser( String review, Float rating, User user ) {
		user.getUserReviews().add( review );
		user.getRatings().add( rating );
	}
	
	public void calculateUserGrade ( User user ) {
		float average = 0;
		float counter = 0;
		
		for( Float rating : user.getRatings() ) {
			average += rating;
			counter++;
		}
		
		average = average/counter;
		
		user.setUserGrade( average );
	}*/

}
