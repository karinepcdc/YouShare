package br.ufrn.imd.pds.YouShareInterface;

import br.ufrn.imd.pds.APIinterface.MessageData;

public interface YouShareBotFacade {
	
	/// Interface of interaction with Telegram Java library (TelegramBotAPIServices).
	
	/*********************
	 * BotCommands *
	 *********************/
	
	/// Process text message checking for bot commands and request to APIInterface to send an appropriate reply. 
	/*
	 * Bot command must be 1-32 characters. Can contain only lowercase English letters, digits and underscores.
	 * Description of the command must have 3-256 characters.
	 * 
	 * @param userFirstName User first name.
	 * @param userLastName User last name.
	 * @param userId User Id.
	 * @param userTxtMsg User message text to be process by YouShare bot.
	 * @param chatId chat Id where the reply will be send.
	 * 
	 * @return Bot answer for log purposes.
	 */
	///public String processReceivedTextMsg( String userFirstName, String userLastName, String telegramUserName, String userTxtMsg, String chatId );

	
	
	
	/// Process callback queries (when an user press a button) and request to APIInterface to send an appropriate reply. Return bot answer/action for log purposes.
	///public String processCallBackQuery( String telegramUserName, String callbackData, long messageId, String chatId );
	
	/*********************
	 * Callback commands *
	 *********************/
	
	//public void yesUnregister ( MessageData callbackMessage );
	
	
	
	/// Request registration of the id of an image of an item advertisement from the user in the database. Return bot answer/action for log purposes.  - double check arguments???
	public String registerAdImage( String userFirstName, String userLastName, String telegramUserName, String fileId, String chatId );
	
	/// Print a log of messages exchanged with users.
	public void log( String userFirstName, String userLastName, String telegramUserName, String userTxtMsg, String botAnswer );

}
