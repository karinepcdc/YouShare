package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public abstract class Item {

	@CsvBindByName
	protected String name; /// item name
	
	@CsvBindByName
	protected String description; /// item description
	
	@CsvBindByName
	protected String code; /// used as id in the database
	
	@CsvBindByName
	protected double itemGrade; /// average of grades received evaluating item performance
	
	@CsvBindByName
	protected int itemGradeCount; /// total number of grades 
	
	@CsvBindByName
	protected String lastReview; /// last review received when he returned an item
	
	@CsvBindByName
	protected boolean isAvailable; /// is item available for rent?
	
	@CsvBindByName
	protected double price; /// rent price, set zero is it is borroed
	
	/* Constructor Default */
	public Item () {}
	
	public Item ( String n, String desc, String cd, double iG, int iGC, String lRev, boolean isAv, double p ) {
		this.name = n;
		this.description = desc;
		this.code = cd;
		this.itemGrade = iG;
		this.itemGradeCount = iGC;
		this.lastReview = lRev;
		this.isAvailable = isAv;
		this.price = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
	
}
