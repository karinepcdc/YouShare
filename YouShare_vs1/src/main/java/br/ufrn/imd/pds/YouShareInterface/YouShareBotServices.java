package br.ufrn.imd.pds.YouShareInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vdurmont.emoji.EmojiParser; // to parse emojis
//check emojis at: 
//https://emojipedia.org/beaming-face-with-smiling-eyes/
//https://www.webfx.com/tools/emoji-cheat-sheet/

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;
import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.APIinterface.TelegramBotAPIFacade;
import br.ufrn.imd.pds.business.FacadeItem;
import br.ufrn.imd.pds.business.FacadeUser;
import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.ItemServices;
import br.ufrn.imd.pds.business.Tool;
import br.ufrn.imd.pds.business.UserServices;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.ReadItemFromDatabaseException;
import br.ufrn.imd.pds.exceptions.UserAlreadyRegisteredException;
import br.ufrn.imd.pds.exceptions.UserDatabaseCreationException;
import br.ufrn.imd.pds.exceptions.UserHeaderException;
import br.ufrn.imd.pds.exceptions.UserNotRegisteredException;

public class YouShareBotServices implements YouShareBotFacade {
	
	private static TelegramBotAPIFacade apiServices;
	private static FacadeUser userServices;
	private static FacadeItem itemServices;

	
	/* Default constructor */	
	public YouShareBotServices() throws UserDatabaseCreationException {
		apiServices = new TelegramBotAPIServices();
		
		try {
			userServices = new UserServices();
		} catch ( UserHeaderException e ) { // TODO é para ser dataexception
			e.printStackTrace();
		}		

		try {
			itemServices = new ItemServices();
		} catch (DataException | ReadItemFromDatabaseException e) { // TODO Tem que ser so dataExeption
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println( "YouShareBotServices criado!" );
	}
	
	/*********************
	 * BotCommands 
	 * @return 
	 * @throws UserNotRegisteredException *
	 *********************/
	
	/* BotCommand
	 * command: Text of the command, 1-32 characters. 
	 * 			Can contain only lowercase English letters, digits and underscores.
	 * description: Description of the command, 3-256 characters.
	 */
	public static void start ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		// TODO substituir por bussinessExeption
		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );			
			if( isUserRegistered ) {	// user already regitered
				
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
			
	        // YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	        
	        
		} catch (UserNotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void help ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		// TODO substituir por bussinessExeption
		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered
	
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
		
		    // YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
	        
		} catch (UserNotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public static void register ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered    		
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

		    // YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);
		    
		} catch (UserNotRegisteredException | UserAlreadyRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	
		
	}
	
	
	public static void unregister ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered    		
				
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

			// YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

		} catch ( UserNotRegisteredException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public static void profile ( MessageData message ) {
		String botAnswer = ""; // Bot reply

		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered    		
				
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
			
		    // YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

			
		} catch ( UserNotRegisteredException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public static void myshare ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered    		
				
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
				
					// create item: Tool					
					Tool newTool3 = new Tool("Drill", "Drill that do what's expected", "", message.getTelegramUserName(), 0, 0, "", "14", "none", "220");
					newTool3.setCode(itemServices.createItem( newTool3 ) );
					
					Tool newTool4 = new Tool("Vacuum cleaner", "dam good vacuum cleaner", "", message.getTelegramUserName(), 0, 0, "", "12.4", "dont spoil", "220");
					newTool4.setCode(itemServices.createItem( newTool4 ));
					
					Tool newTool5 = new Tool("Electric sander", "Good electric sander", "", message.getTelegramUserName(), 0, 0, "", "12.6", "take care", "110");
					newTool5.setCode(itemServices.createItem( newTool5 ));
					
					Tool tool1 = (Tool) itemServices.readItem( "2" );
					printTool(tool1);
					
					tool1.setDescription("Damn good vacuum cleaner!");
					printTool(tool1);
					itemServices.updateItem(tool1);
					printTool(tool1);
					
					itemServices.changeAvailability( tool1.getCode() );
					tool1 = (Tool) itemServices.readItem( "2" );
					printTool(tool1);
					
					System.out.println("\nList of items:\n");
					List<Item> items = itemServices.readAll();
					for(Item item: items) {
						System.out.println(item.getCode() + " " + item.getName());
					}
					
					itemServices.deleteItem(newTool3);
					
					System.out.println("\nList of items:\n");
					items = itemServices.readAll();
					for(Item item: items) {
						System.out.println(item.getCode() + " " + item.getName());
					}
					
					System.out.println("\nList of " + message.getTelegramUserName() + " items:\n");
					items = itemServices.readAll(message.getTelegramUserName());
					for(Item item: items) {
						System.out.println(item.getCode() + " " + item.getName());
					}
					
					System.out.println("\nList of tools:\n");
					List<Tool> tools = itemServices.readAllTools();
					for(Tool tool: tools) {
						System.out.println(tool.getCode() + " " + tool.getName());
					}
					
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
			
		    // YouShare bot loggins
	        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getUserTxtMsg(), botAnswer);

		} catch ( UserNotRegisteredException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void myreservations ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		try {
			boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
			if( isUserRegistered ) {	// user already regitered    		
			
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

		} catch ( UserNotRegisteredException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
	}

	public static void search ( MessageData message ) {
		String botAnswer = ""; // Bot reply
		
		try {
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

		} catch ( UserNotRegisteredException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		} catch (UserNotRegisteredException e) {
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
