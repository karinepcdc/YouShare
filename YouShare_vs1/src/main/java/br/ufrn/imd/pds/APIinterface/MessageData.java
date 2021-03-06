package br.ufrn.imd.pds.APIinterface;

public class MessageData {

	private String userFirstName;
	private String userLastName;
	private String telegramUserName;
	private String txtMessage;
	private String chatId;
	private String callbackData;
	private String parameter;

	private long messageId;
	private boolean isCallback;
	private boolean hasParameter;
	
	public MessageData() {
		super();
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getTelegramUserName() {
		return telegramUserName;
	}

	public void setTelegramUserName(String telegramUserName) {
		this.telegramUserName = telegramUserName;
	}

	public String getTxtMessage() {
		return txtMessage;
	}

	public void setTxtMessage(String userTxtMsg) {
		this.txtMessage = userTxtMsg;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getCallbackData() {
		return callbackData;
	}

	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public boolean isCallback() {
		return isCallback;
	}

	public void setCallback(boolean isCallback) {
		this.isCallback = isCallback;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public boolean hasParameter() {
		return hasParameter;
	}

	public void setHasParameter(boolean hasParameter) {
		this.hasParameter = hasParameter;
	} 	
	
}
