package br.ufrn.imd.pds.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.pds.business.User;

public class UserDAOMemory implements UserDAO {
	ArrayList<User> users;
	String fileName;
	
	public UserDAOMemory() {
		this.fileName = "userDatabase.txt";
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
	
	public void createUser( String firstName, String lastName , String password ) {
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
	}

}
