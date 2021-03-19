package br.ufrn.imd.pds.commands;

import java.util.HashMap;

import br.ufrn.imd.pds.APIinterface.MessageData;


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
		commandsMap.put( "/register", new RegisterCommand() );
		commandsMap.put( "/unregister", new UnregisterCommand() );
		
		// Callback query answers
		commandsMap.put("Yes_unregisterConfirmation", new YesUnregisterCommand() );
	}
	
	public static void executeCommand( String command, MessageData message ) {
		commandsMap.get( command ).execute( message );
	}
	
	public static void undoCommand( String command ) { // TODO  faz algum sentido?
		commandsMap.get( command ).undo();
	}
	
	// isCommand?
	
}
