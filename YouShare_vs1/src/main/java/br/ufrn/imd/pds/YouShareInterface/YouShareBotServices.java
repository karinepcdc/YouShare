package br.ufrn.imd.pds.YouShareInterface;

// parse emojis
import com.vdurmont.emoji.EmojiParser; 
/* 
** check emojis at: 
** https://emojipedia.org/beaming-face-with-smiling-eyes/
** https://www.webfx.com/tools/emoji-cheat-sheet/
*/

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;
import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.APIinterface.TelegramBotAPIFacade;
import br.ufrn.imd.pds.business.FacadeItem;
import br.ufrn.imd.pds.business.FacadeUser;
import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.ItemServices;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.business.UserServices;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;

public class YouShareBotServices implements YouShareBotFacade {
	
	private static TelegramBotAPIFacade apiServices;
	private static FacadeUser userServices;
	private static FacadeItem itemServices;

	public YouShareBotServices() throws DataException {
		apiServices = new TelegramBotAPIServices();
		userServices = new UserServices();
		itemServices = new ItemServices();
		
		System.out.println( "YouShareBotServices criado!" );
	}
	
	/* BotCommand
	 * command: Text of the command, 1-32 characters. 
	 * 			Can contain only lowercase English letters, digits and underscores.
	 * description: Description of the command, 3-256 characters.
	 */
	public static void start ( MessageData message ) {
		// Bot reply
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );			
		if( isUserRegistered ) {	// user already registered
				
			// set bot reply
		    botAnswer = "Welcome back " + message.getUserFirstName() + EmojiParser.parseToUnicode("! :grin:\n\n")
		        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
		        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
		        			+ "Type /help to see the main menu.\n\n"
		        			+ "Or, if you want to leave our community, type /unregister.";
		        
				
		} else { // new user
				
	    	// set bot reply
		    botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:\n\n")
		        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
		        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
		        			+ EmojiParser.parseToUnicode("Register into our community to start sharing! :wink:\n\n")
		        			+ "/register - subscribe in YouShare system";
		        
	    }
	        
	    // request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
	    // YouShare bot logins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	}
	
	public static void help ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {	// user already registered
	
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
		
		// YouShare bot logins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	    
	}
	
	public static void register ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {	// user already registered    		
		// define bot answer
		botAnswer = message.getUserFirstName() + ", you are already registered in our system!"
					+ "Type /help to see the main menu.\n"
					+ "Or, if you want to leave our community, type /unregister.";
			
	    } else { // new user
	
			// cadastrar novo usuário
	    	User newUser = new User( message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), "0", "0", "No reviews yet!" );
			try {
				userServices.createUser( newUser );
			} catch ( BusinessException e ) {
				e.printStackTrace();
			} catch ( DataException e ) {
				e.printStackTrace();
			}
				
			// define bot answer
			botAnswer = message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "you've been successfully registered into our system!\n\n"
	    				+ "Type /help to see the main menu.\n"
	    				+ "If you changed your mind, type /unregister. ";
	    			    
	    }
	
		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );

		// YouShare bot loggins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
		    
		
	}
	
	
	public static void unregister ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {  		
				
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

		// YouShare bot logins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

	}
	
	public static void profile ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {	// user already registered    		
				
	    	try {
				botAnswer = "This is how YouShare users see you: \n\n"
							 + userServices.readUser( message.getTelegramUserName() );
			} catch ( BusinessException e ) {
				e.printStackTrace();
			} catch ( DataException e ) {
				e.printStackTrace();
			}
	    		
	   		// TODO: a message with picture could be send here... do ???
	
	   		// request APIInterface to send text message to user
	   		apiServices.sendTextMsg( message.getChatId(), botAnswer );
	
	   		botAnswer = "Do you want to change your profile?\n\n"
	    				+ "/name - change name.\n";
	    				//+ "/picture - change picture."; // ... do ???
	    		
	    	// TODO: fazer mecanismo de continuar navegando num submenu.... do ???
			
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
			
		// YouShare bot loggins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

			
	}
	

	public static void myshare ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {	// user already registered
			/*
			Tool newTool3 = new Tool("Drill", "Drill that do what's expected", "", message.getTelegramUserName(), 0, 0, "", "14", "none", "220");
			Tool newTool4 = new Tool("Vacuum cleaner", "dam good vacuum cleaner", "", message.getTelegramUserName(), 0, 0, "", "12.4", "dont spoil", "220");
			Tool newTool5 = new Tool("Electric sander", "Good electric sander", "", message.getTelegramUserName(), 0, 0, "", "12.6", "take care", "110");

			try {
				newTool3.setCode(itemServices.createItem( newTool3 ) );
				newTool4.setCode(itemServices.createItem( newTool4 ));			
				newTool5.setCode(itemServices.createItem( newTool5 ));
			} catch (BusinessException | DataException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			
			// check if the user have ads
			List<Item> userAds = new ArrayList<Item>();
			try {
				userAds = itemServices.readAll( message.getTelegramUserName() );
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if( !userAds.isEmpty() ) { 
				
			// define bot answer
			botAnswer = "Here are your Ads:\n\n";
			
				// list user ads in inline keyboard \\ TODO include inline keyboard 
				for( Item it: userAds ) {
					botAnswer += it.getName() + " (id: " + it.getCode() + ")\n";
				}
				
				botAnswer += "\nType '/itemdetails_' + 'id' for more details.\n";
				botAnswer += "To include more items type /additem.\n";
				
			 } else {
				 botAnswer = "You don't have any ad yet!\n";
				 botAnswer += "To include an item type /additem.\n";
				
			}
				
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
			
	    // YouShare bot loggins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

	}

	public static void itemDetails ( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			if( message.hasParameter() ) {
				try {
					itemServices.validateId(message.getParameter(), message.getTelegramUserName());
				} catch (BusinessException e) {
					
					// define bot answer			
					botAnswer = "Id " +  message.getParameter() + "is not valid:\n"
							+ e.getMessage()
							+ "\nThe command /itemdetail require a item id as parameter.\n"
							+ "Type /itemdetail_id, replacing id by the id number of the item you want to see."
							+ "For instance, /itemdetail_123.\n\n"
							+ "To check you items id type /myshare.";
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				try {
					Item item = itemServices.readItem(message.getParameter());
					
					// display item ad
					botAnswer = "Item id #" + message.getParameter() + " Ad:\n\n"
							+ item.getName() + "\n"
							+ "Status: " + (item.isAvailable() ? "available" : "not available") + "\n"
							+ "Grade: " + item.getItemGrade() + "\n"
							+ "Most recent review: " + item.getLastReview() + "\n"
							+ "Price: $" + item.getPrice() + "\n\n";
					
					// item specifics
					if( item instanceof Tool ) {
						botAnswer += "Terms of use: " + ((Tool) item).getTermsOfUse() + "\n"
								+ "Voltage: " + ((Tool) item).getVoltage();
					}
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		} else { // new user
	    		
	    	// define bot answer
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		}
		
	}

	public static void myreservations ( MessageData message ) {

		String botAnswer = ""; 
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {    		
			
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
			
	    // YouShare bot loggins
        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	}

	public static void search ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {	// user already regitered    		
			
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

		// YouShare bot loggins
		YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

	}

	
	/*********************
	 * Callback commands 
	 * @throws UserNotRegisteredException *
	 *********************/
	
	public static void yesUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Done! You've been successfully unsubscribed from YouShare!";
		
		// unregister user
		try {
			userServices.deleteUser( callbackMessage.getTelegramUserName() );
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
		
		// YouShare bot callback loggins
        YouShareBotFacade.logCallback( callbackMessage.getTelegramUserName(), callbackMessage.getChatId(), callbackMessage.getMessageId(), callbackMessage.getCallbackData(), botAnswer);
	}
	

	public static void noUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Yay! You're staying!";

		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
		
		// YouShare bot callback loggins
        YouShareBotFacade.logCallback( callbackMessage.getTelegramUserName(), callbackMessage.getChatId(), callbackMessage.getMessageId(), callbackMessage.getCallbackData(), botAnswer);
	}


	@Override
	public String registerAdImage(String userFirstName, String userLastName, String telegramUserName, String imageId, String chatId ) {
		// TODO Auto-generated method stub
		
		return "todo";
	}
	
	/// Utils
	private static void printTool(Tool tool) {
		System.out.println("\n**************************\n");
		System.out.println("Reading Tool " + tool.getCode() + "\n"
				+ "Name: " + tool.getName() + "\n"
				+ "Description: " + tool.getDescription() + "\n"
				+ "Owner: " + tool.getOwner() + "\n"
				+ "itemGrade: " + tool.getItemGrade() + "\n"
				+ "itemGradeCount: " + tool.getItemGradeCount() + "\n"
				+ "lastReview: " + tool.getLastReview() + "\n"
				+ "isAvailable:" + tool.isAvailable() + "\n"
				+ "price: " + tool.getPrice() + "\n"
				+ "TOU: " + tool.getTermsOfUse() + "\n"
				+ "Voltage: " + tool.getVoltage() + "\n" );

		System.out.println("\n**************************\n");

	}
	
}
