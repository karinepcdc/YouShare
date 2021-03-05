package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class Tool extends Item {

	private String termsOfUse;
	private int voltage;
	
	public Tool ( String n, String desc, int cd, float iG, ArrayList<Float> iRat, ArrayList<String> iRev, boolean isAv, double p,
			String tOU, int v) {
		super(n, desc, cd, iG, iRat, iRev, isAv, p);
		this.termsOfUse = tOU;
		this.voltage = v;
	}
	
	public String getTermsOfUse() {
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
