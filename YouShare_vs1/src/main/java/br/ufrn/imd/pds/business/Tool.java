package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class Tool extends Item {

	@CsvBindByName
	private String termsOfUse;
	
	@CsvBindByName
	private String voltage;
	
	public Tool ( String n, String desc, String cd, String iG, String iGC, String lRev, String isAv, String p,
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
