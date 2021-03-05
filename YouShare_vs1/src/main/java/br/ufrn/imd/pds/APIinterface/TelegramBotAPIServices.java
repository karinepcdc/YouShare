package br.ufrn.imd.pds.APIinterface;

import com.vdurmont.emoji.EmojiParser; // to parse emojis
// check emojis at: 
//   https://emojipedia.org/beaming-face-with-smiling-eyes/
//   https://www.webfx.com/tools/emoji-cheat-sheet/

import br.ufrn.imd.pds.YouShareInterface.YouShareBot;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;
import br.ufrn.imd.pds.business.UserServices;

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
public class TelegramBotAPIServices extends TelegramLongPollingBot implements TelegramBotAPIFacade {
	
	private YouShareBot ysBot;

	private static TelegramBotAPIServices uniqueInstance;
	
	// deletar depois .... 		
	private int expectedRepplyRegister; // number of answers the bots requires from the user when he uses the command /register
	
	
	/* Default constructor */	
	public TelegramBotAPIServices() {		

		// instantiate YouShare Bot
		ysBot = new YouShareBot();

		System.out.println("TelegramBotAPIServices criado!");
		
	}
	
	/// precisa do padrão singleton ???
	/* Singleton constructor */
	/*
	public static synchronized TelegramBotAPIServices getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new TelegramBotAPIServices();
		}
		
		return uniqueInstance;
	}
	*/

	/// Class that check for updates from the user (coming from Telegram servers), like user text messages, callback queries, images, etc
	@Override
	public void onUpdateReceived(Update update) {
		
		YouShareBotServices ysServices = new YouShareBotServices();
		
		// Check if the update has a message and the message has text
	    if ( update.hasMessage() && update.getMessage().hasText() ) {
	    	// set message variables
	    	String userMessageText = update.getMessage().getText();
	    	String chatId = update.getMessage().getChatId().toString();
	    	
	    	// set user variables
	    	String userFirstName =  update.getMessage().getChat().getFirstName();
	    	String userLastName = update.getMessage().getChat().getLastName();
	    	String userUserName = update.getMessage().getChat().getUserName(); // eh util ???
	    	// long userId = update.getMessage().getChat().getId();
	    	
	    	
	    	// register Bot reply, for log purposes
	    	String botAnswer = "";
	    		    	
	    	/// process message. Define commands
	    	botAnswer = ysServices.processReceivedTextMsg( userFirstName, userLastName, userUserName, userMessageText, chatId );
	    	
	    	// create messages log
	    	ysServices.log(userFirstName, userLastName, userUserName, userMessageText, botAnswer);
	
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


	@Override
	public void sendTextMsg( String chatId, String botTxtMsg ) {
		
		
		// set Bot reply variables
        SendMessage message = new SendMessage(); 
		
		// set message's mandatory fields of the bot's repply
        message.setChatId(chatId);
        message.setText(botTxtMsg);
        
        // send message
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        		
	}


	@Override
	public void sendTextMsgWithButtons( String chatId, String botTxtMsg, String[] buttonsLabels ) {
		// TODO Auto-generated method stub    	
		
	}


	@Override
	public void sendImage( String chatId, String caption, String fileId ) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void editTextMsg( String chatId, long messageId, String botTxtMsg ) {
		// TODO Auto-generated method stub
		
	}
	
}
	