package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class Appliance extends Item {

	@CsvBindByName
	private String termsOfUse;
	
	@CsvBindByName
	private String voltage;
	
	public Appliance ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev, String p,
			String tOU, String v) {
		super( n, desc, cd, owner, iG, iGC, lRev, p );
		this.termsOfUse = tOU;
		this.voltage = v;
	}
	
	public String getTermsOfUSe() {
		return termsOfUse;
	}
	
	public void setTermsOfUSe(String termsOfUSe) {
		this.termsOfUse = termsOfUSe;
	}
	
	public String getVoltage() {
		return voltage;
	}
	
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	
}
