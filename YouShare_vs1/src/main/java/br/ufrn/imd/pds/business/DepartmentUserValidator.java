package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public class DepartmentUserValidator extends UserValidator {
	
	public DepartmentUserValidator ( User user ){
		super( );
	}

	@Override
	public List<String> userValidator(User user) throws BusinessException {
		if( !( user instanceof DepartmentUser ) ) {
			throw new BusinessException("Trying to validate a DepartmentUser, but user is from another type.");
		}
		
		List<String> exceptionMessages = new ArrayList<String>();
		
		if( ( (DepartmentUser) user).getDepartment() == null || ( (DepartmentUser) user).getDepartment().isBlank() ) {
			exceptionMessages.add("Department is required.");
		}
		
		return exceptionMessages;
	}
}