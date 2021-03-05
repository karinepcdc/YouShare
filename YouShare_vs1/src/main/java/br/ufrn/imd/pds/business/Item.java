package br.ufrn.imd.pds.business;

public abstract class Item {

	protected String name;
	protected String description;
	protected int code;
	private float itemGrade;
	protected String lastReview;
	protected boolean isAvailable;
	protected double price;
	
	public Item ( String n, String desc, int cd, float iG, String lRev, boolean isAv, double p ) {
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public float getItemGrade() {
		return itemGrade;
	}

	public void setItemGrade(float itemGrade) {
		this.itemGrade = itemGrade;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
