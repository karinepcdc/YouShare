package br.ufrn.imd.pds.business;

public class User {

	private String firstName;
	private String lastName;
	private String telegramUserName;
	private String userGrade;
	private String userGradeCount;
	private String lastReview;
	
	/* Default constructor */
	public User () {
		
	}
	
	public User ( String fName, String lName, String tUserName, String uG, String qt, String lRev ) {
		this.firstName = fName;
		this.lastName = lName;
		this.telegramUserName = tUserName;
		this.userGrade = uG;
		this.userGradeCount = qt;
		this.lastReview = lRev;		
	}		
	
	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
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

	public String getUserGradeCount() {
		return userGradeCount;
	}

	public void setUserGradeCount(String userGradeCount) {
		this.userGradeCount = userGradeCount;
	}
	
	public void incrementUserGradeCount() {
		int aux = Integer.parseInt( userGradeCount );
		userGradeCount = String.valueOf( aux++ );
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}	
	
}
