package br.ufrn.imd.pds.business;

public class User {

	private String firstName;
	private String lastName;
	private String telegramUserName;
	private float userGrade;
	private int userGradeCount;
	private String lastReview;
	

	public User ( String fName, String lName, String tUserName, float uG, int qt, String lRev ) {
		this.firstName = fName;
		this.lastName = lName;
		this.telegramUserName = tUserName;
		this.userGrade = uG;
		this.userGradeCount = qt;
		this.lastReview = lRev;		
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

	public int getUserGradeCount() {
		return userGradeCount;
	}

	public void setUserGradeCount(int userGradeCount) {
		this.userGradeCount = userGradeCount;
	}
	
	public void incrementUserGradeCount() {
		this.userGradeCount++;
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	
	
}
