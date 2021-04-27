package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class SharedService extends Item {

	@CsvBindByName
	protected boolean isAvailable = false; /// is item available for rent?
	
	@CsvBindByName
	protected String price; /// rent price, set zero is it is borroed
	
	/* Constructor Default */
	public SharedService () {
		super();
	}
	
	/* Constructor full */
	public SharedService ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev, String p ) {
		super( n, desc, cd, owner, iG, iGC, lRev);
		this.price = p;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

		
}