package PaulTelegramBots.ZinurivBotAdmin.MessageScript;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.HikariCP;

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
			prepStatment = connection.prepareStatement("select id, timeshift, msg from messages");
			
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Message(rs.getLong("id"), rs.getString("timeshift"), rs.getString("msg")));
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
			prepStatment = connection.prepareStatement("delete from messages where id = ?");
			prepStatment.setLong(1, message.getId());
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
				prepStatment = connection.prepareStatement("update messages set timeshift = ?::interval, msg = ? where id = ?");
				prepStatment.setString(1, message.getTimeshift() + " day");
				prepStatment.setString(2, message.getMsg());
				prepStatment.setLong(3, message.getId());
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				prepStatment = connection.prepareStatement("insert into messages (timeshift, msg) values(?::interval, ?)");
				prepStatment.setString(1, message.getTimeshift() + " day");
				prepStatment.setString(2, message.getMsg());
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
