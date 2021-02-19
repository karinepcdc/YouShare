package br.ufrn.imd.pds.business;

public class PartyClothe extends item {

	private String termsOfUse;
	private String color;
	private char size;
	private String clotheStyle;
	private String partyStyle;
	
	public String getTermsOfUse() {
		return termsOfUse;
	}
	
	public void setTermsOfUse(String termsOfUse) {
		this.termsOfUse = termsOfUse;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public char getSize() {
		return size;
	}
	
	public void setSize(char size) {
		this.size = size;
	}
	
	public String getClotheStyle() {
		return clotheStyle;
	}
	
	public void setClotheStyle(String clotheStyle) {
		this.clotheStyle = clotheStyle;
	}
	
	public String getPartyStyle() {
		return partyStyle;
	}
	
	public void setPartyStyle(String partyStyle) {
		this.partyStyle = partyStyle;
	}
	
	
}
