package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class OfficeItems extends Item {

	@CsvBindByName
	protected boolean isAvailable = false; /// is item available for rent?
	
	@CsvBindByName
	private String condition; /// the usage condition can be: weared, good or new  
	
	@CsvBindByName
	private String voltage; /// eletrical voltage 220 or 110  
	
	
	/* Constructor Default */
	public OfficeItems () {
		super();
	}
	
	/* Constructor full */
	public OfficeItems ( String n, String desc, String cd, String owner, double iG, int iGC, String lRev,
			 String c, String v ) {
		super( n, desc, cd, owner, iG, iGC, lRev);
		this.condition = c;
		this.voltage = v;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
		
}
