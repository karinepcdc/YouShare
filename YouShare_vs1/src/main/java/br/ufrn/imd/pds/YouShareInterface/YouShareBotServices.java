package br.ufrn.imd.pds.YouShareInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.vdurmont.emoji.EmojiParser; // to parse emojis
//check emojis at: 
//https://emojipedia.org/beaming-face-with-smiling-eyes/
//https://www.webfx.com/tools/emoji-cheat-sheet/

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;
import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.APIinterface.TelegramBotAPIFacade;
import br.ufrn.imd.pds.business.FacadeItem;
import br.ufrn.imd.pds.business.FacadeUser;
import br.ufrn.imd.pds.business.ItemServices;
import br.ufrn.imd.pds.business.UserServices;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class YouShareBotServices implements YouShareBotFacade {
	
	private static TelegramBotAPIFacade apiServices;
	private static FacadeUser userServices;
	private static FacadeItem itemServices;

	
	/* Default constructor */	
	public YouShareBotServices() {
		apiServices = new TelegramBotAPIServices();
		userServices = new UserServices();
		
		try {
			itemServices = new ItemServices();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("YouShareBotServices criado!");
	}
	
	/*********************
	 * BotCommands *
	 *********************/
	
	/* BotCommand
	 * command: Text of the command, 1-32 characters. 
	 * 			Can contain only lowercase English letters, digits and underscores.
	 * description: Description of the command, 3-256 characters.
	 */
	public static void start ( MessageData message ){
		String botAnswer = ""; // Bot reply
		
		if( userServices.isRegistered( message.getTelegramUserName() ) ) {	// user already regitered
			
			// set bot repply
	        botAnswer = "Welcome back " + message.getUserFirstName() + EmojiParser.parseToUnicode("! :grin:\n\n")
	        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
	        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
	        			+ "Type /help to see the main menu.\n\n"
	        			+ "Or, if you want to leave our community, type /unregister.";
	        
			
		} else { // new user
			
    		// set bot repply
	        botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:\n\n")
	        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
	        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
	        			+ EmojiParser.parseToUnicode("Register into our community to start sharing! :wink:\n\n")
	        			+ "/register - subscribe in YouShare system";
	        
        }
        
        // request APIInterface to send text message to user
        apiServices.sendTextMsg( message.getChatId(), botAnswer );


	}
	
	public static void help ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		if( userServices.isRegistered( message.getTelegramUserName() ) ) {	// user already regitered

			// define bot answer
			botAnswer = "Select the desired action:\n\n"
	        		+ "/search - Search an item of interest.\n"
	        		+ "/myshare - check your ads.\n"
	        		+ "/myreservations - check your reservations history and reviews.\n\n"
	        		+ "/profile - view and edit your user profile.\n"
	        		+ "/unregister - unsubscribe YouShare system.";
        
		} else { // new user
			
			// define bot answer
			botAnswer = "Select the desired action:\n\n"
					+ "/start - welcome menu.\n"
        			+ "/register - subscribe in YouShare system.";
		}
		
		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	}
	
	public static void register ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		if( userServices.isRegistered( message.getTelegramUserName() ) ) {	// user already regitered
    		
			// define bot answer
			botAnswer = message.getUserFirstName() + ", you are already registered in our system!"
					+ "Type /help to see the main menu.\n"
					+ "Or, if you want to leave our community, type /unregister.";
		
    	} else { // new user

			// cadastrar novo usuário
			userServices.createUser( message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName() );
			// TODO tratamento de excessão ???
			
			// define bot answer
			botAnswer = message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "you've been successfully registered into our system!\n\n"
    				+ "Type /help to see the main menu.\n"
    				+ "If you changed your mind, type /unregister. ";
    			    
    	}

		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    
	}
	
	
	public static void unregister ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		if( userServices.isRegistered( message.getTelegramUserName() ) ) {	// user already regitered
			
    		// define bot answer
			botAnswer = message.getUserFirstName() + ", are you sure you want to unregister?\n"
					+ "All your data, items and reservations will be erased from our system!\n\n"
					+ EmojiParser.parseToUnicode(":warning: Operation cannot be undone!");
			
			// send msg with inline keyboard
			String[] buttonsLabels = {"Yes", "No"};
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "unregister", buttonsLabels , 2, 1);	    	    
			
    	} else { // new user
    		
    		// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}


	}
	
	public static void profile ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		if( userServices.isRegistered( message.getTelegramUserName() ) ) {	// user already regitered
			
    		// define bot answer
    		botAnswer = "This is how YouShare users see you: \n\n"
    				 + userServices.readUser( message.getTelegramUserName() );
    		
    		// a message with picture could be send here... do ???

    		// request APIInterface to send text message to user
    		apiServices.sendTextMsg( message.getChatId(), botAnswer );

    		botAnswer = "Do you want to change your profile?\n\n"
    				+ "/name - change name.\n";
    				//+ "/picture - change picute."; // ... do ???
    		
    		// fazer mecanismo de continuar navegando num submenu.... do ???
		
    		// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
    	} else { // new user
    		
    		// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}
		
	}
	

	public static void myshare ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		if( userServices.isRegistered( message.getTelegramUserName() ) ) {
			
			// check if the user have ads
			// if( userServices.haveAds??? ) { \\ TODO methold that check if user have any ad
			
			// define bot answer
			botAnswer = "Here are your Ads:\n";
			
			// list user ads in inline keyboard \\ TODO include inline keyboard 
			
			botAnswer += "Type the item id for more details.\n";
			botAnswer += "To include more items type /additem.\n";
			
			// } else {
			botAnswer = "You don't have any ad yet!\n";
			botAnswer += "To include an item type /additem.\n";

			try {
				itemServices.createItem("", "", "", "", "");
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// }
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}  else { // new user
    		
    		// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}
	}

	public static void myreservations ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		if( userServices.isRegistered( message.getTelegramUserName() ) ) {
			
			// check if the user have reservations
			// if( userServices.haveReservations??? ) { \\ TODO methold that check if user have any reservation
			
			// define bot answer
			botAnswer = "Here are the items you have solicited a reservation:\n";
			
			// list user ads in inline keyboard \\ TODO include inline keyboard 
			
			botAnswer += "Type the reservation id for more details.\n";
			botAnswer += "If you would like to cancel a reservation, type /cancelreservation.\n\n";
			botAnswer += "Type /search to find more items!\n";
			
			// } else {
			botAnswer = "You don't have any reservation yet!\n";
			botAnswer += "Type /search to find an items!\n";

			// }
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}  else { // new user
    		
    		// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}
	}

	public static void search ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		if( userServices.isRegistered( message.getTelegramUserName() ) ) {
			
			// define bot answer
			botAnswer = "Which item are you interested?\n";
			
			// list user ads in inline keyboard \\ TODO include inline keyboard 
			// such that the person don't need to send the message to do the search
			
			//botAnswer += "Type the item id for more details.\n"; \\ TODO como pões essa msg???
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}  else { // new user
    		
    		// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
    				+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
			// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}
	}

	
	/*********************
	 * Callback commands *
	 *********************/
	
	public static void yesUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Done! You've been successfully unsubscribed from YouShare!";
		
		// unregister user
		userServices.deleteUser( callbackMessage.getTelegramUserName() );
		
		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
	}
	
	public static void noUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Yay! You're staying!";
		
		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
	}


	@Override
	public String registerAdImage(String userFirstName, String userLastName, String telegramUserName, String imageId, String chatId ) {
		// TODO Auto-generated method stub
		
		return "todo";
	}

	
	// Print YouShareBot loggings
	@Override
	public void log(String userFirstName, String userLastName, String telegramUserName, String userTxtMsg, String botAnswer ) {
		System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + userFirstName + " " + userLastName + ". (id = " + telegramUserName + ") \n Text - " + userTxtMsg);
        System.out.println("Bot answer: \n Text - " + botAnswer);
        
	}

	
}
