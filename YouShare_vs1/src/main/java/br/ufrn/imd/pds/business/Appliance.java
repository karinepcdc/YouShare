package br.ufrn.imd.pds.business;

public class Appliance extends Item {

	private String termsOfUse;
	private int voltage;
	
	public Appliance ( String n, String desc, int cd, float iG, String lRev, boolean isAv, double p,
			String tOU, int v) {
		super( n, desc, cd, iG, lRev, isAv, p );
		this.termsOfUse = tOU;
		this.voltage = v;
	}
	
	public String getTermsOfUSe() {
		return termsOfUse;
	}
	
	public void setTermsOfUSe(String termsOfUSe) {
		this.termsOfUse = termsOfUSe;
	}
	
	public int getVoltage() {
		return voltage;
	}
	
	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}
	
}
