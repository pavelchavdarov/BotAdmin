package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.HikariCP;
import PaulTelegramBots.ZinurivBotAdmin.Models.Post;

public class PostService {
	
	private static Connection connection;
	
	private static PostService postService;
	
//	private Long postScriptId;
	
	private PostService() {
	}
	
	public static PostService getInstance() {
		if (postService == null) {
			postService = new PostService();
		}
		return postService;
	}
	
	public List<Post> findAll(String userName){
		return DAO.BotFunctions.getBotPosts(userName);
//		ArrayList<Post> result = new ArrayList<>();
//		PreparedStatement prepStatment;
//		
//		try {
//			if (connection == null || connection.isClosed())
//				connection = HikariCP.getDataSource().getConnection();
//			
////			prepStatment = connection.prepareStatement("SELECT p.id id, p.message message, p.daydelay delay FROM posts p, post_scripts ps where p.ref_script_id = ?");
//			prepStatment = connection.prepareStatement("SELECT p.id id, p.message message, p.daydelay delay FROM posts p");
////			prepStatment.setBigDecimal(1, BigDecimal.valueOf(postScriptId));
//			ResultSet rs = prepStatment.executeQuery();
//            while(rs.next()){
//            	result.add(new Post(rs.getLong("id"), String.valueOf(rs.getInt("delay")), rs.getString("message")));
//            }
//            connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return result;
	}
	
	public synchronized void delete(Post post) {
		DAO.BotFunctions.deleteBotPost(post);
//		PreparedStatement prepStatment;
//		try {
//			if (connection == null || connection.isClosed())
//				connection = HikariCP.getDataSource().getConnection();
//			prepStatment = connection.prepareStatement("delete from posts where id = ?");
//			prepStatment.setBigDecimal(1, BigDecimal.valueOf(post.getId()));
//			prepStatment.executeUpdate();
//			connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public void save(Post post, String userName) {
		DAO.BotFunctions.saveBotPost(post, userName);
//		PreparedStatement prepStatment;
//		BigDecimal botId = null;
//		try {
//			if (connection == null || connection.isClosed())
//				connection = HikariCP.getDataSource().getConnection();
//			if (post.isPersisted()) {
//				prepStatment = connection.prepareStatement("update posts set daydelay = ?, message = ? where id = ?");
//				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
//				prepStatment.setString(2, post.getMessage());
//				prepStatment.setBigDecimal(3, BigDecimal.valueOf(post.getId()));
//				prepStatment.executeUpdate();
//			}
//			else {
//				prepStatment = connection.prepareStatement("select id from bots where username = ?");
//				prepStatment.setString(1, userName);
//				ResultSet rSet = prepStatment.executeQuery();
//				if(rSet.next())
//					botId = rSet.getBigDecimal(1);
//				prepStatment = connection.prepareStatement("insert into posts (daydelay, message, ref_bot) values(?, ?, ?)");
//				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
//				prepStatment.setString(2, post.getMessage());
//				prepStatment.setBigDecimal(3, botId);
//				//prepStatment.setBigDecimal(3, BigDecimal.valueOf(postScriptId));
//				prepStatment.executeUpdate();
//			}
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

}
