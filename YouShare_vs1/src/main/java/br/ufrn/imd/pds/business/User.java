package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class User {

	@CsvBindByName
	private String firstName;
	
	@CsvBindByName
	private String lastName;
	
	@CsvBindByName
	private String telegramUserName; /// used as id in the system

	
	/* Default constructor */
	public User () {
		
	}
	
	public User ( String fName, String lName, String tUserName ) {
		this.firstName = fName;
		this.lastName = lName;
		this.telegramUserName = tUserName;	
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
	
}
