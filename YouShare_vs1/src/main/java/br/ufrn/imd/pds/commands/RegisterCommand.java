package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;
import br.ufrn.imd.pds.exceptions.UserAlreadyRegisteredException;

public class RegisterCommand implements CommandInterface {
	
	public void execute ( MessageData message ) {
		try {
			YouShareBotServices.register( message );
		} catch ( UserAlreadyRegisteredException e ) {
			e.printStackTrace();
		}
	}
	
	public void undo () {
		// TODO: ??
	}
}
