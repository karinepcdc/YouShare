package br.ufrn.imd.pds.commands;

public class StartCommand implements CommandInterface {
	
	public void execute () {
		YouShareBotReceiver.start();
	}
	
	public void undo () {
		// TODO: ??
	}
}
