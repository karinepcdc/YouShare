package br.ufrn.imd.pds.formParsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.business.SharedService;
import br.ufrn.imd.pds.exceptions.UIException;

public class FormToItem {
	
	public static SharedService createFormToSharedService( String form, String owner ) throws UIException {
		
		// form pattern
		String REGEX = "<Name>\\s*(.+?)\\s*</Name>.*?\n"
				 + "<Description>\\s*(.+?)\\s*</Description>.*?\n"
				 + "<Price>\\s*(.+?)\\s*</Price>.*?\n";
	
		Pattern idPattern = Pattern.compile(REGEX);
		Matcher m = idPattern.matcher(form);
		
		// if we find a match, get id
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		
		if( m.find() ) {
			itemName = m.group(1);
			itemDescription = m.group(2);
			itemPrice = m.group(3);
			
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			System.out.println("Description read: ." + itemDescription + ".\n");
			System.out.println("Price read: ." + itemPrice + ".\n");
	
		} else {
			throw new UIException("Item information could not be read.");
		}
		
		// create concrete instance of item					
		SharedService newSharedService = new SharedService( itemName , itemDescription, "", owner, 0, 0, "", itemPrice);
			
		return newSharedService;
	}

public static SharedService editFormToSharedService( String editForm, SharedService originalItem ) throws UIException {
			
		boolean AnyChange = false;
		
		// form pattern
		String RegexName = "<Name>\\s*(.+?)\\s*</Name>.*?\n?";
		String RegexDescription = "<Description>\\s*(.+?)\\s*</Description>.*?\n?";
		String RegexPrice = "<Price>\\s*(.+?)\\s*</Price>.*?\n?";
		
		// variables
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		
		Pattern itemPattern;
		Matcher m;
			
		// check if name edition where requested
		itemPattern = Pattern.compile(RegexName);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemName = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			
		} else {
			itemName = originalItem.getName();
		}
		
		// check if description edition where requested
		itemPattern = Pattern.compile(RegexDescription);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemDescription = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Description read: ." + itemDescription + ".\n");
			
		} else {
			itemDescription = originalItem.getDescription();
		}
		
		// check if price edition where requested
		itemPattern = Pattern.compile(RegexPrice);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemPrice = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Price read: ." + itemPrice + ".\n");
			
		} else {
			itemPrice = originalItem.getPrice();
		}
				
		if( !AnyChange ) {
			throw new UIException("Item update request could not be read.\n"
					+ "Check instruction and try again.");
		}
		
		// create item					
		SharedService newSharedService = new SharedService( itemName , itemDescription, originalItem.getCode(), originalItem.getOwner(), 0, 0, "", itemPrice);
		newSharedService.setAvailable( originalItem.isAvailable() );
		
		return newSharedService;
	}

}
