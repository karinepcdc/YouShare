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
				 + "<Price>\\s*(.+?)\\s*</Price>.*?\n"
				 + "<Terms of use>\\s*(.+?)\\s*</Terms of use>.*?\n"
				 + "<Condition>\\s*(.+?)\\s*</Condition>.*?\n"
				 + "<Voltage>\\s*(.+?)\\s*</Voltage>";
	
		Pattern idPattern = Pattern.compile(REGEX);
		Matcher m = idPattern.matcher(form);
		
		// if we find a match, get id
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		String itemTOU = "";
		String itemCondition = "";
		String itemVoltage = "";
		
		if( m.find() ) {
			itemName = m.group(1);
			itemDescription = m.group(2);
			itemPrice = m.group(3);
			itemTOU = m.group(4);
			itemCondition = m.group(5);
			itemVoltage = m.group(6);
			
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			System.out.println("Description read: ." + itemDescription + ".\n");
			System.out.println("Price read: ." + itemPrice + ".\n");
			System.out.println("TOU read: ." + itemTOU + ".\n");
			System.out.println("Condition read: ." + itemCondition + ".\n");
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
	
		} else {
			throw new UIException("Item information could not be read.");
		}
		
		// create concrete instance of item					
		SharedService newSharedService = new SharedService( itemName , itemDescription, "", owner, 0, 0, "", itemPrice, itemTOU, itemCondition, itemVoltage);
			
		return newSharedService;
	}

public static SharedService editFormToSharedService( String editForm, SharedService originalItem ) throws UIException {
			
		boolean AnyChange = false;
		
		// form pattern
		String RegexName = "<Name>\\s*(.+?)\\s*</Name>.*?\n?";
		String RegexDescription = "<Description>\\s*(.+?)\\s*</Description>.*?\n?";
		String RegexPrice = "<Price>\\s*(.+?)\\s*</Price>.*?\n?";
		String RegexTOU = "<Terms of use>\\s*(.+?)\\s*</Terms of use>.*?\n?";
		String RegexCondition = "<Condition>\\s*(.+?)\\s*</Condition>.*?\n";
		String RegexVoltage = "<Voltage>\\s*(.+?)\\s*</Voltage>.*?\n?";
	
		// variables
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		String itemTOU = "";
		String itemCondition = "";
		String itemVoltage = "";
	
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
		
		// check if TOU edition where requested
		itemPattern = Pattern.compile(RegexTOU);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemTOU = m.group(1);
			AnyChange = true;
			// log
			System.out.println("TOU read: ." + itemTOU + ".\n");
			
		} else {
			itemTOU = originalItem.getTermsOfUse();
		}
		
		// check if condition edition where requested
		itemPattern = Pattern.compile(RegexCondition);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemCondition = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Condition read: ." + itemCondition + ".\n");
			
		} else {
			itemCondition = originalItem.getCondition();
		}
		
		// check if voltage edition where requested
		itemPattern = Pattern.compile(RegexVoltage);
		m = itemPattern.matcher(editForm);

		if( m.find() ) {
			itemVoltage = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
			
		} else {
			itemVoltage = originalItem.getVoltage();
		}
		
		if( !AnyChange ) {
			throw new UIException("Item update request could not be read.\n"
					+ "Check instruction and try again.");
		}
		
		// create item					
		SharedService newSharedService = new SharedService( itemName , itemDescription, originalItem.getCode(), originalItem.getOwner(), 0, 0, "", itemPrice, itemTOU, itemCondition, itemVoltage);
		newSharedService.setAvailable( originalItem.isAvailable() );
		
		return newSharedService;
	}

}
