package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.ReadItemFromDatabaseException;

public class ItemServices implements FacadeItem {

	ItemDAO itemDatabase; // database manager class

	public ItemServices() throws DataException, ReadItemFromDatabaseException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
	}
	
	
	/*
	 * TODO Exception in Business layer: 
	 * - cadastros são responsaveis por fazerem validações de regras de negócio e lançar exceções correspondentes
	 * - Fachadas lançam excessões relacionadas a delimitações de transações com o banco de dados
	 * - Todas as excessões lançadas pelas camadas de Business e Data são repassadas para a camada GUI
	 */
	
	@Override
	public void createItem( Item newItem ) throws BusinessException, DataException {

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
			itemDatabase.createItem( toolDb );
							
		} // TODO other items creation		
		
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
	public List<Tool> readAllTools() {
		return itemDatabase.readAllTools();
	}


	@Override
	public void updateItem(Item item) throws BusinessException, DataException {
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
					
			// TODO copy restricted fields
			toolDb.setLastReview( ((Tool) itemAux).getLastReview() );
			toolDb.setItemGrade( ((Tool) itemAux).getItemGrade() );
			toolDb.setItemGradeCount( ((Tool) itemAux).getItemGradeCount() );

			
			// require item registration in the database
			itemDatabase.updateItem( toolDb );
									
		} // TODO other items update

	}

	@Override
	public void deleteItem(Item item) throws BusinessException, DataException {
		// check if code is from regitered item
		Item itemAux = itemDatabase.readItem( item.getCode() );
		if( itemAux != null ) {
			throw new BusinessException("Item not registered in the database, thus cannot be removed.");
		}
		
		// check if user owns item
		if(itemAux.getOwner().equals(item.getOwner()) ) {
			itemDatabase.deleteItem( itemAux );
		} else {
			throw new BusinessException("Item does not belong you! You can't remove it.");
		}
			
		
		
	}

	@Override
	public void validateItem(Item item) throws BusinessException {
		boolean hasViolations = false;
		List<String> exeptionMessages = new ArrayList<String>();
		
		if( item.getName() == null || item.getName().isBlank() ) {
			hasViolations = true;
			exeptionMessages.add("Name is required.\n");
		}
		
		if( item.getDescription() == null || item.getDescription().isBlank() ) {
			hasViolations = true;
			exeptionMessages.add("Description is required.\n");
		}
		
		// check if price is a double
		try {
			Double.parseDouble( item.getPrice() );
		} catch ( NullPointerException e1 ) {
			hasViolations = true;
			exeptionMessages.add("Price is required.\n");
		} catch ( NumberFormatException e2 ) {
			hasViolations = true;
			exeptionMessages.add("Price must be a number (don't use currency symbols).\n");
		}
		
		// Check if owner is register and already has alredy reach 10 items ads
		
		// Check in any field has excess a characteres limmmit
		
		
		// 
		
		if( item instanceof Tool ) {
			
			if( ( (Tool) item).getTermsOfUse() == null || ( (Tool) item).getTermsOfUse().isBlank() ) {
				hasViolations = true;
				exeptionMessages.add("Terms of Use are required.\n");
			}
			
			
			if( ( (Tool) item).getVoltage() == null || ( (Tool) item).getVoltage().isBlank() ) {
				hasViolations = true;
				exeptionMessages.add("Voltage is required (110, 220 or none values are accepted).\n");
			}
			
			// Check if voltage is valid: 110, 220 or none
			if( ( (Tool) item).getVoltage() != "110" && ( (Tool) item).getVoltage() != "220" 
					&& ( (Tool) item).getVoltage() != "none" ) {
				hasViolations = true;
				exeptionMessages.add("Voltage is invalid (110, 220 or none values are accepted).\n");				
			}

		}
		
		if( hasViolations ) {
			throw new BusinessException( exeptionMessages );
		}
		
	}


	@Override
	public void changeAvailability(String code) throws BusinessException, DataException {
		
		Item itemAux = itemDatabase.readItem( code );
		
		if( itemAux == null ) {
			throw new BusinessException("Item not registered, thus cannot change availability!");
		}
		
		itemAux.setAvailable( !itemAux.isAvailable() );
		itemDatabase.updateItem( itemAux );

	}

	/*
	@Override
	public void addItemReview(String code, int grade, String review) {
		// TODO Auto-generated method stub

	}
	*/
	

}
