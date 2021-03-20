package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class ChildrenToy extends Item {

	@CsvBindByName
	private String condition;
	
	public ChildrenToy ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev, String p,
			String cond ) {
		super( n, desc, cd, owner, iG, iGC, lRev, p );
		this.condition = cond;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
