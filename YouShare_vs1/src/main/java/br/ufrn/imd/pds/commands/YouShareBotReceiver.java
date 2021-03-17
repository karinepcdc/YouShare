package br.ufrn.imd.pds.commands;

import com.vdurmont.emoji.EmojiParser;

public class YouShareBotReceiver {
	
	public static void start (){
		String botAnswer = ""; // Bot reply
		
		botAnswer = "Welcome back " + userFirstName + EmojiParser.parseToUnicode("! :grin:\n\n")
		+ "I can help you to share/rent utilities that are just taking dust in your home. "
		+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
		+ "Type /help to see the main menu.\n\n"
		+ "Or, if you want to leave our community, type /unregister.";
		
		// request APIInterface to send text message to user
        apiServices.sendTextMsg( chatId, botAnswer );
	}
	
	
	public static void register () {
		if( userServices.isRegistered( telegramUserName ) ) {	// user already regitered
    		
			// define bot answer
			botAnswer = userFirstName + ", you are already registered in our system!"
					+ "Type /help to see the main menu.\n"
					+ "Or, if you want to leave our community, type /unregister.";
		
    	} else { // new user

			// cadastrar novo usuário
			userServices.createUser( userFirstName, userLastName, telegramUserName );
			// TODO tratamento de excessão ???
			
			// define bot answer
			botAnswer = userFirstName + " " + userLastName + ", "
    				+ "you've been successfully registered into our system!\n\n"
    				+ "Type /help to see the main menu.\n"
    				+ "If you changed your mind, type /unregister. ";
    			    
    	}

		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( chatId, botAnswer );
	}
}
