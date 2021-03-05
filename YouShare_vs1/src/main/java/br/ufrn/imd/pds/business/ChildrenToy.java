package br.ufrn.imd.pds.business;

public class ChildrenToy extends Item {

	private String condition;
	
	public ChildrenToy ( String n, String desc, int cd, float iG, String lRev, boolean isAv, double p,
			String cond ) {
		super( n, desc, cd, iG, lRev, isAv, p );
		this.condition = cond;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
