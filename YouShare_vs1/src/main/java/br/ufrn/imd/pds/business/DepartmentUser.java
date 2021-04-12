package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;

public class DepartmentUser extends User {
	
	@CsvBindByName
	private String department;
	
	public DepartmentUser () {
		super();
	}
}
