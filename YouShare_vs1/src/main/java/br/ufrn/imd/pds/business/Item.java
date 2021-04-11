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
	protected String owner; /// user that owns item
	
	@CsvBindByName
	protected double itemGrade; /// average of grades received evaluating item performance
	
	@CsvBindByName
	protected int itemGradeCount; /// total number of grades 
	
	@CsvBindByName
	protected String lastReview; /// last review received when he returned an item
	
	/* Constructor Default */
	public Item () {}
	
	/* Constructor full */
	public Item ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev ) {
		this.name = n;
		this.description = desc;
		this.code = cd;
		this.owner = owner;
		this.itemGrade = iG;
		this.itemGradeCount = iGC;
		this.lastReview = lRev;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	
}
