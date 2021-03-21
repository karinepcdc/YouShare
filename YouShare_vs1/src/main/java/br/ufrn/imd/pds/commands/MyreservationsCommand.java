package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class MyreservationsCommand implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		YouShareBotServices.myreservations( message );
	}

	@Override
	public void undo() {

	}

}
