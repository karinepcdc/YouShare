package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class ShareItemNeighbor extends User {
	
	@CsvBindByName
	private double itemGrade;

	@CsvBindByName
	private int itemGradeCount;
	
	@CsvBindByName
	private String lastReview;
	
	@CsvBindByName
	private String condominium;
	
	
	public ShareItemNeighbor () {		
		super();
	}
	
	public double getItemGrade() {
		return itemGrade;
	}

	public void setItemGrade(double itemGrade) {
		this.itemGrade = itemGrade;
	}

	public int getItemGradeCount() {
		return itemGradeCount;
	}

	public void setItemGradeCount(int itemGradeCount) {
		this.itemGradeCount = itemGradeCount;
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
}
