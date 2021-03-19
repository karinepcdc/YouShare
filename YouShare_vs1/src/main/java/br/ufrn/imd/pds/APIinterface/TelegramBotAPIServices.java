package br.ufrn.imd.pds.APIinterface;

import br.ufrn.imd.pds.YouShareInterface.YouShareBot;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotFacade;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;
import br.ufrn.imd.pds.commands.CommandsInvoker;

import br.ufrn.imd.pds.exceptions.CommandNotFoundExeption;
import br.ufrn.imd.pds.exceptions.UserDatabaseCreationException;



import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.Math.toIntExact;

/// Class that create the Youshare Bot as a Long Polling Bot. Define the interface with the user.
/**
 * Class that create YouShare Bot and defines default action when a 
 * update is received. 
 *
 */
public class TelegramBotAPIServices extends TelegramLongPollingBot implements TelegramBotAPIFacade {
	
	private YouShareBot ysBot;
	private static YouShareBotFacade ysServices;
		
	/* Default constructor */	
	public TelegramBotAPIServices() {		

		// instantiate YouShare Bot
		ysBot = new YouShareBot();

		System.out.println("TelegramBotAPIServices criado!");
		
	}

	/// Class that check for updates from the user (coming from Telegram servers), like user text messages, callback queries, images, etc
	@Override
	public void onUpdateReceived(Update update) {
		
		
		try {
			// TODO onde instanciar isso? não é usado mais diretamente, mas precisa ser instanciado em algum lugar pois os commands usam...
			ysServices = new YouShareBotServices();
		} catch ( UserDatabaseCreationException e ) {
			e.printStackTrace();
		}

		
		// Check if the update has a message and the message has text
	    if ( update.hasMessage() && update.getMessage().hasText() ) {
	    	// set message variables
	    	String userMessageText = update.getMessage().getText();
	    	String chatId = update.getMessage().getChatId().toString();
	    	
	    	// set user variables
	    	String userFirstName =  update.getMessage().getChat().getFirstName();
	    	String userLastName = update.getMessage().getChat().getLastName();
	    	String userUserName = update.getMessage().getChat().getUserName(); // used as key in our systems
	    	// long userId = update.getMessage().getChat().getId(); // TODO eh util? Se não, apagar...
	    	
	    	
	    	// register Bot reply, for log purposes
	    	//String botAnswer = "";
	    	
	    	// set message data
	    	MessageData message = new MessageData();
	    	message.setUserTxtMsg(userMessageText);
	    	message.setChatId(chatId);
	    	message.setUserFirstName(userFirstName);
	    	message.setUserLastName(userLastName);
	    	message.setTelegramUserName(userUserName);
	    	
	    	// process command
			try {
				CommandsInvoker.executeCommand(userMessageText, message );
			} catch (CommandNotFoundExeption e) {
				sendTextMsg(chatId, "Unknow command... Check what I can do typing /help.");
			}
	    	
	    	
	    	// create messages log
	    	//ysServices.log(userFirstName, userLastName, userUserName, userMessageText, botAnswer);
	
	    } else if( update.hasCallbackQuery() ) {
	    	
	    	// set message variables
            String callData = update.getCallbackQuery().getData();
	    	String userUserName = update.getCallbackQuery().getFrom().getUserName(); // used as key in YouShare systems
            long messageId = update.getCallbackQuery().getMessage().getMessageId(); // to edit callback message and remove buttons
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            
            // set message data
	    	MessageData message = new MessageData();
	    	message.setChatId(chatId);
	    	message.setTelegramUserName(userUserName);
	    	message.setMessageId(messageId);
            
            // process callback query
			try {
				CommandsInvoker.executeCommand(callData, message );
			} catch (CommandNotFoundExeption e) {
				// TODO será que deveria ser separado aqui?
				sendTextMsg(chatId, "Problem Processing your choice. Contact support.");
			}
	    	// TODO create messages log
	    	//ysServices.logCallback( userUserName, call_data, message_id, chat_id, botAnswer );

	    	
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
        //message.setParseMode("MarkdownV2");
        
        // send message
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        		
	}


	@Override
	public void sendInlineKeyboardWithInlineButtons( String chatId, String botTxtMsg, String[] buttonsLabels ) {
		// set Bot reply variables
        SendMessage message = new SendMessage(); 
		
		// set message's mandatory fields of the bot's repply
        message.setChatId(chatId);
        message.setText(botTxtMsg);
        
        // set custom keyboard
        
        
        // send message
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }		
	}

	@Override
	public void sendInlineKeyboardWithCallbackButtons( String chatId, String botTxtMsg, String callbackLabel, String[] buttonsLabels, int columns, int lines ) {
		
		// TODO colocar dentro de um log de erros...
		// check number of labels
		if( buttonsLabels.length != (columns * lines) ) {
			String errorMsg = "\n\n *** Internal Error ***\n";
			errorMsg += "Buttons labels and button's grade differ:\n";
			errorMsg += "# columns: " + columns + "\n# lines: " + lines;
			errorMsg += "\nlabels: ";
			
	        for ( int i = 0; i < buttonsLabels.length ; i++ ) {
	        	errorMsg += buttonsLabels[i] + " ";
	        }

	        errorMsg += "\n**********\n";
	        		
			System.out.println(errorMsg);
			return;
		}
		
		// set Bot reply variables
        SendMessage message = new SendMessage(); 
		
		// set message's mandatory fields of the bot's repply
        message.setChatId(chatId);
        message.setText(botTxtMsg);
        
        // set Inline keyboard
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(); // inline keyboard that appears right next to the message it belongs to
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // list of rows of buttons
        
        // set buttons
        for ( int line = 0; line < lines; line++ ) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>(); // row of buttons

	        for( int col = 0; col < columns; col++ ) {
	        	InlineKeyboardButton button = new InlineKeyboardButton();
		        button.setText( buttonsLabels[line*columns + col] );
		        button.setCallbackData( buttonsLabels[line*columns + col] + "_" + callbackLabel);
		        
		        
		        rowInline.add(button); // add button to the the row
	        }
	        
	        rowsInline.add(rowInline); // add row of buttons to the list of rows
        }
        
        markupInline.setKeyboard(rowsInline);  // Set the keyboard to the markup
        message.setReplyMarkup(markupInline);  // Add it to the message
        
        // send message
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }	
	}

	
	@Override
	public void sendImage( String chatId, String caption, String fileId ) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void editTextMsg( String chatId, long messageId, String editedBotTxtMsg ) {        
		// set Bot reply variables
		EditMessageText message = new EditMessageText();
				
		// set message's mandatory fields of the bot's repply
		message.setChatId(chatId);
		message.setMessageId(toIntExact(messageId));
		message.setText(editedBotTxtMsg);
        
		try {
            execute(message); 
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        
	}
	
}
	