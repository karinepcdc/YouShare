package br.ufrn.imd.pds.commands;

import java.util.HashMap;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.exceptions.UIException;


/// Define availabe commands. 
/*
 * Allows client (YSbotService???)  to invoke a command by name (using a string).
 * 
 */
public class CommandsInvoker {
	private static HashMap <String, CommandInterface> commandsMap = new HashMap<String, CommandInterface>();
	
	/* 
	 * List of BotCommand YouShareBot answers.
	 * 
	 * command: Text of the command, 1-32 characters. 
	 * 			Can contain only lowercase English letters, digits and underscores.
	 * description: Description of the command, 3-256 characters.
	 */
	static {
		// BotCommands answers
		commandsMap.put( "/start", new StartCommand() );
		commandsMap.put( "/help", new HelpCommand() );
		commandsMap.put( "/register", new RegisterCommand() );
		commandsMap.put( "/unregister", new UnregisterCommand() );
		commandsMap.put( "/profile", new ProfileCommand() );
		commandsMap.put( "/myshare", new MyshareCommand() );
		commandsMap.put( "/itemdetails", new ItemDetailsCommand() );
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
			// TODO Especificar melhor erro
			throw new UIException("command not found"); 
		}
	}
	
	public static void undoCommand( String command ) { // TODO  faz algum sentido?
		commandsMap.get( command ).undo();
	}
	
	// isCommand?
	
}
