package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class ChildrenToy extends Item {

	@CsvBindByName
	private String condition;
	
	public ChildrenToy ( String n, String desc, String cd, String iG, String iGC, String lRev, String isAv, String p,
			String cond ) {
		super( n, desc, cd, iG, iGC, lRev, isAv, p );
		this.condition = cond;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
