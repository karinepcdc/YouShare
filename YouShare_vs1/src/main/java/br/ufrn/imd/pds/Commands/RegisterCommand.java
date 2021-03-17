package br.ufrn.imd.pds.Commands;

public class RegisterCommand implements CommandInterface {
	
	public void execute () {
		YouShareBotReceiver.register();
	}
	
	public void undo () {
		// TODO: ??
	}
}
