package br.ufrn.imd.pds.business;

import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class ShareServiceNeighbor extends User {
	
	@CsvBindByName
	private String condominium;
	
	@CsvBindByName
	private List<String> reviewTags;
	
	public ShareServiceNeighbor () {
		super();
	}

}
