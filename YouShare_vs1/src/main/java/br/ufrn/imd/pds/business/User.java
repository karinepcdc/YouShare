package br.ufrn.imd.pds.business;

import java.util.List;

public class User {

	private String name;
	private String telegramUserName;
	private List<Integer> UserGrade;
	private List<String> userReviews; // Mudar apra Json??
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTelegramUserName() {
		return telegramUserName;
	}
	
	public void setTelegramUserName(String telegramUserName) {
		this.telegramUserName = telegramUserName;
	}
	
	public List<Integer> getUserGrade() {
		return UserGrade;
	}
	
	public void setUserGrade(List<Integer> userGrade) {
		UserGrade = userGrade;
	}
	
	public List<String> getUserReviews() {
		return userReviews;
	}
	
	public void setUserReviews(List<String> userReviews) {
		this.userReviews = userReviews;
	}
	
	
	
}
