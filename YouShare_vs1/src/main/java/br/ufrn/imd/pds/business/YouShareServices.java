package br.ufrn.imd.pds.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YouShareServices implements FacedeBot {

	
	/// Print YouShareBot loggings
	@Override
	public void log(String userFirstName, String userLastName, String userId, String userMsgTxt, String botAnswer ) {
		System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + userFirstName + " " + userLastName + ". (id = " + userId + ") \n Text - " + userMsgTxt);
        System.out.println("Bot answer: \n Text - " + botAnswer);
        
	}

	@Override
	public void processRegister(String userFirstName, String userLastName, String userId, String passwd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processLogin(String userFirstName, String userLastName, String userId, String passwd) {
		// TODO Auto-generated method stub
		
	}
}
