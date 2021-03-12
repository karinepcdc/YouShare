package br.ufrn.imd.pds.business;

public class Tool extends Item {

	private String termsOfUse;
	private String voltage;
	
	public Tool ( String n, String desc, String cd, String iG, String lRev, String isAv, String p,
			String tOU, String v) {
		super( n, desc, cd, iG, lRev, isAv, p );
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
