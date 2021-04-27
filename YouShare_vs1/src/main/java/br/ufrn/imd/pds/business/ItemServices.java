package br.ufrn.imd.pds.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vdurmont.emoji.EmojiParser;

import br.ufrn.imd.pds.data.ItemDAO;
import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.data.UserDAO;
import br.ufrn.imd.pds.data.UserDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;


public class ItemServices implements FacadeItem {

	ItemDAO itemDatabase; // item database manager class
	UserDAO userDatabase; // user database manager class
	ItemValidator validationStrategy; // validation strategy for diferent subclasses of items
	ItemAvailabilityChanger changeAvailabilityStrategy; // change availability accordantly to the defined strategy

	public ItemServices() throws DataException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
		userDatabase = UserDAOMemory.getInstance();
		
		// define strategies
		validationStrategy = new ApplianceValidator();
		changeAvailabilityStrategy = new ApplianceAvailabilityChanger();

	}
	
	
	/*
	 * Exception in Business layer: 
	 * - cadastros são responsaveis por fazerem validações de regras de negócio e lançar exceções correspondentes
	 * - Fachadas lançam excessões relacionadas a delimitações de transações com o banco de dados
	 * - Todas as excessões lançadas pelas camadas de Business e Data são repassadas para a camada GUI
	 */
	@Override
	public String createItem( Item newItem ) throws BusinessException, DataException {

		// validate item
		validateItem(newItem);
				
		if( newItem instanceof Appliance ) {
			Appliance applianceDb = new Appliance();

			// copy fields
			applianceDb.setName( ((Appliance) newItem).getName() );
			applianceDb.setDescription( ((Appliance) newItem).getDescription() );
			applianceDb.setCode( ((Appliance) newItem).getCode() );
			applianceDb.setOwner( ((Appliance) newItem).getOwner() );
			applianceDb.setAvailable( ((Appliance) newItem).isAvailable() );
			applianceDb.setPrice( ((Appliance) newItem).getPrice() );
			applianceDb.setTermsOfUse( ((Appliance) newItem).getTermsOfUse() );
			applianceDb.setCondition( ((Appliance) newItem).getCondition() );
			applianceDb.setVoltage( ((Appliance) newItem).getVoltage() );
									
			// fill default review, grade and grade count
			applianceDb.setLastReview("No reviews yet!");
			applianceDb.setItemGrade(5);
			applianceDb.setItemGradeCount(0);
			
			// require item registration in the database
			return itemDatabase.createItem( applianceDb );
										
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
	public List<Item> readAll(List<String> name, List<String> filters) throws BusinessException, DataException {
		
		// validate search
		validadeSearch(name, filters);
		
		return itemDatabase.readAll(name, filters);
	}
	

	@Override
	public List<Appliance> readAllAppliances() {
		return itemDatabase.readAllAppliances();
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

		if( item instanceof Appliance ) {
			Appliance applianceDb = new Appliance();

			// copy fields
			applianceDb.setName( ((Appliance) item).getName() );
			applianceDb.setDescription( ((Appliance) item).getDescription() );
			applianceDb.setCode( ((Appliance) item).getCode() );
			applianceDb.setOwner( ((Appliance) item).getOwner() );
			applianceDb.setAvailable( ((Appliance) item).isAvailable() );
			applianceDb.setPrice( ((Appliance) item).getPrice() );
			applianceDb.setTermsOfUse( ((Appliance) item).getTermsOfUse() );
			applianceDb.setCondition( ((Appliance) item).getCondition() );
			applianceDb.setVoltage( ((Appliance) item).getVoltage() );
					
			// copy restricted fields
			applianceDb.setLastReview( ((Appliance) itemAux).getLastReview() );
			applianceDb.setItemGrade( ((Appliance) itemAux).getItemGrade() );
			applianceDb.setItemGradeCount( ((Appliance) itemAux).getItemGradeCount() );

			
			// require item registration in the database
			return itemDatabase.updateItem( applianceDb );
									
		} // TODO other items update

		return null;
	}

	@Override
	public String deleteItem( Item item ) throws BusinessException, DataException {
		// check if code its a from registered item
		Item itemAux = itemDatabase.readItem( item.getCode() );
		if( itemAux == null ) {
			throw new BusinessException("Item not registered in the database, thus cannot be removed.");
		}
		
		// check if user owns item
		if(!itemAux.getOwner().equals( item.getOwner()) ) {
			throw new BusinessException("Item does not belong you! You can't remove it.");
		}
		
		return itemDatabase.deleteItem( itemAux );
		
		
	}


	@Override
	public void deleteItem( String user ) throws BusinessException, DataException {		
		itemDatabase.deleteItem( user );
	}
	
	
	@Override
	public void validateItem( Item item ) throws BusinessException {
		boolean hasViolations = false;
		List<String> exceptionMessages = new ArrayList<String>();
		
		if( item.getName() == null || item.getName().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add("Name is required.");
		}
		
		if( item.getDescription() == null || item.getDescription().isBlank() ) {
			hasViolations = true;
			exceptionMessages.add("Description is required.");
		}
			
		List<String> exceptionMessagesSpecific = this.validationStrategy.itemValidator(item);
		if( !(exceptionMessagesSpecific.isEmpty()) ) {
			hasViolations = true;
			exceptionMessages.addAll( exceptionMessagesSpecific );
		}
		
		if( hasViolations ) {
			String errorMsg = "";
			
			for( String error: exceptionMessages ) {
				errorMsg += EmojiParser.parseToUnicode(":warning: ") + error + "\n";
			}
			
			throw new BusinessException( errorMsg );
		}
				
	}


	@Override
	public String changeAvailability(String code) throws BusinessException, DataException {
		
		
		Item itemAux = itemDatabase.readItem( code );
		
		if( itemAux == null ) {
			throw new BusinessException("Appliance not registered, thus cannot change availability!");
		} 
				
		Item item_ret = changeAvailabilityStrategy.changeAvailability(itemAux);
		
		return itemDatabase.updateItem( item_ret );
	}

	public void validadeSearch(List<String> name, List<String> filters) throws BusinessException, DataException  {
		boolean hasViolations = false;
		List<String> exceptionMessages = new ArrayList<String>();

		if( name == null || name.isEmpty() ) {
			hasViolations = true;
			exceptionMessages.add("Search returned no results. Specify the name of what you want to search.");
		} 
		
		List<String> validFilters = new ArrayList<String>(Arrays.asList("$grade1+","$grade2+","$grade3+","$grade4+",
				"$weared","$good","$new","$under10","10to20","over20"));
		if(filters != null && !validFilters.containsAll(filters)) {
			hasViolations = true;
			exceptionMessages.add("Filters are not valid.");
		}
		
		if( hasViolations ) {
			String errorMsg = "";
			
			for( String error: exceptionMessages ) {
				errorMsg += EmojiParser.parseToUnicode(":warning: ") + error + "\n";
			}
			
			throw new BusinessException( errorMsg );
		}
		
	}

	
	@Override
	public void validateId(String code, String user) throws BusinessException {

		try {
			Long.parseLong(code);
		} catch ( NumberFormatException e ) {
			throw new BusinessException("Id is not a integer number.");
		}
		
		Item item = itemDatabase.readItem(code);
		if( item == null ) { 
			throw new BusinessException("Item with id " + code + " not found.");
		}
		
		if( !item.getOwner().equals(user) ) {
			throw new BusinessException("Item with id " + code + " does not belong to you.");
		}
		
	}



	/*
	@Override
	public void addItemReview(String code, int grade, String review) {
		// TODO Auto-generated method stub

	}
	*/
	

}
