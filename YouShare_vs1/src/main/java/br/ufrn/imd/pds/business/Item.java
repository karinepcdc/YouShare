package br.ufrn.imd.pds.business;

import java.util.List;

public abstract class Item {

	protected String name;
	protected String description; // mudar para json???
	protected int code;
	protected List<Integer> itemGrade;
	protected List<String> itemReview;  // Mudar apra Json??
	protected boolean isAvailable;
	protected double price;
	
	
	
	
}
