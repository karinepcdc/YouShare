package br.ufrn.imd.pds.commands;

public class RegisterCommand implements CommandInterface {
	
	public void execute () {
		YouShareBotReceiver.register();
	}
	
	public void undo () {
		// TODO: ??
	}
}
