package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class User {

	private String firstName;
	private String lastName;
	private String telegramUserName;
	private float userGrade;
	private ArrayList<Float> userRatings;
	private ArrayList<String> userReviews;
	
	public User ( String fName, String lName, String tUserName, float uG, ArrayList<Float> uRtgs, ArrayList<String> uR ) {
		this.firstName = fName;
		this.lastName = lName;
		this.telegramUserName = tUserName;
		this.userGrade = uG;
		this.userRatings = uRtgs;
		this.userReviews = uR;		
	}
			
	public ArrayList<Float> getUserRatings() {
		return userRatings;
	}

	public void setRatings( ArrayList<Float> ratings ) {
		this.userRatings = ratings;
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


	public ArrayList<String> getUserReviews() {
		return userReviews;
	}


	public void setUserReviews(ArrayList<String> userReviews) {
		this.userReviews = userReviews;
	}
	
	
}
