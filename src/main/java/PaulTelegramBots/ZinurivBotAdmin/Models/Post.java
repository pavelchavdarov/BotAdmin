package PaulTelegramBots.ZinurivBotAdmin.Models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Post implements Serializable, Cloneable{
	private Long id;
	private String message;
	private String dayDelay;
	
	public Post() {}
	
	public Post(long id, String dayDelay, String msg) {
		this.id = id;
		this.dayDelay = dayDelay;
		this.message = msg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String msg) {
		this.message = msg;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Post && obj.getClass().equals(getClass())) {
			return this.id.equals(((Post) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public Post clone() throws CloneNotSupportedException {
		return (Post) super.clone();
	}

	@Override
	public String toString() {
		return id + " \"" + message + "\" " + dayDelay;
	}
	
	public boolean isPersisted() {
		return id != null;
	}

	public String getDayDelay() {
		return dayDelay;
	}

	public void setDayDelay(String dayDelay) {
		this.dayDelay = dayDelay;
	}

}
