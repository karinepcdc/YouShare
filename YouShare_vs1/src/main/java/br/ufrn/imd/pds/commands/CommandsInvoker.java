package br.ufrn.imd.pds.commands;

import java.util.HashMap;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.exceptions.UIException;


public class CommandsInvoker {
	private static HashMap <String, CommandInterface> commandsMap = new HashMap<String, CommandInterface>();
	
	static {
		commandsMap.put( "/start", new StartCommand() );
		commandsMap.put( "/help", new HelpCommand() );
		commandsMap.put( "/register", new RegisterCommand() );
		commandsMap.put( "/unregister", new UnregisterCommand() );
		commandsMap.put( "/profile", new ProfileCommand() );
		commandsMap.put( "/myshare", new MyshareCommand() );
		commandsMap.put( "/itemdetails", new ItemDetailsCommand() );
		commandsMap.put( "/changeadstatus", new ChangeAdStatusCommand() );
		commandsMap.put( "/deleteitem", new DeleteItemCommand() );
		commandsMap.put( "/myreservations", new MyreservationsCommand() );
		commandsMap.put( "/search", new SearchCommand() );
		
		// Callback query answers
		commandsMap.put("Yes_unregister", new YesUnregisterCommand() );
		commandsMap.put("No_unregister", new NoUnregisterCommand() );

	}
	
	public static void executeCommand( String command, MessageData message ) throws UIException {
		
		if( commandsMap.get( command ) != null ) {
			commandsMap.get( command ).execute( message );
		} else {
			throw new UIException( "Command not defined. \n" ); 
		}
	}
	
	public static void undoCommand( String command ) {
		commandsMap.get( command ).undo();
	}

	
}
