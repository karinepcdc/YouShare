package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class SharedServiceValidator implements ItemValidator {

	@Override
	public List<String> itemValidator(Item item) throws BusinessException {
		
		if( !(item instanceof SharedService) ) {
			throw new BusinessException("Trying to validate an SharedService's, but item is from another type.");
		}
		
		List<String> exceptionMessages = new ArrayList<String>();

		// check if price is valid
		try {
			Double.parseDouble( ((SharedService) item).getPrice() );
			
		} catch ( NullPointerException e1 ) {
			exceptionMessages.add("Price is required.");
			
		} catch ( NumberFormatException e2 ) {
			exceptionMessages.add("Price must be a number (don't use currency symbols).");
		}
				
		return exceptionMessages;
	}

}