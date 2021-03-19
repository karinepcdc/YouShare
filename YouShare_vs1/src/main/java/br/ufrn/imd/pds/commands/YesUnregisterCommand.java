package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;
import br.ufrn.imd.pds.exceptions.UserNotRegisteredException;

public class YesUnregisterCommand implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		try {
			YouShareBotServices.yesUnregister( message );
		} catch ( UserNotRegisteredException e ) {
			e.printStackTrace();
		}

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
