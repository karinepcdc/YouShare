package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class UnegisterCommand implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		YouShareBotServices.unregister( message );
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
