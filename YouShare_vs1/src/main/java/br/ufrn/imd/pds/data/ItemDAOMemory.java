package br.ufrn.imd.pds.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import br.ufrn.imd.pds.DBHandlers.DBReader;
import br.ufrn.imd.pds.DBHandlers.DBWriter;
import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.ReadItemFromDatabaseException;

public class ItemDAOMemory implements ItemDAO {
	
	private HashMap<String, Item > itemMap;
	
	private static ItemDAOMemory uniqueInstance;
	
	/* Default constructor */
	public ItemDAOMemory() throws ReadItemFromDatabaseException {
		System.out.println( "ItemDAOMemory's constructor started\n" );
		
		startDatabase();
	}
	
	/* Singleton constructor */
	public static synchronized ItemDAOMemory getInstance() throws ReadItemFromDatabaseException {
		if( uniqueInstance == null ) {
			uniqueInstance = new ItemDAOMemory();
		}
		return uniqueInstance;
		
	}


	/*
	 * TODO Exception in data layer: DAOS concretos lançam exceções de acesso ao banco de dados;
	 */
	
	@Override
	public void startDatabase( ) throws ReadItemFromDatabaseException {
		
		// start item database
		try {
			itemMap = DBReader.csvToItemHashMap();
		} catch ( IOException e ) {
			throw new ReadItemFromDatabaseException();
		}
	}
	
	@Override
	public void createItem( Item newItem ) throws DataException {
		
		boolean isRegistered = itemMap.containsKey( newItem.getCode() );		

		if( isRegistered ) {
	
			// add Item to item map
			itemMap.put( newItem.getCode() ,  newItem );
				
			// update database
			try {
				DBWriter.itemHashMapToCSV( itemMap );
			} catch ( CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
				// TODO detail better exception, trying to tell what exactly have happened
				throw new DataException( "Problem trying to write new item in the database." );
			}
		} else {
			throw new DataException( "Item is alredy registered in the database." );
		}
		
	}
	
	@Override
	public Item readItem(String code) throws DataException {
		
		boolean isRegistered = itemMap.containsKey( code );		
		if( isRegistered ) {
			
			return itemMap.get( code );
			
		} else {
			throw new DataException("Item cannot be read because it is not register in the database.");
		}
		
	}

	@Override
	public List<Item> readAll() {
		ArrayList<Item> items = new ArrayList<Item>();
		
		// put tools from toolMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			items.add( pair.getValue() );
		}
		
		return items; // TODO check if it is fine to return empty list
	}
	

	@Override
	public List<Tool> readAllTools() {
		ArrayList<Tool> toolList = new ArrayList<Tool>();
		
		// put tools from toolMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			if( pair.getValue() instanceof Tool ) {
				toolList.add( (Tool) pair.getValue() );
			}
		}

		return toolList; // TODO check if it is fine to return empty list
	}

	
	@Override
	public void updateItem(Item item) throws DataException {

		boolean isRegistered = itemMap.containsKey( item.getCode() );		
		if( isRegistered ) {
			// Tool item
			if( item instanceof Tool ) {
				Tool toolAux = (Tool) itemMap.get( item.getCode() );
				
				toolAux.setName( 			((Tool) item ).getName() );
				toolAux.setCode( 			((Tool) item ).getCode() );
				toolAux.setDescription( 	((Tool) item ).getDescription() );
				toolAux.setItemGrade( 		((Tool) item ).getItemGrade() );
				toolAux.setItemGradeCount( 	((Tool) item ).getItemGradeCount() );
				toolAux.setLastReview( 		((Tool) item ).getLastReview() );
				toolAux.setAvailable( 		((Tool) item ).isAvailable() );
				toolAux.setPrice( 			((Tool) item ).getPrice() );
				toolAux.setTermsOfUse( 		((Tool) item ).getTermsOfUse() );
				toolAux.setVoltage( 		((Tool) item ).getVoltage() );
				
				// update item hashmap
				itemMap.put(toolAux.getCode(), toolAux);
			}
			
			// update database
			try {
				DBWriter.itemHashMapToCSV( itemMap );
			} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
				// TODO detail better exeption, trying to tell what exactly have happened
				throw new DataException("Problem trying to write updated item in the database.");
			}
			
		} else {
			throw new DataException("Item cannot be updated because it is not register in the database.");
		}
	}
	
	@Override
	public void deleteItem(Item item) throws DataException {
		
		boolean isRegistered = itemMap.containsKey( item.getCode() );		
		if( isRegistered ) {
			
			// remove item form item map
			itemMap.remove( item.getCode() );
			
			// update database
			try {
				DBWriter.itemHashMapToCSV( itemMap );
			}  catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
				// TODO detail better exeption, trying to tell what exactly have happened
				throw new DataException("Problem trying to update database after removing an item.");
			}
			
		} else {
			throw new DataException("Item cannot be removed because it is not register in the database.");
		}
		
	}

	
}
