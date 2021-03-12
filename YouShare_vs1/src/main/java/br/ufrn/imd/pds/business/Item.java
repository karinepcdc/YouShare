package br.ufrn.imd.pds.business;

public abstract class Item {

	protected String name;
	protected String description;
	protected String code;
	protected String itemGrade;
	protected String lastReview;
	protected String isAvailable;
	protected String price;
	
	public Item ( String n, String desc, String cd, String iG, String lRev, String isAv, String p ) {
		this.name = n;
		this.description = desc;
		this.code = cd;
		this.itemGrade = iG;
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
