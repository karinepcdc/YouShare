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
	ItemValidator itemValidationStrategy; // validation strategy for different subclasses of items
	ItemAvailabilityChanger changeAvailabilityStrategy; // change availability accordantly to the defined strategy

	public ItemServices() throws DataException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
		userDatabase = UserDAOMemory.getInstance();
		
		// define strategies
		itemValidationStrategy = new OfficeItemsValidator();
		changeAvailabilityStrategy = new OfficeItemsAvailabilityChanger();

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
				
		if( newItem instanceof OfficeItems ) {
			OfficeItems db = new OfficeItems();

			// copy fields
			db.setName( ((OfficeItems) newItem).getName() );
			db.setDescription( ((OfficeItems) newItem).getDescription() );
			db.setCode( ((OfficeItems) newItem).getCode() );
			db.setOwner( ((OfficeItems) newItem).getOwner() );
			db.setAvailable( ((OfficeItems) newItem).isAvailable() );
			db.setPrice( ((OfficeItems) newItem).getPrice() );
			db.setTermsOfUse( ((OfficeItems) newItem).getTermsOfUse() );
			db.setCondition( ((OfficeItems) newItem).getCondition() );
			db.setVoltage( ((OfficeItems) newItem).getVoltage() );
									
			// fill default review, grade and grade count
			db.setLastReview("No reviews yet!");
			db.setItemGrade(5);
			db.setItemGradeCount(0);
			
			// require item registration in the database
			return itemDatabase.createItem( db );
										
		} 		
		
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
	public String updateItem(Item item) throws BusinessException, DataException {
		// validate item
		validateItem(item);
				
		// check if code is from registered item
		Item itemAux = itemDatabase.readItem( item.getCode() );

		if( itemAux == null ) {
			throw new BusinessException("Item not registered, thus cannot be updated!");
		}
		
		// check if user owns item
		if( !itemAux.getOwner().equals(item.getOwner()) ) {
			throw new BusinessException("Item does not belong you, thus cannot be updated!");
		}

		if( item instanceof OfficeItems ) {
			OfficeItems db = new OfficeItems();

			// copy fields
			db.setName( ((OfficeItems) item).getName() );
			db.setDescription( ((OfficeItems) item).getDescription() );
			db.setCode( ((OfficeItems) item).getCode() );
			db.setOwner( ((OfficeItems) item).getOwner() );
			db.setAvailable( ((OfficeItems) item).isAvailable() );
			db.setPrice( ((OfficeItems) item).getPrice() );
			db.setTermsOfUse( ((OfficeItems) item).getTermsOfUse() );
			db.setCondition( ((OfficeItems) item).getCondition() );
			db.setVoltage( ((OfficeItems) item).getVoltage() );
					
			// copy restricted fields
			db.setLastReview( ((OfficeItems) itemAux).getLastReview() );
			db.setItemGrade( ((OfficeItems) itemAux).getItemGrade() );
			db.setItemGradeCount( ((OfficeItems) itemAux).getItemGradeCount() );

			
			// require item registration in the database
			return itemDatabase.updateItem( db );
									
		} 

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
			
		List<String> exceptionMessagesSpecific = this.itemValidationStrategy.itemValidator(item);
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
			throw new BusinessException("Register not found, thus cannot change availability!");
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
				"$weared","$good","$new","$under10","$10to20","$over20"));
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
