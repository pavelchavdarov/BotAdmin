package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.HikariCP;
import PaulTelegramBots.ZinurivBotAdmin.Models.Post;

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
	
	public synchronized List<Post> findAll(){
		ArrayList<Post> result = new ArrayList<>();
		PreparedStatement prepStatment;
		
		try {
			prepStatment = connection.prepareStatement("SELECT \"ID\" id, \"Message\" message, \"DayDelay\" delay FROM \"Posts\"");
			
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Post(rs.getLong("id"), String.valueOf(rs.getInt("delay")), rs.getString("message")));
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public synchronized void delete(Post post) {
		PreparedStatement prepStatment;
		
		try {
			prepStatment = connection.prepareStatement("delete from \"Posts\" where \"ID\" = ?");
			prepStatment.setBigDecimal(1, BigDecimal.valueOf(post.getId()));
			prepStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void save(Post post) {
		PreparedStatement prepStatment;
		if (post.isPersisted()) {
			try {
				prepStatment = connection.prepareStatement("update \"Posts\" set \"DayDelay\" = ?, \"Message\" = ? where \"ID\" = ?");
				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
				prepStatment.setString(2, post.getMessage());
				prepStatment.setBigDecimal(3, BigDecimal.valueOf(post.getId()));
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				prepStatment = connection.prepareStatement("insert into \"Posts\" (\"DayDelay\", \"Message\") values(?, ?)");
				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
				prepStatment.setString(2, post.getMessage());
				prepStatment.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
