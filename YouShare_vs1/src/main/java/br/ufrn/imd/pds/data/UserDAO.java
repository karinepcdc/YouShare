package br.ufrn.imd.pds.data;

import java.util.ArrayList;

import br.ufrn.imd.pds.business.User;

public interface UserDAO {
	
	public void createUser( String firstName, String lastName, String telegramUserName, float userGrade, 
			ArrayList<Float> userRatings, ArrayList<String> userReviews );
	
	public String readUser( User user );
	
	public void updateUser();
	
	public void deleteUser();
	
	public void reviewUser( String review, Float rating, User user );
}
