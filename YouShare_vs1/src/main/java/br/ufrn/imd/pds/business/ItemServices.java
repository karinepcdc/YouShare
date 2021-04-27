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

	public ItemServices(ApplianceValidator itemValidator, ApplianceAvailabilityChanger itemAvailabilityChanger) throws DataException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
		userDatabase = UserDAOMemory.getInstance();
		
		// define strategies
		itemValidationStrategy = itemValidator;
		changeAvailabilityStrategy = itemAvailabilityChanger;

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
		
		// fill default review, grade and grade count
		newItem.setLastReview("No reviews yet!");
		newItem.setItemGrade(5);
		newItem.setItemGradeCount(0);
		
		// require item registration in the database
		return itemDatabase.createItem( newItem );
		
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

		// copy restricted fields
		item.setLastReview( itemAux.getLastReview() );
		item.setItemGrade( itemAux.getItemGrade() );
		item.setItemGradeCount( itemAux.getItemGradeCount() );

					
		// require item registration in the database
		return itemDatabase.updateItem( item );

	}

	@Override
	public String deleteItem( Item item ) throws BusinessException, DataException {
		// check if code its a from registered item
		Item itemAux = itemDatabase.readItem( item.getCode() );
		if( itemAux == null ) {
			throw new BusinessException("Item not registered in the database, thus cannot be removed.");
		}
		
		System.out.println("item code send to db: " + item.getCode());
		System.out.println("owner: " + item.getOwner());
		System.out.println("true owner: " + itemAux.getOwner());
		System.out.println("equal? " + itemAux.getOwner().equals( item.getOwner()) );
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
			throw new BusinessException("Item not registered, thus cannot change availability!");
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
