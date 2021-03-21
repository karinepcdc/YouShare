package br.ufrn.imd.pds.APIinterface;


public interface TelegramBotAPIFacade {
	
	/// Send a text message reply to an user
	public void sendTextMsg( String chatId, String botTxtMsg );

	/// Send a text message reply with buttons to an user - double check arguments ??? 
	public void sendInlineKeyboardWithInlineButtons( String chatId, String botTxtMsg, String[] buttonsLabels );
	
	/// Send a text message reply with buttons to an user - double check arguments ??? 
	public void sendInlineKeyboardWithCallbackButtons( String chatId, String botTxtMsg, String callbackLabel, String[] buttonsLabels, int columns, int lines );

	/// Send an image (from an item advertisement) with caption to an user - double check arguments ??? 
	public void sendImage( String chatId, String caption, String fileId );

	/// Edit a sent text message - double check arguments ??? 
	public void editTextMsg( String chatId, long messageId, String botTxtMsg ); 

	/// Tell API that a command is asking for an text repply from an user. 
	/*
	 * @param nextCommand the command that will process user repply.
	 */
	public void requestUserRepply( String nextCommand);
}
