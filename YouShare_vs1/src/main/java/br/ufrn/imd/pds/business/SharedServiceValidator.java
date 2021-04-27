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
			
		// validate TermsOfUse
		if( ( (SharedService) item).getTermsOfUse() == null || ( (SharedService) item).getTermsOfUse().isBlank() ) {
			exceptionMessages.add("Terms of Use are required.");
		}
			
		// validate condition
		if( ( (SharedService) item).getCondition() == null || ( (SharedService) item).getCondition().isBlank() ) {
			exceptionMessages.add("Condition is required (weared, good or new values are accepted).");
		}
			
		// valid conditions: worn, good or new
		String condition = ( (SharedService) item).getCondition();
		if( !condition.equals("weared") && !condition.equals("good") && !condition.equals("new") ) {
			exceptionMessages.add("Condition is invalid (weared, good or new values are accepted).");				
		}
			
		// validate voltage
		if( ( (SharedService) item).getVoltage() == null || ( (SharedService) item).getVoltage().isBlank() ) {
			exceptionMessages.add("Voltage is required (110, 220 or none values are accepted).");
		}
			
		// valid voltages: 110, 220 or none
		String voltage = ( (SharedService) item).getVoltage();
		if( !voltage.equals("110") && !voltage.equals("220")  && !voltage.equals("none") ) {
			exceptionMessages.add("Voltage is invalid (110, 220 or none values are accepted).");				
		}
		
		return exceptionMessages;
	}

}