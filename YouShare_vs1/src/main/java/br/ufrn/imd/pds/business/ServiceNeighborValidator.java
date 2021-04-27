package br.ufrn.imd.pds.business;

import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class ServiceNeighborValidator extends UserValidator {
	
	public ServiceNeighborValidator ( User user ){
		super( );
	}

	@Override
	public List<String> userValidator(User user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}