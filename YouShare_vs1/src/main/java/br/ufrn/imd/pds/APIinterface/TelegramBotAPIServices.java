package br.ufrn.imd.pds.APIinterface;

import br.ufrn.imd.pds.YouShareInterface.YouShareBot;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotFacade;
import br.ufrn.imd.pds.YouShareInterface.YouShareBotServices;
import br.ufrn.imd.pds.commands.CommandsInvoker;
import br.ufrn.imd.pds.exceptions.UIException;
import br.ufrn.imd.pds.exceptions.DataException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.Math.toIntExact;

public class TelegramBotAPIServices extends TelegramLongPollingBot implements TelegramBotAPIFacade {
	
	private YouShareBot ysBot;
	private boolean waitingRepply = false;
	private String commandCached;
	
	private static YouShareBotFacade ysServices;
	
	private static TelegramBotAPIServices uniqueInstance;
	
	/* Constructor */
	public TelegramBotAPIServices() {		

		ysBot = new YouShareBot();
		System.out.println( "TelegramBotAPIServices criado!" );
		
	}
	
	/* Singleton constructor */
	public static synchronized TelegramBotAPIServices getInstance() {
		if( uniqueInstance == null ) {
			uniqueInstance = new TelegramBotAPIServices();
		}
		return uniqueInstance;
		
	}

	public boolean isWaitingRepply() {
		return waitingRepply;
	}

	public void setWaitingRepply(boolean waitingReply) {
		this.waitingRepply = waitingReply;
	}

	public String getCommandCached() {
		return commandCached;
	}

	public void setCommandCached(String commandCached) {
		this.commandCached = commandCached;
	}

	/// Class that checks for updates from the user (coming from Telegram servers), 
	/// like user text messages, callback queries, etc
	@Override
	public void onUpdateReceived( Update update ) {		
		
		try {
			ysServices = new YouShareBotServices();
		} catch ( DataException e1 ) {
			e1.printStackTrace();
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
	    	
	    	// set message data
	    	MessageData message = new MessageData();
	    	message.setTxtMessage( userMessageText );
	    	message.setChatId( chatId );
	    	message.setUserFirstName( userFirstName );
	    	message.setUserLastName( userLastName );
	    	message.setTelegramUserName( userUserName );
	    	message.setCallback( false );
	    	message.setHasParameter( false );
			
	    	// process command
			try {
				
				if( !isWaitingRepply() ) {

			    	// check if command have a parameter
			    	String REGEX = "_";
			    	Pattern commandPattern = Pattern.compile( REGEX );
			    	String[] parameters = commandPattern.split( userMessageText );
			    	
			    	String command = parameters[0];
			    	if( parameters.length > 1 ) {
			    		message.setHasParameter( true );
			    		message.setParameter( parameters[1] );
			    	}
			    	
					System.out.println("\nExecutando comando: " + command );

					CommandsInvoker.executeCommand( command, message );
					
				} else {
					System.out.println("\nExecutando comando: " + getCommandCached() );
					CommandsInvoker.executeCommand( getCommandCached(), message );
					setWaitingRepply(false);
				}
				
			} catch ( UIException e ) {
				sendTextMsg(chatId, "Unknown command... Check what I can do typing /help.");
			}
	
	    } else if( update.hasCallbackQuery() ) {
	    	
	    	// set message variables
            String callData = update.getCallbackQuery().getData();
	    	String userUserName = update.getCallbackQuery().getFrom().getUserName(); // used as key in YouShare systems
            long messageId = update.getCallbackQuery().getMessage().getMessageId(); // to edit callback message and remove buttons
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
	    	String messageText = update.getCallbackQuery().getMessage().getText(); // get text message with callback buttons

            // set message data
	    	MessageData message = new MessageData();
	    	message.setChatId(chatId);
	    	message.setTelegramUserName(userUserName);
	    	message.setMessageId(messageId);
	    	message.setTxtMessage(messageText); // for retrieving parameters
	    	message.setCallback(true);
	    	message.setCallbackData(callData);
	    	
	    	
            // process callback query
			try {
				CommandsInvoker.executeCommand( callData, message );
			} catch ( UIException e ) {
				// TODO será que deveria ser separado aqui?
				sendTextMsg( chatId, "Problem Processing your choice. Contact support." );
			}
	    	
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
		
        SendMessage message = new SendMessage(); 
		
		// set message's mandatory fields of the bot's reply
        message.setChatId( chatId );
        message.setText( botTxtMsg );
        // TODO message.setParseMode("MarkdownV2");
        
        // send message
        try {
            execute(message); // Call method to send the message
        } catch ( TelegramApiException e ) {
            e.printStackTrace();
        }
        		
	}

	@Override
	public void sendInlineKeyboardWithInlineButtons( String chatId, String botTxtMsg, String[] buttonsLabels ) {
		// set Bot reply variables
        SendMessage message = new SendMessage(); 
		
		// set message's mandatory fields of the bot's reply
        message.setChatId(chatId);
        message.setText(botTxtMsg);        
        
        // send message
        try {
            execute( message ); // Call method to send the message
        } catch ( TelegramApiException e ) {
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
        message.setChatId( chatId );
        message.setText( botTxtMsg );
        
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
		
	}


	@Override
	public void editTextMsg( String chatId, long messageId, String editedBotTxtMsg ) {        
		// set Bot reply variables
		EditMessageText message = new EditMessageText();
				
		// set message's mandatory fields of the bot's reply
		message.setChatId(chatId);
		message.setMessageId(toIntExact(messageId));
		message.setText(editedBotTxtMsg);
        
		try {
            execute(message); 
        } catch ( TelegramApiException e ) {
            e.printStackTrace();
        }
        
	}

	@Override
	public void requestUserRepply(String nextCommand) {
		
		this.setWaitingRepply(true);
		this.setCommandCached(nextCommand);
		
		System.out.println("\n\n *** Solicitando resposta do usuario ***\n"
				+ "waiting: " + isWaitingRepply() + " comando: " + getCommandCached());
	}
	
}
	