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

		if( ( (ShareItemNeighbor) user).getUserGrade() == null || ( (ShareItemNeighbor) user).getUserGrade().isBlank() ) {
			exceptionMessages.add("UserGrade is required.");
		}
		
		return exceptionMessages;
	}
}
