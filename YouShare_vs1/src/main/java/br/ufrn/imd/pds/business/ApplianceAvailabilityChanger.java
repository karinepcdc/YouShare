package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class ApplianceAvailabilityChanger implements ItemAvailabilityChanger {


	@Override
	public Item changeAvailability(Item item) throws BusinessException, DataException { 
		
		if( !(item instanceof Appliance) ) {
			throw new BusinessException("Trying to edit a Appliance's attribute, but item is from another type.");
		}
		
		((Appliance) item).setAvailable( !((Appliance) item).isAvailable() );
		
		return item;
	}

}
