package br.ufrn.imd.pds.application;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import br.ufrn.imd.pds.business.YouShareBot;

public class YouShareApp {

	public static void main(String[] args) {

		System.out.println("Starting Bot");
		
		try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new YouShareBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

}
