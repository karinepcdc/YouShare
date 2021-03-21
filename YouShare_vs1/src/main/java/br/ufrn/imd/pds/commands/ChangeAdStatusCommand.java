package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class ChangeAdStatusCommand implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		YouShareBotServices.changeAdStatus(message);
	}

	@Override
	public void undo() {

	}

}
