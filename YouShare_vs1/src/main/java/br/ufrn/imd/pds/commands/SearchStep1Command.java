package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;

public class SearchStep1Command implements CommandInterface {

	@Override
	public void execute( MessageData message ) {
		YouShareBotServices.searchStep1( message );
	}

	@Override
	public void undo() {

	}

}
