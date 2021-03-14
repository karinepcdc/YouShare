package br.ufrn.imd.pds.business;

public class Costume extends Item {

	private String termsOfUse;
	private String color;
	private String size;
	private String clotheStyle;
	private String partyStyle;
	
	public Costume ( String n, String desc, String cd, String iG, String iGC, String lRev, String isAv, String p,
			String tOU, String c, String s, String cStyle, String pStyle ) {
		super( n, desc, cd, iG, iGC, lRev, isAv, p );
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
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
