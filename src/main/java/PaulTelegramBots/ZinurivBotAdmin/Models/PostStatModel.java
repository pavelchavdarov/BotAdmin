package PaulTelegramBots.ZinurivBotAdmin.Models;

import com.vaadin.ui.PopupView;

public class PostStatModel {
	private Long charId;
	private String subscriberName;
	private Long postsAmount;
	
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public Long getPostsAmount() {
		return postsAmount;
	}
	public void setPostsAmount(Long postsAmount) {
		this.postsAmount = postsAmount;
	}
	public Long getCharId() {
		return charId;
	}
	public void setCharId(Long charId) {
		this.charId = charId;
	}
}