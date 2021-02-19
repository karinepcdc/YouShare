package br.ufrn.imd.pds.business;

import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class YouShareBot extends TelegramLongPollingBot {

	BotCommand start;
	List<BotCommand> ysCommands;
	
	
	public YouShareBot () {
		start = new BotCommand("start", "start YouShareBot");
		
		
	}
	
	
	public BotCommand getStart() {
		return start;
	}

	public void setStart(BotCommand start) {
		this.start = start;
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		// We check if the update has a message and the message has text
	    if (update.hasMessage() && update.getMessage().hasText()) {
	    	// Create a SendMessage object with mandatory fields
	        SendMessage message = new SendMessage(); 
	        
	        message.setChatId(update.getMessage().getChatId().toString());
	        message.setText(update.getMessage().getText());
	        
	        try {
	            execute(message); // Call method to send the message
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public String getBotUsername() {
		return "YouShareBot";
	}

	@Override
	public String getBotToken() {
		return "1659104965:AAH35gseMyELMa3PkHY_uWt_PzJy4Wv63Vo";
	}
	
	

}
