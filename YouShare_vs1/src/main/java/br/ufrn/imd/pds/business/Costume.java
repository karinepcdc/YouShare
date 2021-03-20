package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class Costume extends Item {

	@CsvBindByName
	private String termsOfUse;
	
	@CsvBindByName
	private String color;
	
	@CsvBindByName
	private String size;
	
	@CsvBindByName
	private String clotheStyle;
	
	@CsvBindByName
	private String partyStyle;
	
	public Costume ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev, String p,
			String tOU, String c, String s, String cStyle, String pStyle ) {
		super( n, desc, cd, owner, iG, iGC, lRev, p );
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
