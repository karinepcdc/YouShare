package br.ufrn.imd.pds.business;

import java.util.ArrayList;

public class ChildrenToy extends Item {

	private String condition;
	
	public ChildrenToy ( String n, String desc, int cd, float iG, ArrayList<Float> iRat, ArrayList<String> iRev, boolean isAv, double p,
			String cond ) {
		super(n, desc, cd, iG, iRat, iRev, isAv, p);
		this.condition = cond;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
