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
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );			
		if( isUserRegistered ) {
				
			// set bot reply
		    botAnswer = "Welcome back " + message.getUserFirstName() + EmojiParser.parseToUnicode("! :grin:\n\n")
		        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
		        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
		        			+ "Type /help to see the main menu.\n\n"
		        			+ "Or, if you want to leave our community, type /unregister.";
		        
				
		} else { // if it's a new user
				
		    botAnswer = EmojiParser.parseToUnicode("Welcome to the YouShare community! :grin:\n\n")
		        			+ "I can help you to share/rent utilities that are just taking dust in your home. "
		        			+ "It's an opportunity to earn some money or just to help a neighbour!\n\n"
		        			+ EmojiParser.parseToUnicode("Register into our community to start sharing! :wink:\n\n")
		        			+ "/register - subscribe in YouShare system";
		        
	    }
	        
	    // request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
	    // YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	}
	
	public static void help ( MessageData message ) {
		String botAnswer = ""; 
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
	
			botAnswer = "Select the desired action:\n\n"
		        		+ "/search - Search an item of interest.\n"
		        		+ "/myshare - check your ads.\n"
		        		+ "/myreservations - check your reservations history and reviews.\n\n"
		        		+ "/profile - view and edit your user profile.\n"
		        		+ "/unregister - unsubscribe YouShare system.";
	        
		} else { // if it's a new user
				
			botAnswer = "Select the desired action:\n\n"
						+ "/start - welcome menu.\n"
	        			+ "/register - subscribe in YouShare system.";
		}
			
		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
		
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer );
	    
	}
	
	public static void register ( MessageData message ) {
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {  		

		botAnswer = message.getUserFirstName() + ", you are already registered in our system!"
					+ "Type /help to see the main menu.\n"
					+ "Or, if you want to leave our community, type /unregister.";
			
	    } else { // if it's a new user
	
	    	User newUser = new User( message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), "0", "0", "No reviews yet!" );
			try {
				userServices.createUser( newUser );
			} catch ( BusinessException e ) {
				e.printStackTrace();
			} catch ( DataException e ) {
				e.printStackTrace();
			}
				
			botAnswer = message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "you've been successfully registered into our system!\n\n"
	    				+ "Type /help to see the main menu.\n"
	    				+ "If you changed your mind, type /unregister. ";
	    			    
	    }
	
		// request APIInterface to send text message to user
	    apiServices.sendTextMsg( message.getChatId(), botAnswer );

		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer );
		    
	}
	
	public static void unregister ( MessageData message ) {
		String botAnswer = "";

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		
		if( isUserRegistered ) {  		
				
			botAnswer = message.getUserFirstName() + ", are you sure you want to unregister?\n"
						+ "All your data, items and reservations will be erased from our system!\n\n"
						+ EmojiParser.parseToUnicode(":warning: Operation cannot be undone!");
				
			// send msg with inline keyboard
			String[] buttonsLabels = {"Yes", "No"};
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "unregister", buttonsLabels , 2, 1);	    	    
				
    	} else { // if it's a new user

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
		String botAnswer = "";

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		
		if( isUserRegistered ) { 		
				
	    	try {
				botAnswer = "This is how YouShare users see you: \n\n"
							 + userServices.readUser( message.getTelegramUserName() );
			} catch ( BusinessException e ) {
				e.printStackTrace();
			} catch ( DataException e ) {
				e.printStackTrace();
			}
	
	   		// request APIInterface to send text message to user
	   		apiServices.sendTextMsg( message.getChatId(), botAnswer );
	
	   		botAnswer = "Do you want to change your profile?\n\n"
	    				+ "/name - change name.\n";
			
	   		// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
	   	} else { // if it's a new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}
			
		// YouShare bot logins
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

			
	}
	
	public static void myshare ( MessageData message ) {
		String botAnswer = "";		
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			
			// check if the user have ads
			List<Item> userAds = new ArrayList<Item>();
			try {
				userAds = itemServices.readAll( message.getTelegramUserName() );

			} catch (BusinessException e) {
				// define bot answer			
				botAnswer = "Problem trying to read items:\n" + e.getMessage();
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );

			}
			if( !userAds.isEmpty() ) { 
				
				botAnswer = "Here are your Ads:\n\n";
			
				// list user ads 
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
	    	    
		}  else { // if it's a new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}
			
	    // YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer );

	}

	public static void itemDetails ( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			if( message.hasParameter() ) {
				try {
					itemServices.validateId(message.getParameter(), message.getTelegramUserName());

				} catch ( BusinessException e ) {
							
					botAnswer = "Id " +  message.getParameter() + "is not valid:\n"
							+ e.getMessage()
							+ "\nThe command /itemdetails require a item id as parameter.\n"
							+ "Type /itemdetails_id, replacing id by the id number of the item you want to see.\n\n"
							+ "For instance, /itemdetails_123.\n\n"
							+ "To check you items id type /myshare.";
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				try {
					Item item = itemServices.readItem(message.getParameter());
					
					// display item ad
					botAnswer = "Item id #" + message.getParameter() + " Ad:\n\n"
							+ item.getName() + "\n"
							+ "Status: " + (item.isAvailable() ? "public" : "private") + "\n"
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
					
		        	botAnswer = "Do you want to update this item?\n"
		        			+ "/edit_id - update item\n"
		        			+ "/delete_id - remove item\n"
		        			+ "/changeadstatus_id - switch ad status (private/public)\n\n"
		        			+ EmojiParser.parseToUnicode("(ps: replace id with item's id number :wink:)");
		        	
		        	// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
				} catch (BusinessException e) {
					// define bot answer			
					botAnswer = "Problem trying to read item:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				} catch (DataException e) {
					// define bot answer			
					botAnswer = "Problem trying to read item:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				
			}
			
		} else { // if it's a new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		}
		
	}

	public static void  changeAdStatus( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {

			
		} else { // if it's a new user
    		
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
			
			botAnswer = "Here are the items you have solicited a reservation:\n";
				
			// list user ads				
			botAnswer += "Type the reservation id for more details.\n";
			botAnswer += "If you would like to cancel a reservation, type /cancelreservation.\n\n";
			botAnswer += "Type /search to find more items!\n";
				
			// TODO if-else....
			botAnswer = "You don't have any reservation yet!\n";
			botAnswer += "Type /search to find an items!\n";
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}  else { // if it's a new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}		
			
	    // YouShare bot logins
        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	}

	public static void search ( MessageData message ) {
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) { 		
			
			botAnswer = "Which item are you interested?\n";
				
			// list user ads
				
			//botAnswer += "Type the item id for more details. \n"; \\ TODO como põe essa msg???
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}  else { // new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}

		// YouShare bot logins
		YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

	}
	
	public static void yesUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Done! You've been successfully unsubscribed from YouShare!";
		
		try {
			userServices.deleteUser( callbackMessage.getTelegramUserName() );
		} catch ( BusinessException e ) {
			e.printStackTrace();
		} catch ( DataException e ) {
			e.printStackTrace();
		}		
		
		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
		
		// YouShare bot callback logins
        YouShareBotFacade.logCallback( callbackMessage.getTelegramUserName(), callbackMessage.getChatId(), callbackMessage.getMessageId(), callbackMessage.getCallbackData(), botAnswer);
	}
	
	public static void noUnregister ( MessageData callbackMessage ) {
		String botAnswer = "Yay! You're staying!";

		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
		
		// YouShare bot callback logins
        YouShareBotFacade.logCallback( callbackMessage.getTelegramUserName(), callbackMessage.getChatId(), callbackMessage.getMessageId(), callbackMessage.getCallbackData(), botAnswer);
	}

	@Override
	public String registerAdImage(String userFirstName, String userLastName, String telegramUserName, String imageId, String chatId ) {
		
		return "ToDo";
	}
	
	/// Utils
	/*private static void printTool( Tool tool ) {
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

	}*/
	
}
