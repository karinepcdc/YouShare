package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class OfficeItemsValidator implements ItemValidator {

	@Override
	public List<String> itemValidator(Item item) throws BusinessException {
		
		if( !(item instanceof OfficeItems) ) {
			throw new BusinessException("Trying to validate an OfficeItems's, but item is from another type.");
		}
		
		List<String> exceptionMessages = new ArrayList<String>();

		// check if price is valid
		try {
			Double.parseDouble( ((OfficeItems) item).getPrice() );
			
		} catch ( NullPointerException e1 ) {
			exceptionMessages.add("Price is required.");
			
		} catch ( NumberFormatException e2 ) {
			exceptionMessages.add("Price must be a number (don't use currency symbols).");
		}
			
		// validate TermsOfUse
		if( ( (OfficeItems) item).getTermsOfUse() == null || ( (OfficeItems) item).getTermsOfUse().isBlank() ) {
			exceptionMessages.add("Terms of Use are required.");
		}
			
		// validate condition
		if( ( (OfficeItems) item).getCondition() == null || ( (OfficeItems) item).getCondition().isBlank() ) {
			exceptionMessages.add("Condition is required (weared, good or new values are accepted).");
		}
			
		// valid conditions: worn, good or new
		String condition = ( (OfficeItems) item).getCondition();
		if( !condition.equals("weared") && !condition.equals("good") && !condition.equals("new") ) {
			exceptionMessages.add("Condition is invalid (weared, good or new values are accepted).");				
		}
			
		// validate voltage
		if( ( (OfficeItems) item).getVoltage() == null || ( (OfficeItems) item).getVoltage().isBlank() ) {
			exceptionMessages.add("Voltage is required (110, 220 or none values are accepted).");
		}
			
		// valid voltages: 110, 220 or none
		String voltage = ( (OfficeItems) item).getVoltage();
		if( !voltage.equals("110") && !voltage.equals("220")  && !voltage.equals("none") ) {
			exceptionMessages.add("Voltage is invalid (110, 220 or none values are accepted).");				
		}
		
		return exceptionMessages;
	}

}
