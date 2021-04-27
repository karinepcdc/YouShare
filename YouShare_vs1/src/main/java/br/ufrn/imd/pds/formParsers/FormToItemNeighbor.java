package br.ufrn.imd.pds.formParsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.business.OfficeItems;
import br.ufrn.imd.pds.business.ItemNeighbor;
import br.ufrn.imd.pds.exceptions.UIException;

public class FormToItemNeighbor {
	
public static ItemNeighbor createFormToItemNeighbor( String itemNeighborForm ) throws UIException {
		
		// OfficeItems form pattern
		String REGEX = "<Condominium>\\s*(.+?)\\s*</Condominium>.*?\n";
				 
	
		Pattern idPattern = Pattern.compile( REGEX );
		Matcher m = idPattern.matcher( itemNeighborForm );
		
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
			throw new UIException("OfficeItems information could not be read.");
		}
		
		// create item: OfficeItems					
		//OfficeItems newAppliance = new OfficeItems( itemName , itemDescription, "", owner, 0, 0, "", itemPrice, itemTOU, itemCondition, itemVoltage);
			
		//return newAppliance;
		return null;
	}

public static OfficeItems editFormToAppliance( String applianceEditForm, OfficeItems originalAppliance ) throws UIException {
			
		boolean AnyChange = false;
		
		// OfficeItems form pattern
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
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemName = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			
		} else {
			itemName = originalAppliance.getName();
		}
		
		// check if description edition where requested
		itemPattern = Pattern.compile(RegexDescription);
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemDescription = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Description read: ." + itemDescription + ".\n");
			
		} else {
			itemDescription = originalAppliance.getDescription();
		}
		
		// check if price edition where requested
		itemPattern = Pattern.compile(RegexPrice);
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemPrice = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Price read: ." + itemPrice + ".\n");
			
		} else {
			itemPrice = originalAppliance.getPrice();
		}
		
		// check if TOU edition where requested
		itemPattern = Pattern.compile(RegexTOU);
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemTOU = m.group(1);
			AnyChange = true;
			// log
			System.out.println("TOU read: ." + itemTOU + ".\n");
			
		} else {
			itemTOU = originalAppliance.getTermsOfUse();
		}
		
		// check if condition edition where requested
		itemPattern = Pattern.compile(RegexCondition);
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemCondition = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Condition read: ." + itemCondition + ".\n");
			
		} else {
			itemCondition = originalAppliance.getCondition();
		}
		
		// check if voltage edition where requested
		itemPattern = Pattern.compile(RegexVoltage);
		m = itemPattern.matcher(applianceEditForm);

		if( m.find() ) {
			itemVoltage = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
			
		} else {
			itemVoltage = originalAppliance.getVoltage();
		}
		
		if( !AnyChange ) {
			throw new UIException("OfficeItems update could not be read.\n"
					+ "Check instruction and try again.");
		}
		
		// create item: OfficeItems					
		OfficeItems newAppliance = new OfficeItems( itemName , itemDescription, originalAppliance.getCode(), originalAppliance.getOwner(), 0, 0, "", itemPrice, itemTOU, itemCondition, itemVoltage);
		newAppliance.setAvailable( originalAppliance.isAvailable() );
		
		return newAppliance;
	}
	

}
