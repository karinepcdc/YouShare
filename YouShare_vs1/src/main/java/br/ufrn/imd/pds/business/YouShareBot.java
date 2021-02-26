package br.ufrn.imd.pds.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.vdurmont.emoji.EmojiParser; // to parse emojis
// check emojis at: 
//   https://emojipedia.org/beaming-face-with-smiling-eyes/
//   https://www.webfx.com/tools/emoji-cheat-sheet/

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
	private YouShareServices ysServices;

	
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
		
		// instantiate Services
		ysServices = new YouShareServices();
		
	}
	

	/// Class that check for updates from the user
	@Override
	public void onUpdateReceived(Update update) {
		
		// Check if the update has a message and the message has text
	    if ( update.hasMessage() && update.getMessage().hasText() ) {
	    	// set message variables
	    	String userMessageText = update.getMessage().getText();
	    	String chatId = update.getMessage().getChatId().toString();
	    	
	    	// set user variables
	    	String userFirstName =  update.getMessage().getChat().getFirstName();
	    	String userLastName = update.getMessage().getChat().getLastName();
	    	String userUserName = update.getMessage().getChat().getUserName();
	    	long userId = update.getMessage().getChat().getId();
	    	
	    	// set Bot reply variables
	        SendMessage message = new SendMessage(); 
	    	String botAnswer = "";
	    		    	
	    	
	    	/// process message
	    	if( userMessageText.equals("/start") ) { // define /start command
	    		
	    		// set message mandatory fields
		        botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:"); 
		        message.setChatId(chatId);
		        message.setText(botAnswer);
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	} else { // unknow command
	    		
	    		// set message mandatory fields
		        botAnswer = "Unknow command...Check what I can do typing /help.";
		        message.setChatId(chatId);
		        message.setText(botAnswer);
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	}
	    	
	    	/// criate message log
	    	ysServices.log(userFirstName, userLastName, Long.toString(userId), userMessageText, botAnswer);
	
	
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
