package br.ufrn.imd.pds.application;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;

public class YouShareApp {

	public static void main( String[] args ) {

		System.out.println("Starting Bot");
		
		try {
			
            TelegramBotsApi botsApi = new TelegramBotsApi( DefaultBotSession.class );
            
            botsApi.registerBot( TelegramBotAPIServices.getInstance() );
            
        } catch ( TelegramApiException e ) {
            e.printStackTrace();
        }
		
		System.out.println( "YouShareBot successfully started!\n" );
	}

}
