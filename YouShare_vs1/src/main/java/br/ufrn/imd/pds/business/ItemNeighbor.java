package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class ItemNeighbor extends User {
	
	@CsvBindByName
	private double itemGrade;
	
	@CsvBindByName
	private int itemGradeCount;
	
	@CsvBindByName
	private String lastReview;
	
	@CsvBindByName
	private String condominium;
	
	public ItemNeighbor () {		
		super();
	}
}
