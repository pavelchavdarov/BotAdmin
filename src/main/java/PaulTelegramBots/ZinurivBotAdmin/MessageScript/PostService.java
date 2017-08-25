package PaulTelegramBots.ZinurivBotAdmin.MessageScript;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.HikariCP;

public class PostService {
	
	private static Connection connection;
	
	private static PostService messageservice;
	
	private PostService() {
	}
	
	public static PostService getInstance() {
		if (messageservice == null) {
			messageservice = new PostService();
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
			prepStatment = connection.prepareStatement("SELECT \"ID\" id, \"Message\" message, \"Date_to_send\" sand_date FROM \"Messages\"");
			
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Message(rs.getLong("id"), rs.getDate("sand_date"), rs.getString("message")));
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
				prepStatment = connection.prepareStatement("update \"Messages\" set \"Date_to_send\" = ?, \"Message\" = ? where \"ID\" = ?");
				prepStatment.setDate(1, message.getDateToSend());
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
				prepStatment = connection.prepareStatement("insert into \"Messages\" (\"Date_to_send\", \"Message\") values(?, ?)");
				prepStatment.setDate(1, message.getDateToSend());
				prepStatment.setString(2, message.getMessage());
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
