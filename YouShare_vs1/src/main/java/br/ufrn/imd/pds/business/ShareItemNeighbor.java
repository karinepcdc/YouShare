package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class ShareItemNeighbor extends User {
	
	@CsvBindByName
	private double userGrade;

	@CsvBindByName
	private int userGradeCount;
	
	@CsvBindByName
	private String lastReview;
	
	@CsvBindByName
	private String condominium;
	
	
	public ShareItemNeighbor () {		
		super();
	}
	
	public double getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(double itemGrade) {
		this.userGrade = itemGrade;
	}

	public int getUserGradeCount() {
		return userGradeCount;
	}

	public void setUserGradeCount(int itemGradeCount) {
		this.userGradeCount = itemGradeCount;
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	public String getCondominium() {
		return condominium;
	}

	public void setCondominium(String condominium) {
		this.condominium = condominium;
	}
	
	public void incrementUserGradeCount() {
		userGradeCount++;
	}
}
