package PaulTelegramBots.ZinurivBotAdmin.MessageScript;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class Message implements Serializable, Cloneable{
	private Long id;
	private String message;
	private Date dateToSend;
	
	public Message() {}
	
	public Message(long id, Date sendDate, String msg) {
		this.id = id;
		this.dateToSend = sendDate;
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
		return id + " \"" + message + "\" " + getDateToSend();
	}
	
	public boolean isPersisted() {
		return id != null;
	}

	public Date getDateToSend() {
		return dateToSend;
	}

	public void setDateToSend(Date dateToSend) {
		this.dateToSend = dateToSend;
	}



}
