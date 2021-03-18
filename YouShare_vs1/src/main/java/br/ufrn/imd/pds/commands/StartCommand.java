package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class StartCommand implements CommandInterface {
	
	public void execute ( MessageData message ) {
		YouShareBotServices.start( message );
	}
	
	public void undo () {
		// TODO: ??
	}

	
}
