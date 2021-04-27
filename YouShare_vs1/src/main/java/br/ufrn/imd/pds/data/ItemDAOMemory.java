package br.ufrn.imd.pds.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import br.ufrn.imd.pds.DBHandlers.DBReader;
import br.ufrn.imd.pds.DBHandlers.DBWriter;
import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.SharedService;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.util.IdCounter;

public class ItemDAOMemory implements ItemDAO {
	
	private HashMap<String, Item > itemMap;
	
	private static ItemDAOMemory uniqueInstance;
	
	/* Default constructor */
	private ItemDAOMemory() throws DataException {
		System.out.println( "ItemDAOMemory's constructor started\n" );
		
		startDatabase();
	}
	
	/* Singleton constructor */
	public static synchronized ItemDAOMemory getInstance() throws DataException {
		if( uniqueInstance == null ) {
			uniqueInstance = new ItemDAOMemory();
		}
		return uniqueInstance;
		
	}


	/*
	 * Exception in data layer: DAOS concretos lançam exceções de acesso ao banco de dados;
	 */
	
	@Override
	public void startDatabase( ) throws DataException {
		
		// start item database
		try {
			itemMap = DBReader.csvToItemHashMap();
		} catch ( IOException e ) {
			throw new DataException("An error occurred trying to read item database file.");
		}
		
		// start idcounter with biggest item id in database
		if( !itemMap.isEmpty() ) {
			List<Long> idList = new ArrayList<Long>();
			for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
				idList.add( Long.valueOf( pair.getValue().getCode() ) );
			}
			
			long largestId = 0;
			for(long id: idList) {
				if( largestId < id ) {
					largestId = id;
				}
			}
			
			IdCounter.setCounter(largestId);
		}
	}
	
	@Override
	public String createItem( Item newItem ) throws DataException {

		// set unique code
		String code = String.valueOf(IdCounter.nextId());
		newItem.setCode( code );
		
		// add Item to item map
		itemMap.put( newItem.getCode() ,  newItem );
				
		// update database
		try {
			DBWriter.itemHashMapToCSV( itemMap );
		} catch ( CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
			throw new DataException( "Problem trying to write new item in the database." );
		}
		
		return newItem.getCode();
		
	}
	
	@Override
	public Item readItem(String code) {

		System.out.println("item code received to db: " + code);
		boolean isRegistered = itemMap.containsKey( code );		
		if( isRegistered ) {
			return itemMap.get( code );
		}
		
		return null;
		
	}

	@Override
	public List<Item> readAll() {
		ArrayList<Item> items = new ArrayList<Item>();
		
		// put items from itemMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			items.add( pair.getValue() );
		}
		
		return items; // TODO check if it is fine to return empty list
	}
	
	@Override
	public List<Item> readAll(String owner) {
		ArrayList<Item> items = new ArrayList<Item>();
		
		// put items from itemMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			Item item = pair.getValue();
			if( item.getOwner().equals(owner) ) {
				items.add( item );
			}
		}
		
		return items; // TODO check if it is fine to return empty list
	}
	
	@Override
	public List<Item> readAll(List<String> names, List<String> filters) throws DataException {
		ArrayList<Item> items = new ArrayList<Item>();

		if( filters == null || filters.isEmpty() ) {
			
			for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
				Item item = pair.getValue();
				List<String> itemNames = Arrays.asList( item.getName().toLowerCase().split("\\s+"));
				
				List<String> sn= new ArrayList<String>();
				names.forEach((n) -> sn.add(n.toLowerCase()));
				
				if(itemNames.containsAll(sn)) {
					items.add( item );
				}
			}
			
		} else {
			
			for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
				Item item = pair.getValue();
				boolean isAmatch = false;
				
				// check name
				List<String> itemNames = Arrays.asList( item.getName().toLowerCase().split("\\s+"));
				
				List<String> sn= new ArrayList<String>();
				names.forEach((n) -> sn.add(n.toLowerCase()));
				
				if(itemNames.containsAll(sn)) {
					isAmatch = true;
				}
				
				for( String filter: filters ) {
					
					// check filters
					if( filter.equals("$grade1+") ) {
						isAmatch = isAmatch && ( item.getItemGrade() >= 1.0 );
						
					} else if( filter.equals("$grade2+") ) {
						isAmatch = isAmatch && ( item.getItemGrade() >= 2.0 );
						
					} else if( filter.equals("$grade3+") ) {
						isAmatch = isAmatch && ( item.getItemGrade() >= 3.0 );
						
					} else if( filter.equals("$grade4+") ) {
						isAmatch = isAmatch && ( item.getItemGrade() >= 4.0 );
						
					} else if( filter.equals("$weared") ) {
						isAmatch = isAmatch && ( ((SharedService) item).getCondition().equals("weared") );
						
					} else if( filter.equals("$good") ) {
						isAmatch = isAmatch && ( ((SharedService) item).getCondition().equals("good") );
						
					} else if( filter.equals("$new") ) {
						isAmatch = isAmatch && ( ((SharedService) item).getCondition().equals("new") );
						
					} else if( filter.equals("$under10") && item instanceof SharedService ) {
						double price = Double.parseDouble( ((SharedService) item).getPrice() );
						isAmatch = isAmatch && ( price <= 10.0 );
						
					} else if( filter.equals("10to20") && item instanceof SharedService ) {
						double price = Double.parseDouble(((SharedService) item).getPrice());
						isAmatch = isAmatch && ( price >= 10.0 && price < 20.0 );
						
					} else if( filter.equals("over20") && item instanceof SharedService ) {
						double price = Double.parseDouble(((SharedService) item).getPrice());
						isAmatch = isAmatch && ( price >= 20.0 );
						
					} else {
						throw new DataException("Filter not valid.");
					}
				}
				
			
				
				// if found required item, add it 
				if( isAmatch ) {
					items.add( item );
				}
			}
			
		}
		
		return items;
	}
	
	@Override
	public String updateItem(Item item) throws DataException {
				
		// update item hashmap
		itemMap.put(item.getCode(), item);
			
		// update database
		try {
			DBWriter.itemHashMapToCSV( itemMap );
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			// TODO detail better exeption, trying to tell what exactly have happened
			throw new DataException("Problem trying to write updated item in the database.");
		}
		
		return item.getCode();
			
	}
	
	@Override
	public String deleteItem(Item item) throws DataException {
		
		// remove item from item map
		itemMap.remove( item.getCode() );
			
		// update database
		try {
			DBWriter.itemHashMapToCSV( itemMap );
		}  catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			throw new DataException("Problem trying to update database after removing item with id " + item.getCode() + ".");
		}
			
		return item.getCode();
	}

	@Override
	public void deleteItem(String user) throws DataException {
		
		ArrayList<Item> userItems = new ArrayList<Item>();
		
		// put user items from itemMap in a list
		for ( Map.Entry<String,Item> pair : itemMap.entrySet() ) {
			Item item = pair.getValue();
			if( item.getOwner().equals(user) ) {
				userItems.add( item );
			}
		}
		
		// remove items
		for( Item item: userItems ) {
			// remove item from item map
			itemMap.remove( item.getCode() );
		}
		
		// update database
		try {
			DBWriter.itemHashMapToCSV( itemMap );
		}  catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			throw new DataException("Problem trying to update database after removing items from user " + user + ".");
		}
		
		
	}


	
}
