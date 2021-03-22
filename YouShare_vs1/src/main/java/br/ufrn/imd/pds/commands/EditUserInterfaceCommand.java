package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class EditUserInterfaceCommand implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		YouShareBotServices.editUserInterface( message );
	}

	@Override
	public void undo() {

	}

}
