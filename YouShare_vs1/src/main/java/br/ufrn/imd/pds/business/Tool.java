package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class Tool extends Item {

	@CsvBindByName
	private String termsOfUse;
	
	@CsvBindByName
	private String voltage;
	
	/* Constructor Default */
	public Tool () {
		super();
	}
	
	/* Constructor */
	public Tool ( String n, String desc, String cd, double iG, int iGC, String lRev, boolean isAv, double p,
			String tOU, String v) {
		super( n, desc, cd, iG, iGC, lRev, isAv, p );
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
