package br.ufrn.imd.pds.Commands;

import java.util.HashMap;

public class CommandsInvoker {
	private static HashMap <String, CommandInterface> commandsMap = new HashMap<String, CommandInterface>();
	
	static {
		commandsMap.put( "/start", new StartCommand() );
		commandsMap.put( "/register", new RegisterCommand() );
	}
	
	public static void executeCommand( String command ) {
		commandsMap.get( command ).execute();
	}
}
