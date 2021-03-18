package br.ufrn.imd.pds.commands;

import br.ufrn.imd.pds.APIinterface.MessageData;

public interface CommandInterface {
	
	public void execute( MessageData message );
	
	public void undo();
	
}
