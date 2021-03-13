package br.ufrn.imd.pds.YouShareInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.vdurmont.emoji.EmojiParser; // to parse emojis

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;
import br.ufrn.imd.pds.business.UserServices;

public class YouShareBotServices implements YouShareBotFacade {
	
	/* Default constructor */	
	public YouShareBotServices() {
		
		System.out.println("YouShareBotServices criado!");
	}
	
	
	@Override
	public String processReceivedTextMsg( String userFirstName, String userLastName, String telegramUserName, String userTxtMsg, String chatId ) {

		TelegramBotAPIServices apiServices = new TelegramBotAPIServices();
		UserServices userServices = new UserServices();
		
    	String botAnswer = ""; // Bot repply

    	// check if text message has bot commands
    	/* BotCommand
    	 * command: Text of the command, 1-32 characters. 
    	 * 			Can contain only lowercase English letters, digits and underscores.
    	 * description: Description of the command, 3-256 characters.
    	 */
		if( userTxtMsg.equals( "/start" ) ) {
    		
    		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered
	
				// set bot repply
		        botAnswer = "Welcome back " + userFirstName + EmojiParser.parseToUnicode("! :grin:\n\n")
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
	        apiServices.sendTextMsg( chatId, botAnswer );
	        
		} else if( userTxtMsg.equals("/help") ){
    		
    		
    		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered

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
    	    apiServices.sendTextMsg( chatId, botAnswer );
	        
    	} else if( userTxtMsg.equals("/register") ){ 
      		
    		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered
    		
    			// define bot answer
				botAnswer = userFirstName + ", you are already registered in our system!"
						+ "Type /help to see the main menu.\n"
						+ "Or, if you want to leave our community, type /unregister.";
    		
	    	} else { // new user

				// cadastrar novo usuário
				userServices.createUser(userFirstName, userLastName, telegramUserName);
				// TODO tratamento de excessão ???
				
    			// define bot answer
    			botAnswer = userFirstName + " " + userLastName + ", "
	    				+ "you've been successfully registered into our system!\n\n"
	    				+ "Type /help to see the main menu.\n"
	    				+ "If you changed your mind, type /unregister. ";
	    			    
        	}

    		// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( chatId, botAnswer );
    	    
    	} else if( userTxtMsg.equals("/unregister") ){
    		
    		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered
	
	    		// define bot answer
				botAnswer = userFirstName + ", are you sure you want to unregister?\n"
						+ "All your data, items and reservations will be erased from our system!\n\n"
						+ EmojiParser.parseToUnicode(":warning: Operation cannot be undone!");
				
				// send msg with inline keyboard
				String[] buttonsLabels = {"Yes", "No"};
				apiServices.sendInlineKeyboardWithCallbackButtons(chatId, botAnswer, "unregisterConfirmation", buttonsLabels , 2, 1);	    	    
				
	    	} else { // new user
	    		
	    		// define bot answer
				botAnswer = "Hello " + userFirstName + " " + userLastName + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}

    		
    	} else if( userTxtMsg.equals("/profile") ){
    		
    		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered
	
	    		// define bot answer
	    		botAnswer = "This is how YouShare users see you: \n\n"
	    				 + userServices.readUser( telegramUserName );
	    		
	    		// a message with picture could be send here... do ???

	    		// request APIInterface to send text message to user
	    		apiServices.sendTextMsg( chatId, botAnswer );

	    		botAnswer = "Do you want to change your profile?\n\n"
	    				+ "/name - change name.\n";
	    				//+ "/picture - change picute."; // ... do ???
	    		
	    		// fazer mecanismo de continuar navegando num submenu.... do ???
    		
	    		// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
	    	} else { // new user
	    		
	    		// define bot answer
				botAnswer = "Hello " + userFirstName + " " + userLastName + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}
    	    
    	} else if( userTxtMsg.equals("/myshare")) {
    		
    		if( userServices.isRegistered( telegramUserName ) ) {
    			
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

				// }
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}  else { // new user
	    		
	    		// define bot answer
				botAnswer = "Hello " + userFirstName + " " + userLastName + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}
    		
    	} else if( userTxtMsg.equals("/myreservations")) {
    		
    		if( userServices.isRegistered( telegramUserName ) ) {
    			
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
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}  else { // new user
	    		
	    		// define bot answer
				botAnswer = "Hello " + userFirstName + " " + userLastName + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}

    	} else if( userTxtMsg.equals("/search")) {
    		
    		if( userServices.isRegistered( telegramUserName ) ) {
    			
    			// define bot answer
				botAnswer = "Which item are you interested?\n";
				
				// list user ads in inline keyboard \\ TODO include inline keyboard 
				// such that the person don't need to send the message to do the search
				
				//botAnswer += "Type the item id for more details.\n"; \\ TODO como pões essa msg???
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}  else { // new user
	    		
	    		// define bot answer
				botAnswer = "Hello " + userFirstName + " " + userLastName + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
				// request APIInterface to send text message to user
	    	    apiServices.sendTextMsg( chatId, botAnswer );
	    	    
    		}

    	} else { // unknow command
			
			// set message mandatory fields
	        botAnswer = "Unknow command...Check what I can do typing /help.";
	        
	        // request APIInterface to send text message to user
	        apiServices.sendTextMsg( chatId, botAnswer );
	        
    	}
		
    /* old... erase latter
  	  
  	  else if( userMessageText.equals("/register") || (expectedRepplyRegister > 0) ){
  		

  		switch (expectedRepplyRegister) {
  		
	    		case 0:
	    			// define bot answer
			        botAnswer = "Hello " + userFirstName + " " + userLastName + "!\n"
			        			+ "Create a password to login into our system.";
			        
			        // update: require one answer from user
			        expectedRepplyRegister = 1;
			        
			        break;
			        
	    		case 1:		    			
	    			
	    			// validate password?
	    			
	    			// register user
	    			userServices.createUser(userFirstName, userLastName, userUserName, userMessageText);
	    			
	    			// define bot answer
			        botAnswer = "Registration complete.";
			        
			        // set bot's repply mandatory fields
  		        message.setChatId(chatId);
  		        message.setText(botAnswer);
  		        
  		        /// send repply
  		        try {
  		            execute(message); // Call method to send the message
  		        } catch (TelegramApiException e) {
  		            e.printStackTrace();
  		        }
  		       
	    			// fazer o login - mudar tela? o que o bot faz aqui???
  		        
  				// define bot answer
  		        botAnswer = "Type /help to access the main menu.";
			        
			        // update: no more answers are required
			        expectedRepplyRegister = 0;
			        break;
			        
			    default:
	    			// define bot answer
			        botAnswer = "Something went wrong with the registration process.\n Contact support.";
			        
			        // update: no more answers are required
			        expectedRepplyRegister = 0;
			        break;
	    			
  		}
	        
  	}  */

		
		
		// return bot answer for log purpose
		return botAnswer;
		
	}


	@Override
	public String processCallBackQuery(String telegramUserName, String callbackData, long messageId, String chatId ) {		
		TelegramBotAPIServices apiServices = new TelegramBotAPIServices();
		UserServices userServices = new UserServices();

		// identify callback query
		if( callbackData.equals("Yes_unregisterConfirmation") ) {
			
			String botAnswer = "Done! You've been successfully unsubscribed from YouShare!";
			
			// unregister user
			userServices.deleteUser(telegramUserName);
			
			// edit callback message confirming operation and removing buttons
			apiServices.editTextMsg(chatId, messageId, botAnswer);
			
		} else if( callbackData.equals("No_unregisterConfirmation") ) {
						
			String botAnswer = "Yay! You're staying!";
						
			// edit callback message confirming operation and removing buttons
			apiServices.editTextMsg(chatId, messageId, botAnswer);
			
		} else {
			// TODO callback querry not found
			
		}
		
		return "";	// TODO	
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
