package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class ServiceNeighbor extends User {
	
	@CsvBindByName
	private String condominium;
	
	@CsvBindByName
	private List<String> reviewTags;
	
	public ServiceNeighbor () {
		super();
	}

}
