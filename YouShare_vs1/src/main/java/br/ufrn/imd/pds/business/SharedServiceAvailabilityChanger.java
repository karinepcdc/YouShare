package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class SharedServiceAvailabilityChanger implements ItemAvailabilityChanger {


	@Override
	public Item changeAvailability(Item item) throws BusinessException, DataException { 
		
		if( !(item instanceof SharedService) ) {
			throw new BusinessException("Trying to edit a SharedService's attribute, but item is from another type.");
		}
		
		((SharedService) item).setAvailable( !((SharedService) item).isAvailable() );
		
		return item;
	}

}