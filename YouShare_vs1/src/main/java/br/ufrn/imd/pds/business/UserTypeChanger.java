package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public interface UserTypeChanger {
	
	/// Change user type for different subclasses of users.
	public User changeUserType( User user ) throws BusinessException, DataException;
}
