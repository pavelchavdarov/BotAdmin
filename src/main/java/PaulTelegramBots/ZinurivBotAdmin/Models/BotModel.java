package PaulTelegramBots.ZinurivBotAdmin.Models;

public class BotModel {
	private String token;
	private String userName;
	private String helloPhrase;
	private String subscribePhrase;
	private String bayPhrase;
	private String authorLink;
	private String defaultAnswer;
	private Boolean isRuning;
	private Long clientId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHelloPhrase() {
		return helloPhrase;
	}
	public void setHelloPhrase(String helloPhrase) {
		this.helloPhrase = helloPhrase;
	}
	public String getBayPhrase() {
		return bayPhrase;
	}
	public void setBayPhrase(String bayPhrase) {
		this.bayPhrase = bayPhrase;
	}
	public String getAuthorLink() {
		return authorLink;
	}
	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}
	public String getDefaultAnswer() {
		return defaultAnswer;
	}
	public void setDefaultAnswer(String defaultAnswer) {
		this.defaultAnswer = defaultAnswer;
	}
	public Boolean getIsRuning() {
		return isRuning;
	}
	public void setIsRuning(Boolean isRuning) {
		this.isRuning = isRuning;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getSubscribePhrase() {
		return subscribePhrase;
	}
	public void setSubscribePhrase(String subscribePhrase) {
		this.subscribePhrase = subscribePhrase;
	}

	
}
