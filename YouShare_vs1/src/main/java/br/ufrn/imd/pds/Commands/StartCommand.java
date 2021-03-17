package br.ufrn.imd.pds.Commands;

public class StartCommand implements CommandInterface {
	
	public void execute () {
		YouShareBotReceiver.start();
	}
	
	public void undo () {
		// TODO: ??
	}
}
