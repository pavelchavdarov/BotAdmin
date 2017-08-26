package PaulTelegramBots.ZinurivBotAdmin.Models;

import java.io.Serializable;
//import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


@SuppressWarnings("serial")
public class Message implements Serializable, Cloneable{
	private Long id;
	private String message;
	private LocalDate dateToSend;
	
	public Message() {}
	
	public Message(long id, LocalDate sendDate, String msg) {
		this.id = id;
		this.dateToSend = sendDate;
		this.message = msg;
	}
	
	public Message(long id, String sendDate, String msg) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");
		this.id = id;
		
		this.dateToSend = LocalDate.parse(sendDate, formatter);
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

	public LocalDate getDateToSend() {
		return dateToSend;
	}


	public void setDateToSend(LocalDate dateToSend) {
		this.dateToSend = dateToSend;
	}



}
