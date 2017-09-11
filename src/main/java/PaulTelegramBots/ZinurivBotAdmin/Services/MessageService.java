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

import DAO.HikariCP;
import PaulTelegramBots.ZinurivBotAdmin.Models.Message;

public class MessageService {
	
	private static Connection connection;
	
	private static MessageService messageservice;
	
	private MessageService() {
	}
	
	public static MessageService getInstance() {
		if (messageservice == null)
			messageservice = new MessageService();
		return messageservice;
	}
	
	public synchronized List<Message> findAll(String botUserName){
		ArrayList<Message> result = new ArrayList<>();
		PreparedStatement prepStatment;
		
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("SELECT m.id, m.message, m.date_to_send FROM messages m, bots b WHERE b.username = ? and m.ref_bot = b.id");
			prepStatment.setString(1, botUserName);
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Message(rs.getLong("id"), rs.getDate("date_to_send").toLocalDate(), rs.getString("message")));
            }
            connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public synchronized void delete(Message message) {
		PreparedStatement prepStatment;
		
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("delete from \"Messages\" where \"ID\" = ?");
			prepStatment.setBigDecimal(1, BigDecimal.valueOf(message .getId()));
			prepStatment.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void save(Message message, String botUserName) {
		DAO.BotFunctions.saveBotMessage(message, botUserName);
//		PreparedStatement prepStatment;
//		BigDecimal botId = null;
//		try {
//			if (connection == null || connection.isClosed())
//				connection = HikariCP.getDataSource().getConnection();
//			if (message.isPersisted()) {
//				prepStatment = connection.prepareStatement("update messages set date_to_send = ?, message = ? where id = ?");
//				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
//				prepStatment.setString(2, message.getMessage());
//				prepStatment.setBigDecimal(3, BigDecimal.valueOf(message.getId()));
//				prepStatment.executeUpdate();
//			}
//			else {
//				prepStatment = connection.prepareStatement("select id from bots where username = ?");
//				prepStatment.setString(1, botUserName);
//				ResultSet rSet = prepStatment.executeQuery();
//				if(rSet.next())
//					botId = rSet.getBigDecimal(1);
//				
//				prepStatment = connection.prepareStatement("insert into messages (date_to_send, message, ref_bot) values(?, ?, ?)");
//				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
//				prepStatment.setString(2, message.getMessage());
//				prepStatment.setBigDecimal(3,  botId);
//				prepStatment.executeUpdate();
//			}
//			connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
