package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class ItemNeighborValidator extends UserValidator {
	
	@Override
	public List<String> userValidator( User user ) throws BusinessException {
		
		if( !( user instanceof ItemNeighbor ) ) {
			throw new BusinessException("Trying to validate an Neighbor, but user is from another type.");
		}
		
		List<String> exceptionMessages = new ArrayList<String>();

		// check if price is valid
		try {
			Double.parseDouble( ((ItemNeighbor) user).getCondominium() );
			
		} catch ( NullPointerException e1 ) {
			exceptionMessages.add("Condominium is required.");
			
		}
		
		return exceptionMessages;
	}
}
