package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.HikariCP;
import PaulTelegramBots.ZinurivBotAdmin.Models.Message;

public class MessageService {
	
	private static Connection connection;
	
	private static MessageService messageservice;
	
	private MessageService() {
	}
	
	public static MessageService getInstance() {
		if (messageservice == null) {
			messageservice = new MessageService();
		}
		try {
			if (connection == null || connection.isClosed()) {
				connection = HikariCP.getDataSource().getConnection();
				System.out.println("Spawn new connection");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageservice;
	}
	
	public synchronized List<Message> findAll(){
		ArrayList<Message> result = new ArrayList<>();
		PreparedStatement prepStatment;
		
		try {
			prepStatment = connection.prepareStatement("SELECT id, message, date_to_send FROM messages");
			
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Message(rs.getLong("id"), rs.getDate("date_to_send").toLocalDate(), rs.getString("message")));
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public synchronized void delete(Message message) {
		PreparedStatement prepStatment;
		
		try {
			prepStatment = connection.prepareStatement("delete from \"Messages\" where \"ID\" = ?");
			prepStatment.setBigDecimal(1, BigDecimal.valueOf(message .getId()));
			prepStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void save(Message message) {
		PreparedStatement prepStatment;
		if (message.isPersisted()) {
			try {
				prepStatment = connection.prepareStatement("update messages set date_to_send = ?, message = ? where id = ?");
				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
				prepStatment.setString(2, message.getMessage());
				prepStatment.setBigDecimal(3, BigDecimal.valueOf(message.getId()));
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				prepStatment = connection.prepareStatement("insert into messages (date_to_send, message) values(?, ?)");
				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
				prepStatment.setString(2, message.getMessage());
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
