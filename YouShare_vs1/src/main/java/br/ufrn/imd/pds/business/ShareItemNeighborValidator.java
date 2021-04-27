package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class ShareItemNeighborValidator extends UserValidator {
	
	@Override
	public List<String> userValidator( User user ) throws BusinessException {
		
		if( !( user instanceof ShareItemNeighbor ) ) {
			throw new BusinessException("Trying to validate a Neighbor, but user is from another type.");
		}
		
		List<String> exceptionMessages = new ArrayList<String>();

		if( ( (ShareItemNeighbor) user).getUserGrade() == 0.0 ) {
			exceptionMessages.add("UserGrade is invalid.");
		}
		
		if( ( (ShareItemNeighbor) user).getLastReview() == null || ( (ShareItemNeighbor) user).getLastReview().isBlank() ) {
			exceptionMessages.add("LastReview is empty.");
		}
		
		if( ( (ShareItemNeighbor) user).getCondominium() == null || ( (ShareItemNeighbor) user).getCondominium().isBlank() ) {
			exceptionMessages.add("Condominium is required.");
		}		
		
		return exceptionMessages;
	}
}
