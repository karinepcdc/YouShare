package br.ufrn.imd.pds.business;

import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public abstract class UserValidator {
	
	/// Perform specific user validation for different subclasses of items.
	public abstract List<String> userValidator( User user ) throws BusinessException;
	
}
