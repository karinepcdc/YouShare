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
	public String processReceivedTextMsg( String userFirstName, String userLastName, long userId, String userTxtMsg, String chatId ) {

		TelegramBotAPIServices apiServices = new TelegramBotAPIServices();
		UserServices userServices = new UserServices();
		
    	String botAnswer = ""; // Bot repply

    	// check if text message has bot commands
		if( userTxtMsg.equals( "/start" ) ) {
    		
    		//if( userServices.isRegistered( userId ) ) {	// user already regitered

			// set bot repply
	        botAnswer = "Welcome back " + userFirstName + EmojiParser.parseToUnicode("! :grin:\n\n")
	        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
	        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
	        			+ "Type /help to see the main menu.\n\n"
	        			+ "Or, if you want to leave our community, type /unregister.";
	        
			
    		//} else { // new user
			
    		// set bot repply
	        botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:\n\n")
	        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
	        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
	        			+ EmojiParser.parseToUnicode("Register into our community to start sharing! :wink:\n\n")
	        			+ "/register - subscribe in YouShare system";
	        
	        //}
	        
	        // request APIInterface to send text message to user
	        apiServices.sendTextMsg( chatId, botAnswer );
	        
		} else if( userTxtMsg.equals("/help") ){
    		
    		
    		//if( userServices.isRegistered( userId ) ) {	// user already regitered

    			// define bot answer
    			botAnswer = "Select the desired action:\n\n"
		        		+ "/search - Search an item of interest.\n"
		        		+ "/myShare - check your ads.\n"
		        		+ "/myReservations - check your reservations history and reviews.\n\n"
		        		+ "/edit - edit your user profile.\n";
	        
    		//} else { // new user
    			
    			// define bot answer
    			botAnswer = "Select the desired action:\n\n"
    					+ "/start - welcome menu.\n"
	        			+ "/register - subscribe in YouShare system.";
    		//}
    		
    		// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( chatId, botAnswer );
	        
    	} else if( userTxtMsg.equals("/register") ){ // tratamento de excessão ???
      		
    		//if( userServices.isRegistered( userId ) ) {	// user already regitered
    		
    			// define bot answer
				botAnswer = userFirstName + ", you are already registered in our system!"
						+ "Type /help to see the main menu.\n"
						+ "Or, if you want to leave our community, type /unregister.";
    		
	    	//} else { // new user

				// cadastrar novo usuário
				userServices.createUser(userFirstName, userLastName, Long.toString(userId), "passwd");
				
    			// define bot answer
    			botAnswer = userFirstName + " " + userLastName + ", "
	    				+ "you've been successfully registered into our system!\n\n"
	    				+ "Type /help to see the main menu.\n"
	    				+ "If you changed your mind, type /unregister. ";
	    			    
        	//}

    		// request APIInterface to send text message to user
    	    apiServices.sendTextMsg( chatId, botAnswer );
    	    
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
	public String processCallBackQuery(String userFirstName, String userLastName, long userId, String callbackData, String chatId ) {		
		// TODO Auto-generated method stub
		
		return "todo";		
	}


	@Override
	public String registerAdImage(String userFirstName, String userLastName, long userId, String imageId, String chatId ) {
		// TODO Auto-generated method stub
		
		return "todo";
	}

	
	// Print YouShareBot loggings
	@Override
	public void log(String userFirstName, String userLastName, String userId, String userTxtMsg, String botAnswer ) {
		System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + userFirstName + " " + userLastName + ". (id = " + userId + ") \n Text - " + userTxtMsg);
        System.out.println("Bot answer: \n Text - " + botAnswer);
        
	}

	
}