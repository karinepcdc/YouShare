package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class Tool extends Item {

	@CsvBindByName
	private String termsOfUse; /// terms of use of the item
	
	@CsvBindByName
	private String voltage; /// eletrical voltage 220 or 110 // TODO check if it is better to use a boolean here 
	
	/* Constructor Default */
	public Tool () {
		super();
	}
	
	/* Constructor full */
	public Tool ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev, String p,
			String tOU, String v) {
		super( n, desc, cd, owner, iG, iGC, lRev, p );
		this.termsOfUse = tOU;
		this.voltage = v;
	}
	
	public String getTermsOfUse() {
		return termsOfUse;
	}

	public void setTermsOfUse(String termsOfUse) {
		this.termsOfUse = termsOfUse;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
		
}
