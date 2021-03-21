package br.ufrn.imd.pds.YouShareInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface YouShareBotFacade {
	
	/// Interface of interaction with Telegram Java library (TelegramBotAPIServices)

	public String registerAdImage( String userFirstName, String userLastName, String telegramUserName, String fileId, String chatId );
	
	/// Print YouShareBot logins, a log of messages exchanged with users.
	public static void log( String userFirstName, String userLastName, String telegramUserName, String userTxtMsg, String botAnswer ) {
		System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println( "Message from " + userFirstName + " " + userLastName + ". (id = " + telegramUserName + ") \n Text - " + userTxtMsg );
        System.out.println( "Bot answer: \n Text - " + botAnswer );
        
	}

	/// Print YouShareBot Callback logins, a log of button replies of users.
	public static void logCallback( String telegramUserName, String chatId, long messageId, String callbackData, String botAnswer ) {
		System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = new Date();
        System.out.println( dateFormat.format(date) );
        System.out.println( "Message from chat id" + chatId + ". (id = " + telegramUserName + ") \n callback - " + callbackData );
        System.out.println( "Bot answer (message id: " + messageId + "): \n Text - " + botAnswer );
	}
	
}
