package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class OfficeItemsAvailabilityChanger implements ItemAvailabilityChanger {


	@Override
	public Item changeAvailability(Item item) throws BusinessException, DataException { 
		
		if( !(item instanceof OfficeItems) ) {
			throw new BusinessException("Trying to edit a OfficeItems's attribute, but item is from another type.");
		}
		
		((OfficeItems) item).setAvailable( !((OfficeItems) item).isAvailable() );
		
		return item;
	}

}
