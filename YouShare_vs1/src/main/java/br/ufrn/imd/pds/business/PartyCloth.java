package br.ufrn.imd.pds.business;

public class PartyCloth extends Item {

	private String termsOfUse;
	private String color;
	private char size;
	private String clotheStyle;
	private String partyStyle;
	
	public PartyCloth ( String n, String desc, int cd, float iG, String lRev, boolean isAv, double p,
			String tOU, String c, char s, String cStyle, String pStyle ) {
		super( n, desc, cd, iG, lRev, isAv, p );
		this.termsOfUse = tOU;
		this.color = c;
		this.size = s;
		this.clotheStyle = cStyle;
		this.partyStyle = pStyle;
	}
	
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
