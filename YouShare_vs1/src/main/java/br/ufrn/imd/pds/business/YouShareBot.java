package br.ufrn.imd.pds.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/// Class that create the YoushareBot as a Long Polling Bot.
/**
 * Class that create YouShareBot and defines default action when a 
 * update is received. 
 *
 */
public class YouShareBot extends TelegramLongPollingBot {
	
	private String TOKEN;
	private String USERNAME;

	
	/* Default constructor */	
	public YouShareBot () {		
		// set bot token
		try {
			File botConfiguration = new File("src/main/conf/confBot");
			Scanner myReader = new Scanner(botConfiguration);
			
			TOKEN= myReader.nextLine();
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file. Bot could not be configured.");
			e.printStackTrace();
		}
		
		// set bot user name
		USERNAME="YouShareBot";
	}
	

	/// Class that check for updates from the user
	@Override
	public void onUpdateReceived(Update update) {
		
		// Check if the update has a message and the message has text
	    if ( update.hasMessage() && update.getMessage().hasText() ) {
	    	// set variables
	    	String message_text = update.getMessage().getText();
	    	String chat_id = update.getMessage().getChatId().toString();
	    	
	    	// define /start command
	    	if( message_text.equals("/start") ) {
	    		// Create a SendMessage object with mandatory fields
		        SendMessage message = new SendMessage(); 
		        
		        message.setChatId(chat_id);
		        message.setText("Welcome to the YouShare community!");
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	} else { // unknow command
	    		// Create a SendMessage object with mandatory fields
		        SendMessage message = new SendMessage(); 
		        
		        message.setChatId(chat_id);
		        message.setText("Unknow command...Check what I can do typing /help.");
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	}
	
	
	    } 
	}

	@Override
	public String getBotUsername() {
		return USERNAME;
	}

	@Override
	public String getBotToken() {
		return TOKEN;
	}
	
	

}
