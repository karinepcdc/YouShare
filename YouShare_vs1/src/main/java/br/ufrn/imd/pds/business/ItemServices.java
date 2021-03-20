package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;


public class ItemServices implements FacadeItem {

	ItemDAO itemDatabase; // item database manager class
	UserDAO userDatabase; // user database manager class

	public ItemServices() throws DataException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
		userDatabase = UserDAOMemory.getInstance();
		

	}
	
	
	/*
	 * TODO Exception in Business layer: 
	 * - cadastros são responsaveis por fazerem validações de regras de negócio e lançar exceções correspondentes
	 * - Fachadas lançam excessões relacionadas a delimitações de transações com o banco de dados
	 * - Todas as excessões lançadas pelas camadas de Business e Data são repassadas para a camada GUI
	 */
	
	@Override
	public String createItem( Item newItem ) throws BusinessException, DataException {

		// validate item
		validateItem(newItem);
				
		if( newItem instanceof Tool ) {
			Tool toolDb = new Tool();

			// copy fields
			toolDb.setName( ((Tool) newItem).getName() );
			toolDb.setDescription( ((Tool) newItem).getDescription() );
			toolDb.setCode( ((Tool) newItem).getCode() );
			toolDb.setOwner( ((Tool) newItem).getOwner() );
			toolDb.setAvailable( ((Tool) newItem).isAvailable() );
			toolDb.setPrice( ((Tool) newItem).getPrice() );
			toolDb.setTermsOfUse( ((Tool) newItem).getTermsOfUse() );
			toolDb.setVoltage( ((Tool) newItem).getVoltage() );
			
			// verificar se item já está cadastrado ???
			
			// teremos campos opcionais, faremos algo com eles?
			
			// fill default review, grade and grade count
			toolDb.setLastReview("No reviews yet!");
			toolDb.setItemGrade(0);
			toolDb.setItemGradeCount(0);
			
			// require item registration in the database
			return itemDatabase.createItem( toolDb );
										
		} // TODO other items creation		
		
		return null;
	}

	@Override
	public Item readItem(String code) throws BusinessException, DataException {
		Item itemAux = itemDatabase.readItem( code );
		if( itemAux == null ) {
			throw new BusinessException("Can't read item because item code doesn't exist!");
		}
		return itemAux;
	}
	

	@Override
	public List<Item> readAll() {
		return itemDatabase.readAll();
	}

	@Override
	public List<Item> readAll(String owner) throws BusinessException {
		if( userDatabase.readUser(owner) == null ) {
			throw new BusinessException("Can't read user items, user not registered.");
		}
		
		return itemDatabase.readAll(owner);
	}

	@Override
	public List<Tool> readAllTools() {
		return itemDatabase.readAllTools();
	}


	@Override
	public String updateItem(Item item) throws BusinessException, DataException {
		// validate item
		validateItem(item);
				
		// check if code is from regitered item
		Item itemAux = itemDatabase.readItem( item.getCode() );

		if( itemAux == null ) {
			throw new BusinessException("Item not registered, thus cannot be updated!");
		}
		
		// check if user owns item
		if( !itemAux.getOwner().equals(item.getOwner()) ) {
			throw new BusinessException("Item does not belong you, thus cannot be updated!");
		}

		if( item instanceof Tool ) {
			Tool toolDb = new Tool();

			// copy fields
			toolDb.setName( ((Tool) item).getName() );
			toolDb.setDescription( ((Tool) item).getDescription() );
			toolDb.setCode( ((Tool) item).getCode() );
			toolDb.setOwner( ((Tool) item).getOwner() );
			toolDb.setAvailable( ((Tool) item).isAvailable() );
			toolDb.setPrice( ((Tool) item).getPrice() );
			toolDb.setTermsOfUse( ((Tool) item).getTermsOfUse() );
			toolDb.setVoltage( ((Tool) item).getVoltage() );
					
			// copy restricted fields
			toolDb.setLastReview( ((Tool) itemAux).getLastReview() );
			toolDb.setItemGrade( ((Tool) itemAux).getItemGrade() );
			toolDb.setItemGradeCount( ((Tool) itemAux).getItemGradeCount() );

			
			// require item registration in the database
			return itemDatabase.updateItem( toolDb );
									
		} // TODO other items update

		return null;
	}

	@Override
	public String deleteItem(Item item) throws BusinessException, DataException {
		// check if code is from registered item
		Item itemAux = itemDatabase.readItem( item.getCode() );
		if( itemAux == null ) {
			throw new BusinessException("Item not registered in the database, thus cannot be removed.");
		}
		
		// check if user owns item
		if(!itemAux.getOwner().equals(item.getOwner()) ) {
			throw new BusinessException("Item does not belong you! You can't remove it.");
		}
		
		return itemDatabase.deleteItem( itemAux );
		
		
	}

	@Override
	public void validateItem( Item item ) throws BusinessException {
		boolean hasViolations = false;
		List<String> exceptionMessages = new ArrayList<String>();
		
		if( item.getName() == null || item.getName().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add("Name is required.\n");
		}
		
		if( item.getDescription() == null || item.getDescription().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add("Description is required.\n");
		}
		
		// check if price is valid
		try {
			Double.parseDouble( item.getPrice() );
		} catch ( NullPointerException e1 ) {
			hasViolations = true;
			exceptionMessages.add("Price is required.\n");
		} catch ( NumberFormatException e2 ) {
			hasViolations = true;
			exceptionMessages.add("Price must be a number (don't use currency symbols).\n");
		}
		
		// TODO Check if owner is registered and already has already reached 10 items ads
		
		// TODO Check in any field has excess a characters limit
	
		
		if( item instanceof Tool ) {
			
			if( ( (Tool) item).getTermsOfUse() == null || ( (Tool) item).getTermsOfUse().isBlank() ) {
				hasViolations = true;
				exceptionMessages.add("Terms of Use are required.\n");
			}
			
			
			if( ( (Tool) item).getVoltage() == null || ( (Tool) item).getVoltage().isBlank() ) {
				hasViolations = true;
				exceptionMessages.add("Voltage is required (110, 220 or none values are accepted).\n");
			}
			
			// valid voltages: 110, 220 or none
			String voltage = ( (Tool) item).getVoltage();
			if( !voltage.equals("110") && !voltage.equals("220")  && !voltage.equals("none") ) {
				hasViolations = true;
				exceptionMessages.add("Voltage is invalid (110, 220 or none values are accepted).\n");				
			}

		}
		
		if( hasViolations ) {
			throw new BusinessException( exceptionMessages );
		}
				
	}


	@Override
	public String changeAvailability(String code) throws BusinessException, DataException {
		
		Item itemAux = itemDatabase.readItem( code );
		
		if( itemAux == null ) {
			throw new BusinessException("Item not registered, thus cannot change availability!");
		}
		
		itemAux.setAvailable( !itemAux.isAvailable() );
		
		return itemDatabase.updateItem( itemAux );

	}


	/*
	@Override
	public void addItemReview(String code, int grade, String review) {
		// TODO Auto-generated method stub

	}
	*/
	

}
