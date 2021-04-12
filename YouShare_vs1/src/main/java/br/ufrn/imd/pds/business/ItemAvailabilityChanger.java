package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public interface ItemAvailabilityChanger {

	/// Change item availability for diferent subclasses of items.
	public Item changeAvailability( Item item ) throws BusinessException, DataException;
	
}
