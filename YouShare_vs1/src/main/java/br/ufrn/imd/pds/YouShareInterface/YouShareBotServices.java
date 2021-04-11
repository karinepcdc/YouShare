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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pds.APIinterface.TelegramBotAPIServices;
import br.ufrn.imd.pds.APIinterface.MessageData;
import br.ufrn.imd.pds.APIinterface.TelegramBotAPIFacade;
import br.ufrn.imd.pds.business.FacadeItem;
import br.ufrn.imd.pds.business.FacadeUser;
import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.ItemServices;
import br.ufrn.imd.pds.business.Appliance;
import br.ufrn.imd.pds.business.User;
import br.ufrn.imd.pds.business.UserServices;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.UIException;
import br.ufrn.imd.pds.formParsers.FormToItem;

public class YouShareBotServices implements YouShareBotFacade {
	
	private static TelegramBotAPIFacade apiServices;
	private static FacadeUser userServices;
	private static FacadeItem itemServices;

	public YouShareBotServices() throws DataException {
		apiServices = TelegramBotAPIServices.getInstance();
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
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer);
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
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
	    
	}
	
	public static void register ( MessageData message ) {
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {  		

			botAnswer = message.getUserFirstName() + ", you are already registered in our system!"
					+ "Type /help to see the main menu.\n"
					+ "Or, if you want to leave our community, type /unregister.";

			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );

	    } else { // if it's a new user
	
	    	User newUser = new User( message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), "0", "0", "No reviews yet!" );
			try {
				userServices.createUser( newUser );
				
				botAnswer = message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "you've been successfully registered into our system!\n\n"
	    				+ "Type /help to see the main menu.\n"
	    				+ "If you changed your mind, type /unregister. ";
				
				// request APIInterface to send text message to user
			    apiServices.sendTextMsg( message.getChatId(), botAnswer );

			} catch ( BusinessException e ) {
				botAnswer =  "We could not register you into our systems. \n";
				botAnswer += "Note that you should have a Telegram Username defined to be able to register.\n";
				botAnswer += e.getMessage();
				
				// request APIInterface to send text message to user
			    apiServices.sendTextMsg( message.getChatId(), botAnswer );
			} catch ( DataException e ) {
				botAnswer =  "We could not register you into our systems. \n";
				botAnswer += "Note that you should have a Telegram Username defined to be able to register.";
				botAnswer += e.getMessage();
				
				// request APIInterface to send text message to user
			    apiServices.sendTextMsg( message.getChatId(), botAnswer );
			}
				
			
	    			    
	    }
	
		
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
		    
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
	    YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getTxtMessage(), botAnswer);

	}
	
	public static void profile ( MessageData message ) {
		String botAnswer = "";

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		
		if( isUserRegistered ) { 			
			
	    	try {
	    		User user = userServices.readUser( message.getTelegramUserName() );
	    		
				botAnswer = "This is how YouShare users see you: \n\n"
							+ "Name: " + user.getFirstName() + " " + user.getLastName() + "\n"
				    		+ "Grade: " + user.getUserGrade() + "\n"
				    		+ "Last review: \n" + user.getLastReview();
			} catch ( BusinessException e ) {
				botAnswer = "We could not read this profile: ";
				botAnswer += e.getMessage() ;
			} catch ( DataException e ) {
				botAnswer = "We could not read this profile: ";
				botAnswer += e.getMessage() ;
			}
	
	   		// request APIInterface to send text message to user
	   		apiServices.sendTextMsg( message.getChatId(), botAnswer );
	
	   		botAnswer = "Do you want to change your profile?\n\n"
	    				+ "/edituser - change your name. \n";
			
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
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer);

			
	}
	
	public static void editUserInterface ( MessageData message ) {
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		
		if( isUserRegistered ) {
			botAnswer = "How would you like to be called? \n";
			
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
	        
		    // request user reply
		    apiServices.requestUserReply( "EditUserBackend" );
			
			
		} else { // if it's a new user
    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n \n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
    	    
		}
	}
	
	public static void editUserBackend ( MessageData message ) {
		
		String botAnswer = "";
		
		try {
			User user = userServices.readUser( message.getTelegramUserName() );
			user.setFirstName( message.getTxtMessage() );
			userServices.updateUser( user );
		} catch ( BusinessException | DataException e ) {
			botAnswer = "We could not update your name: ";
			botAnswer += e.getMessage() ;
		}		
		
		botAnswer = "Your name was updated with success! \n";
		botAnswer += "If you want to check it, use the command /profile. \n";
		
		// request APIInterface to send text message to user
        apiServices.sendTextMsg( message.getChatId(), botAnswer );
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
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );

	}
	
	public static void addItemInterface ( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			botAnswer = "Fill the following form (copy and edit message):\n\n";
			botAnswer += "Ps: All fields are mandatory.\n";
			botAnswer += "Ps2: Don't use currency symbol in the price field.\n";
			botAnswer += "Ps3: Condition can be: weared, good or new.\n";
			botAnswer += "Ps4: Voltage can be: 110, 220 or none.\n";

			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
			botAnswer  = "<Name> </Name>\n";
			botAnswer += "<Description> </Description>\n";
			botAnswer += "<Price> </Price>\n";
			botAnswer += "<Terms of use> </Terms of use>\n";
			botAnswer += "<Condition> </Condition>\n";
			botAnswer += "<Voltage> </Voltage>\n";
			
			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
			// request user reply
			apiServices.requestUserReply("AddItemBackend");
        
		} else { // if it's a new user
    		
		botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
					+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
		// request APIInterface to send text message to user
        apiServices.sendTextMsg( message.getChatId(), botAnswer );
				
		}
	
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
		
	}

	public static void addItemBackend ( MessageData message ) {
		String botAnswer = "";
		
		// Extract item information from user text message
		// TODO include other type of itens
		Appliance newAppliance;
		try {
			newAppliance = FormToItem.createFormToAppliance( message.getTxtMessage(), message.getTelegramUserName());
			try {
				String code = itemServices.createItem( newAppliance );
				
				botAnswer = EmojiParser.parseToUnicode("Item " + "(id: " + code + ") created! :wink:\n");
				botAnswer += "Check your item typing /itemdetails_" + code + ".\n";
				botAnswer += "To check your items id type /myshare.";
				
				// request APIInterface to send text message to user
		        apiServices.sendTextMsg( message.getChatId(), botAnswer );
		        
			} catch (BusinessException e) {
				// define bot answer			
				botAnswer = "Problem trying to create item:\n" + e.getMessage() + "\n";
				botAnswer += "Check if you have folowed all instructions and try again: /additem.";
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
			} catch (DataException e) {
				// define bot answer			
				botAnswer = "Problem trying to create item in the database:\n" + e.getMessage();
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
			}		
		
		} catch (UIException e1) {
			// define bot answer			
			botAnswer = "Problem trying to read reply:\n" + e1.getMessage();
						
			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
		}
		
	}

	public static void editItemInterface ( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			botAnswer = "Fill the fields you would like to edit wapping the information using the folling tags:\n\n";
			botAnswer += "Ps: Don't use currency symbol in the price field.\n";
			botAnswer += "Ps2: Condition can be: weared, good or new.\n";
			botAnswer += "Ps3: Voltage can be: 110, 220 or none.\n";

			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
			botAnswer  = "<Id>" + message.getParameter() + "</Id> (item id is mandatory)\n";
			botAnswer += "<Name> </Name>\n";
			botAnswer += "<Description> </Description>\n";
			botAnswer += "<Price> </Price>\n";
			botAnswer += "<Terms of use> </Terms of use>\n";
			botAnswer += "<Condition> </Condition>\n";
			botAnswer += "<Voltage> </Voltage>\n";
			
			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
			
			// request user reply
			apiServices.requestUserReply("EditItemBackend");
        
		} else { // if it's a new user
    		
		botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
					+ "I didn't find you in our systems!\n\n"
    				+ "Type /help to see the main menu.\n";
			
		// request APIInterface to send text message to user
        apiServices.sendTextMsg( message.getChatId(), botAnswer );
				
		}
	
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
		
	}

	public static void editItemBackend ( MessageData message ) {
		String botAnswer = "";
		
		// Extract item information from user text message
		// TODO include other type of itens
		Appliance newAppliance;
		try {
			String itemId = "";
			String RegexCode = "<Id>\\s*(.+?)\\s*</Id>.*?\n";

			// check if Id edition where sent
			Pattern itemPattern = Pattern.compile(RegexCode);
			Matcher m = itemPattern.matcher(message.getTxtMessage());

			if( m.find() ) {
				itemId = m.group(1);
				// log
				System.out.println("Id read: ." + itemId + ".\n");
				
			} else {
				throw new UIException("Need item if to do edition.");
			}
			
			
			Item originalAppliance = itemServices.readItem( itemId );
			newAppliance = FormToItem.editFormToAppliance( message.getTxtMessage(), (Appliance) originalAppliance);
				
			String code = itemServices.updateItem( newAppliance );
				
			botAnswer = EmojiParser.parseToUnicode("Item " + "(id: " + code + ") updated! :wink:\n");
			botAnswer += "Check your item typing /itemdetails_" + code + ".\n";
			botAnswer += "To check your items id type /myshare.";
				
			// request APIInterface to send text message to user
		    apiServices.sendTextMsg( message.getChatId(), botAnswer );
		        
		
		
		} catch (UIException e1) {
			// define bot answer			
			botAnswer = "Problem trying to read reply:\n" + e1.getMessage();
						
			// request APIInterface to send text message to user
			apiServices.sendTextMsg( message.getChatId(), botAnswer );
		} catch (BusinessException e) {
			// define bot answer			
			botAnswer = "Problem trying to edit item:\n" + e.getMessage() + "\n";
			botAnswer += "Check if you have folowed all instructions and try again: /additem.";
			
			// request APIInterface to send text message to user
        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
		} catch (DataException e) {
			// define bot answer			
			botAnswer = "Problem trying to edit item in the database:\n" + e.getMessage();
			
			// request APIInterface to send text message to user
        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
		}		
		
	}

	public static void itemDetails ( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			if( message.hasParameter() ) {
				try {
					itemServices.validateId(message.getParameter(), message.getTelegramUserName());

				} catch ( BusinessException e ) {
							
					botAnswer = "Id " +  message.getParameter() + "is not valid:\n";
					botAnswer += e.getMessage();
					botAnswer += "\nThe command /itemdetails require a item id as parameter.\n";
					botAnswer += "Type /itemdetails_id, replacing id by the id number of the item you want to see.\n\n";
					botAnswer += "For instance, /itemdetails_0.\n\n";
					botAnswer += "To check your items id type /myshare.";
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				String id = message.getParameter();
				try {
					Item item = itemServices.readItem(id);
					
					// display item ad
					botAnswer  = "Item id #" + id + " Ad:\n\n";
					botAnswer += item.getName() + "\n";
					botAnswer += "Description: " + item.getDescription() + "\n";
					botAnswer += "Grade: " + item.getItemGrade() + "\n";
					botAnswer += "Most recent review: " + item.getLastReview() + "\n";
					
					// item specifics
					if( item instanceof Appliance ) {
						botAnswer += "Price: $" + ((Appliance) item).getPrice() + "\n\n";
						botAnswer += "Terms of use: " + ((Appliance) item).getTermsOfUse() + "\n";
						botAnswer += "Condition: " + ((Appliance) item).getCondition() + "\n";
						botAnswer += "Voltage: " + ((Appliance) item).getVoltage() + "\n";
						botAnswer += "Status: " + (((Appliance) item).isAvailable() ? "public" : "private");

					}
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		        	botAnswer = "Do you want to update this item?\n";
		        	botAnswer += "/edititem_" + id + " - update item\n";
		        	botAnswer += "/deleteitem_" + id + " - remove item\n";
		        	botAnswer += "/changeadstatus_" + id + " - switch ad status (private/public)\n\n";
		        	
		        	// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
				} catch (BusinessException e) {
					botAnswer = "Problem trying to read item:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				} catch (DataException e) {
					botAnswer = "Problem trying to read item:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				
			} else {
				
				botAnswer = "\nThe command /itemdetails require a item id as parameter.\n"
						+ "Type /itemdetails_id, replacing id by the id number of the item you want to see.\n\n"
						+ "For instance, /itemdetails_0.\n\n"
						+ "To check you items id type /myshare.";
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
			}
			
		} else { // if it's a new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		}
		
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
		
	}

	// warning: appliance specific methold
	public static void changeAdStatus( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			if( message.hasParameter() ) {
				try {
					itemServices.validateId(message.getParameter(), message.getTelegramUserName());

				} catch ( BusinessException e ) {
							
					botAnswer = "Id " +  message.getParameter() + "is not valid:\n"
							+ e.getMessage()
							+ "\nThe command /changeadstatus require a appliance id as parameter.\n"
							+ "Type /changeadstatus_id, replacing id by the id number of the item you want to see.\n\n"
							+ "For instance, /changeadstatus_0.\n\n"
							+ "To check your appliances ids type /myshare.";
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				// change ad status
				String id = message.getParameter();
				try {
					itemServices.changeAvailability( id );
					Item item = itemServices.readItem( id );
					
					botAnswer = item.getName() + " (id: " + item.getCode() + ") have been set to " + (((Appliance) item).isAvailable() ? "public" : "private");
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
		        	
				} catch (BusinessException e) {
					botAnswer = "Problem trying to update item availability:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				} catch (DataException e) {
					botAnswer = "Problem trying to update item:\n" + e.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}				
				

			} else {
				
				botAnswer = "\nThe command /changeadstatus require a item id as parameter.\n"
						+ "Type /changeadstatus_id, replacing id by the id number of the item you want to see.\n\n"
						+ "For instance, /changeadstatus_0.\n\n"
						+ "To check you items id type /myshare.";
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
			}
			
		} else { // if it's a new user
    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		}
		
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
	}

	public static void deleteItem( MessageData message ) {
		String botAnswer = ""; 

		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) {
			if( message.hasParameter() ) {
				try {
					itemServices.validateId(message.getParameter(), message.getTelegramUserName());

				} catch ( BusinessException e ) {
							
					botAnswer = "Id " +  message.getParameter() + "is not valid:\n"
							+ e.getMessage()
							+ "\nThe command /deleteitem require a item id as parameter.\n"
							+ "Type /deleteitem_id, replacing id by the id number of the item you want to see.\n\n"
							+ "For instance, /deleteitem_0.\n\n"
							+ "To check you items id type /myshare.";
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
				// confirm item deletion
				String id = message.getParameter();
				Item delItem;
				try {
					delItem = itemServices.readItem(id);
					botAnswer = "Are you sure you want to remove " + delItem.getName() + " (id: " + delItem.getCode() + ") from our systems?\n\n";
					botAnswer+= EmojiParser.parseToUnicode(":warning: Operation cannot be undone!");
					
					// send msg with inline keyboard
					String[] buttonsLabels = {"Yes", "No"};
					apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "deleteItem", buttonsLabels , 2, 1);
					
				} catch (BusinessException e1) {
					botAnswer = "Problem trying to read item asked to be deleted:\n" + e1.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				} catch (DataException e1) {
					botAnswer = "Problem trying to read item asked to be deleted from database:\n" + e1.getMessage();
					
					// request APIInterface to send text message to user
		        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
				}
				
			} else {
				
				botAnswer = "\nThe command /deleteitem require a item id as parameter.\n"
						+ "Type /deleteitem_id, replacing id by the id number of the item you want to see.\n\n"
						+ "For instance, /deleteitem_0.\n\n"
						+ "To check you items id type /myshare.";
				
				// request APIInterface to send text message to user
	        	apiServices.sendTextMsg( message.getChatId(), botAnswer );
			}
			
		} else { // if it's a new user
    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
						+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	        apiServices.sendTextMsg( message.getChatId(), botAnswer );
					
		}
		
		// YouShare bot logins
	    YouShareBotFacade.log( message.getUserFirstName(), message.getUserLastName(), 
	    		message.getTelegramUserName(), message.getTxtMessage(), botAnswer );
		
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
        YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getTxtMessage(), botAnswer);
	}

	public static void search ( MessageData message ) {
		String botAnswer = "";
		
		boolean isUserRegistered = userServices.isRegistered( message.getTelegramUserName() );
		if( isUserRegistered ) { 		
			
			botAnswer = "What item is you interested in?\n";
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );

	   	    // TODO colocar codigo seguinte dentro do comando searchbackend ou fazer um submenu
	   	    
			// list of ads
			botAnswer = "*** " + " availables ***\n";

			//itemServices.
			
			
			botAnswer += "\n\nType the item id for more details. \n";
			
			// TODO button page labels builder
			String[] buttonsLabels = {"<< prev","next >>"}; 
			
			// turn page buttons 
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "searchPage", buttonsLabels, 2, 1);

			
			botAnswer = "Filter your search: \n";			
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	   	    
			botAnswer = "Grade: ";
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "filter#" + message.getMessageId(), new String[] {"1+","2+","3+","4+"}, 4, 1);
			
			botAnswer = "Price: ";
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "filter#" + message.getMessageId(), new String[] {"until $10","$10-20","$20+"}, 3, 1);
			
			botAnswer = "Condition: ";
			apiServices.sendInlineKeyboardWithCallbackButtons( message.getChatId(), botAnswer, "filter#" + message.getMessageId(), new String[] {"weared","good","new"}, 3, 1);
	    	    
		}  else { // new user
	    		
			botAnswer = "Hello " + message.getUserFirstName() + " " + message.getUserLastName() + ", "
	    				+ "I didn't find you in our systems!\n\n"
	    				+ "Type /help to see the main menu.\n";
				
			// request APIInterface to send text message to user
	   	    apiServices.sendTextMsg( message.getChatId(), botAnswer );
	    	    
		}

		// YouShare bot logins
		YouShareBotFacade.log(message.getUserFirstName(), message.getUserLastName(), message.getTelegramUserName(), message.getTxtMessage(), botAnswer);

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
	
	public static void yesDeleteItem ( MessageData callbackMessage ) {
		String botAnswer = "";
		
		// retrieve code
		String stringTosearchId = callbackMessage.getTxtMessage();
		
		Pattern idPattern = Pattern.compile("\\(id: +(\\S)+\\)");
		Matcher m = idPattern.matcher(stringTosearchId);
		
		// if we find a match, get id
		String code = "";
		if( m.find() ) {
			code = m.group(1);
		}
		
		// build item to be deleted
		Item delItem = new Appliance("", "", "", callbackMessage.getTelegramUserName(), 0, 0, "", "", "", "", "");
		delItem.setCode( code );		
		
		// delete item
		try {
			Item toBedeleted = itemServices.readItem(code);
			botAnswer = "Done! item " + toBedeleted.getName() + " (id: " + code + ") have been deleted!" ;

			itemServices.deleteItem(delItem);
			
		} catch (BusinessException e) {
			botAnswer = "Problem trying to delete item:\n" + e.getMessage();
			
			// request APIInterface to send text message to user
        	apiServices.sendTextMsg( callbackMessage.getChatId(), botAnswer );
		} catch (DataException e) {
			botAnswer = "Problem trying to delete item from database:\n" + e.getMessage();
			
			// request APIInterface to send text message to user
        	apiServices.sendTextMsg( callbackMessage.getChatId(), botAnswer );
		}
		
		// edit callback message confirming operation and removing buttons
		apiServices.editTextMsg( callbackMessage.getChatId(), callbackMessage.getMessageId(), botAnswer);
		
		// YouShare bot callback logins
        YouShareBotFacade.logCallback( callbackMessage.getTelegramUserName(), callbackMessage.getChatId(), callbackMessage.getMessageId(), callbackMessage.getCallbackData(), botAnswer);
	}
	
	public static void noDeleteItem ( MessageData callbackMessage ) {
		String botAnswer = "Okay!";

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
	/*private static void printAppliance( Appliance appliance ) {
		System.out.println("\n**************************\n");
		System.out.println("Reading Appliance " + appliance.getCode() + "\n"
				+ "Name: " + appliance.getName() + "\n"
				+ "Description: " + appliance.getDescription() + "\n"
				+ "Owner: " + appliance.getOwner() + "\n"
				+ "itemGrade: " + appliance.getItemGrade() + "\n"
				+ "itemGradeCount: " + appliance.getItemGradeCount() + "\n"
				+ "lastReview: " + appliance.getLastReview() + "\n"
				+ "isAvailable:" + appliance.isAvailable() + "\n"
				+ "price: " + appliance.getPrice() + "\n"
				+ "TOU: " + appliance.getTermsOfUse() + "\n"
				+ "Condition: " + appliance.getCondition() + "\n"
				+ "Voltage: " + appliance.getVoltage() + "\n" );

		System.out.println("\n**************************\n");

	}*/
	
}
