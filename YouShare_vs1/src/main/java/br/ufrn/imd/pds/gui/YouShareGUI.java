package br.ufrn.imd.pds.gui;

import com.vdurmont.emoji.EmojiParser; // to parse emojis
// check emojis at: 
//   https://emojipedia.org/beaming-face-with-smiling-eyes/
//   https://www.webfx.com/tools/emoji-cheat-sheet/

import br.ufrn.imd.pds.business.YouShareBot;
import br.ufrn.imd.pds.business.YouShareServices;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/// Class that create the Youshare Bot as a Long Polling Bot. Define the interface with the user.
/**
 * Class that create YouShare Bot and defines default action when a 
 * update is received. 
 *
 */
public class YouShareGUI extends TelegramLongPollingBot {
	
	private YouShareBot ysBot;
	private YouShareServices ysServices;

	
	/* Default constructor */	
	public YouShareGUI () {		
		// instantiate Services
		ysBot = new YouShareBot();
		
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
	    		    	
	    	
	    	/// process message. Define commands
	    	if( userMessageText.equals("/start") ) {
	    		
	    		// set message mandatory fields
		        botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:\n\n")
		        			+ "I can help you to share/rent utilities that are just taking dust at your home. "
		        			+ "It's an opportunity to earn some money or just to help a neibour!\n\n"
		        			+ "Do you have a login?\n\n"
		        			+ "/login - login in YouShare.\n"
		        			+ "/register - create a login.";
		        message.setChatId(chatId);
		        message.setText(botAnswer);
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	} else if( userMessageText.equals("/help") ){
	    		
	    		// set message mandatory fields
		        botAnswer = "Select the desired action:\n\n"
		        		+ "/search - Search an item of interest.\n"
		        		+ "/myShare - check your ads.\n"
		        		+ "/myReservations - check your reservations history and reviews.\n\n"
		        		+ "/edit - edit your user profile.\n";
		        message.setChatId(chatId);
		        message.setText(botAnswer);
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	} else if( userMessageText.equals("/register") ){
	    		
	    		// set message mandatory fields
		        botAnswer = "Hello " + userFirstName + " " + userLastName + "!\n"
		        			+ "Enter a password to login into our system.";
		        // Como faz para pegar a resposta da pessoa??????
		        message.setChatId(chatId);
		        message.setText(botAnswer);
		        
		        /// send repply
		        try {
		            execute(message); // Call method to send the message
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
		        
	    	} else if( userMessageText.equals("/login") ){
	    		
	    		// set message mandatory fields
		        botAnswer = "Wealcome back " + userFirstName + " " + userLastName + "!\n"
		        			+ "Please, enter your password.";
		        // Como faz para pegar a resposta da pessoa??????
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
		return ysBot.getUSERNAME();
	}

	@Override
	public String getBotToken() {
		return ysBot.getTOKEN();
	}
	
}
	