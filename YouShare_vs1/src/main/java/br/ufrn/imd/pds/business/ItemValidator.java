package br.ufrn.imd.pds.business;

import java.util.List;

import br.ufrn.imd.pds.exceptions.BusinessException;

public interface ItemValidator {

	/// Perform specific item validation for different subclasses of items.
	public List<String> itemValidator(Item item) throws BusinessException;
	
}
