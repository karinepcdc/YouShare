package br.ufrn.imd.pds.formParsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.exceptions.UIException;

public class FormToItem {
	
	public static Tool formToTool( String toolForm, String owner ) throws UIException {
		
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

}
