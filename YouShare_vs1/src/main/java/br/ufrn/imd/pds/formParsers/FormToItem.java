package br.ufrn.imd.pds.formParsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.business.ItemServices;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.UIException;

public class FormToItem {
	
	public static Tool createFormToTool( String toolForm, String owner ) throws UIException {
		
		// Tool form pattern
		String REGEX = "<Name>\\s*(.+?)\\s*</Name>.*?\n"
				 + "<Description>\\s*(.+?)\\s*</Description>.*?\n"
				 + "<Price>\\s*(.+?)\\s*</Price>.*?\n"
				 + "<Terms of use>\\s*(.+?)\\s*</Terms of use>.*?\n"
				 + "<Voltage>\\s*(.+?)\\s*</Voltage>";
	
		Pattern idPattern = Pattern.compile(REGEX);
		Matcher m = idPattern.matcher(toolForm);
		
		// if we find a match, get id
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		String itemTOU = "";
		String itemVoltage = "";
		
		if( m.find() ) {
			itemName = m.group(1);
			itemDescription = m.group(2);
			itemPrice = m.group(3);
			itemTOU = m.group(4);
			itemVoltage = m.group(5);
			
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			System.out.println("Description read: ." + itemDescription + ".\n");
			System.out.println("Price read: ." + itemPrice + ".\n");
			System.out.println("TOU read: ." + itemTOU + ".\n");
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
	
		} else {
			throw new UIException("Tool information could not be read.");
		}
		
		// create item: Tool					
		Tool newTool = new Tool( itemName , itemDescription, "", owner, 0, 0, "", itemPrice, itemTOU, itemVoltage);
			
		return newTool;
	}

public static Tool editFormToTool( String toolEditForm, Tool originalTool ) throws UIException {
			
		boolean AnyChange = false;
		
		// Tool form pattern
		String RegexName = "<Name>\\s*(.+?)\\s*</Name>.*?\n?";
		String RegexDescription = "<Description>\\s*(.+?)\\s*</Description>.*?\n?";
		String RegexPrice = "<Price>\\s*(.+?)\\s*</Price>.*?\n?";
		String RegexTOU = "<Terms of use>\\s*(.+?)\\s*</Terms of use>.*?\n?";
		String RegexVoltage = "<Voltage>\\s*(.+?)\\s*</Voltage>.*?\\n?";
	
		// variables
		String itemName = "";
		String itemDescription = "";
		String itemPrice = "";
		String itemTOU = "";
		String itemVoltage = "";
	
		Pattern itemPattern;
		Matcher m;
			
		// check if name edition where requested
		itemPattern = Pattern.compile(RegexName);
		m = itemPattern.matcher(toolEditForm);

		if( m.find() ) {
			itemName = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Name read: ." + itemName + ".\n");
			
		} else {
			itemName = originalTool.getName();
		}
		
		// check if description edition where requested
		itemPattern = Pattern.compile(RegexDescription);
		m = itemPattern.matcher(toolEditForm);

		if( m.find() ) {
			itemDescription = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Description read: ." + itemDescription + ".\n");
			
		} else {
			itemDescription = originalTool.getDescription();
		}
		
		// check if price edition where requested
		itemPattern = Pattern.compile(RegexPrice);
		m = itemPattern.matcher(toolEditForm);

		if( m.find() ) {
			itemPrice = m.group(1);
			AnyChange = true;
			
			// log
			System.out.println("Price read: ." + itemPrice + ".\n");
			
		} else {
			itemPrice = originalTool.getPrice();
		}
		
		// check if TOU edition where requested
		itemPattern = Pattern.compile(RegexTOU);
		m = itemPattern.matcher(toolEditForm);

		if( m.find() ) {
			itemTOU = m.group(1);
			AnyChange = true;
			// log
			System.out.println("TOU read: ." + itemTOU + ".\n");
			
		} else {
			itemTOU = originalTool.getTermsOfUse();
		}
		
		// check if voltage edition where requested
		itemPattern = Pattern.compile(RegexVoltage);
		m = itemPattern.matcher(toolEditForm);

		if( m.find() ) {
			itemVoltage = m.group(1);
			AnyChange = true;
			// log
			System.out.println("Voltage read: ." + itemVoltage + ".\n");
			
		} else {
			itemVoltage = originalTool.getVoltage();
		}
		
		if( !AnyChange ) {
			throw new UIException("Tool update could not be read.\n"
					+ "Check instruction and try again.");
		}
		
		// create item: Tool					
		Tool newTool = new Tool( itemName , itemDescription, originalTool.getCode(), originalTool.getOwner(), 0, 0, "", itemPrice, itemTOU, itemVoltage);
		
		
		return newTool;
	}

}
