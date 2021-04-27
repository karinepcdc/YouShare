package br.ufrn.imd.pds.formParsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.business.OfficeItems;
import br.ufrn.imd.pds.exceptions.UIException;

public class FormToItem {
	
	public static OfficeItems createFormToOfficeItems( String officeItemsForm, String owner ) throws UIException {
		
		// OfficeItems form pattern
		String REGEX = "<Name>\\s*(.+?)\\s*</Name>.*?\n"
				 + "<Description>\\s*(.+?)\\s*</Description>.*?\n"
				 + "<Condition>\\s*(.+?)\\s*</Condition>.*?\n"
				 + "<Voltage>\\s*(.+?)\\s*</Voltage>";
	
		Pattern idPattern = Pattern.compile(REGEX);
		Matcher m = idPattern.matcher(officeItemsForm);
		
		// if we find a match, get id
		String itemName = "";
		String itemDescription = "";
		String itemCondition = "";
		String itemVoltage = "";
		
		if( m.find() ) {
			itemName = m.group(1);
			itemDescription = m.group(2);
			itemCondition = m.group(5);
			itemVoltage = m.group(6);
			
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			System.out.println("Description read: ." + itemDescription + ".\n");
			System.out.println("Condition read: ." + itemCondition + ".\n");
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
	
		} else {
			throw new UIException("OfficeItems information could not be read.");
		}
		
		// create item: OfficeItems					
		OfficeItems newOfficeItems = new OfficeItems( itemName , itemDescription, "", owner, 0, 0, "", itemCondition, itemVoltage);
			
		return newOfficeItems;
	}

public static OfficeItems editFormToOfficeItems( String officeItemsEditForm, OfficeItems originalOfficeItems ) throws UIException {
			
		boolean AnyChange = false;
		
		// OfficeItems form pattern
		String RegexName = "<Name>\\s*(.+?)\\s*</Name>.*?\n?";
		String RegexDescription = "<Description>\\s*(.+?)\\s*</Description>.*?\n?";
		String RegexCondition = "<Condition>\\s*(.+?)\\s*</Condition>.*?\n";
		String RegexVoltage = "<Voltage>\\s*(.+?)\\s*</Voltage>.*?\n?";
	
		// variables
		String itemName = "";
		String itemDescription = "";
		String itemCondition = "";
		String itemVoltage = "";
	
		Pattern itemPattern;
		Matcher m;
			
		// check if name edition where requested
		itemPattern = Pattern.compile(RegexName);
		m = itemPattern.matcher(officeItemsEditForm);

		if( m.find() ) {
			itemName = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			
		} else {
			itemName = originalOfficeItems.getName();
		}
		
		// check if description edition where requested
		itemPattern = Pattern.compile(RegexDescription);
		m = itemPattern.matcher(officeItemsEditForm);

		if( m.find() ) {
			itemDescription = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Description read: ." + itemDescription + ".\n");
			
		} else {
			itemDescription = originalOfficeItems.getDescription();
		}
				
		// check if condition edition where requested
		itemPattern = Pattern.compile(RegexCondition);
		m = itemPattern.matcher(officeItemsEditForm);

		if( m.find() ) {
			itemCondition = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Condition read: ." + itemCondition + ".\n");
			
		} else {
			itemCondition = originalOfficeItems.getCondition();
		}
		
		// check if voltage edition where requested
		itemPattern = Pattern.compile(RegexVoltage);
		m = itemPattern.matcher(officeItemsEditForm);

		if( m.find() ) {
			itemVoltage = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
			
		} else {
			itemVoltage = originalOfficeItems.getVoltage();
		}
		
		if( !AnyChange ) {
			throw new UIException("OfficeItems update could not be read.\n"
					+ "Check instruction and try again.");
		}
		
		// create item: OfficeItems					
		OfficeItems newOfficeItems = new OfficeItems( itemName , itemDescription, originalOfficeItems.getCode(), originalOfficeItems.getOwner(), 0, 0, "", itemCondition, itemVoltage);
		newOfficeItems.setAvailable( originalOfficeItems.isAvailable() );
		
		return newOfficeItems;
	}

}
