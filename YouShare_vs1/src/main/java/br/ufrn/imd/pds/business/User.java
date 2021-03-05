package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class User {

	private String firstName;
	private String lastName;
	private String telegramUserName;
	private String password;
	private float userGrade;
	private ArrayList<Float> ratings;
	private ArrayList<String> userReviews;
	
	public User ( String fName, String lName, String tUserName, String passwd ) {
		this.firstName = fName;
		this.lastName = lName;
		this.telegramUserName = tUserName;
		this.password = passwd;
		this.userGrade = 0;
		this.ratings = new ArrayList<Float>();
		this.userReviews = new ArrayList<String>();		
	}
			
	public ArrayList<Float> getRatings() {
		return ratings;
	}

	public void setRatings( ArrayList<Float> ratings ) {
		this.ratings = ratings;
	}

	public float getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(float userGrade) {
		this.userGrade = userGrade;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getTelegramUserName() {
		return telegramUserName;
	}
	
	public void setTelegramUserName( String telegramUserName ) {
		this.telegramUserName = telegramUserName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getUserReviews() {
		return userReviews;
	}


	public void setUserReviews(ArrayList<String> userReviews) {
		this.userReviews = userReviews;
	}
	
	
}
