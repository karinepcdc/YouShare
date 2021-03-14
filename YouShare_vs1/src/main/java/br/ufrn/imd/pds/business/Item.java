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
	protected String itemGrade; /// average of grades received evaluating item performance
	
	@CsvBindByName
	private String itemGradeCount; /// total number of grades 
	
	@CsvBindByName
	protected String lastReview; /// last review received when he returned an item
	
	@CsvBindByName
	protected String isAvailable; /// is item available for rent?
	
	@CsvBindByName
	protected String price; /// rent price, set zero is it is borroed
	
	public Item ( String n, String desc, String cd, String iG, String iGC, String lRev, String isAv, String p ) {
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

	public String getItemGrade() {
		return itemGrade;
	}

	public void setItemGrade(String itemGrade) {
		this.itemGrade = itemGrade;
	}

	public String getItemGradeCount() {
		return itemGradeCount;
	}

	public void setItemGradeCount(String itemGradeCount) {
		this.itemGradeCount = itemGradeCount;
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}	
	
}
