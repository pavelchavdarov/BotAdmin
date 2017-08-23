package PaulTelegramBots.ZinurivBotAdmin.MessageScript;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable, Cloneable{
	private Long id;
	private String msg;
	private String timeshift;
	
	public Message() {}
	
	public Message(long id, String timeshift, String msg) {
		this.id = id;
		this.timeshift = timeshift;
		this.msg = msg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimeshift() {
		return timeshift;
	}
	public void setTimeshift(String timeshift) {
		this.timeshift = timeshift;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Message && obj.getClass().equals(getClass())) {
			return this.id.equals(((Message) obj).id);
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
	public Message clone() throws CloneNotSupportedException {
		return (Message) super.clone();
	}

	@Override
	public String toString() {
		return id + " \"" + msg + "\" " + timeshift;
	}
	
	public boolean isPersisted() {
		return id != null;
	}

}
