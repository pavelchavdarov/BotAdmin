package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.Models.Message;
import PaulTelegramBots.ZinurivBotAdmin.Models.Post;





public class BotFunctions {
	private static Connection connection;
	
	public static QueryResults AddSubscriber(Long chatId) {
		PreparedStatement prepStatment;
		QueryResults result = null;
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select exists(select true from subscribers  where chat_id = ?) res");
            prepStatment.setBigDecimal(1, BigDecimal.valueOf(chatId));
            ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
                if(!(rs.getBoolean("res"))){
                    prepStatment = connection.prepareStatement("INSERT INTO subscribers (chat_id, subscribe_date) VALUES (?, current_date)");
                    prepStatment.setBigDecimal(1, BigDecimal.valueOf(chatId));
                    int res = prepStatment.executeUpdate();
                    result = QueryResults.Successful;
                }
                else {
                    result = QueryResults.AllreadyExists;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = QueryResults.Exception;
        }
		return result;
	}
	
	public static QueryResults RemoveSubscriber(Long chatId) {
		PreparedStatement prepStatment;
		QueryResults result = null;
        try {
        	if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
            prepStatment = connection.prepareStatement("DELETE FROM subscribers WHERE  chat_id = ?");
            prepStatment.setBigDecimal(1, BigDecimal.valueOf(chatId));
            prepStatment.executeUpdate();
            result = QueryResults.Successful;
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = QueryResults.Exception;
        }
        return result;
	}
	
	public static String GetToken(String botUserName) {
		PreparedStatement prepStatment;
		String token = null;
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("SELECT token FROM bots WHERE username = ?");
			prepStatment.setString(1, botUserName);
			ResultSet rs = prepStatment.executeQuery();
			if(rs.next()) {
				token = rs.getString(1);
			}
			connection.close();
		}catch (Exception e) {
            e.printStackTrace();
        }
		return token;
	}
	
	public static List<Post> getBotPosts(String botUserName){
		ArrayList<Post> result = new ArrayList<>();
		PreparedStatement prepStatment;
		Connection connection;
		try {
			connection = HikariCP.getDataSource().getConnection();
			
//			prepStatment = connection.prepareStatement("SELECT p.id id, p.message message, p.daydelay delay FROM posts p, post_scripts ps where p.ref_script_id = ?");
			prepStatment = connection.prepareStatement("SELECT p.id id, p.message message, p.daydelay delay FROM posts p, bots b WHERE b.username = ? and p.ref_bot = b.id");
			prepStatment.setString(1, botUserName);
			ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
            	result.add(new Post(rs.getLong("id"), String.valueOf(rs.getInt("delay")), rs.getString("message")));
            }
            connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void saveBotPost(Post post, String userName) {
		PreparedStatement prepStatment;
		BigDecimal botId = null;
		Connection connection;
		try {
			connection = HikariCP.getDataSource().getConnection();
			if (post.isPersisted()) {
				prepStatment = connection.prepareStatement("update posts set daydelay = ?, message = ? where id = ?");
				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
				prepStatment.setString(2, post.getMessage());
				prepStatment.setBigDecimal(3, BigDecimal.valueOf(post.getId()));
				prepStatment.executeUpdate();
			}
			else {
				prepStatment = connection.prepareStatement("select id from bots where username = ?");
				prepStatment.setString(1, userName);
				ResultSet rSet = prepStatment.executeQuery();
				if(rSet.next())
					botId = rSet.getBigDecimal(1);
				prepStatment = connection.prepareStatement("insert into posts (daydelay, message, ref_bot) values(?, ?, ?)");
				prepStatment.setInt(1, Integer.valueOf(post.getDayDelay()));
				prepStatment.setString(2, post.getMessage());
				prepStatment.setBigDecimal(3, botId);
				//prepStatment.setBigDecimal(3, BigDecimal.valueOf(postScriptId));
				prepStatment.executeUpdate();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteBotPost(Post post) {
		PreparedStatement prepStatment;
		Connection connection;
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("delete from posts where id = ?");
			prepStatment.setBigDecimal(1, BigDecimal.valueOf(post.getId()));
			prepStatment.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveBotMessage(Message message, String botUserName) {
		PreparedStatement prepStatment;
		BigDecimal botId = null;
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			if (message.isPersisted()) {
				prepStatment = connection.prepareStatement("update messages set date_to_send = ?, message = ? where id = ?");
				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
				prepStatment.setString(2, message.getMessage());
				prepStatment.setBigDecimal(3, BigDecimal.valueOf(message.getId()));
				prepStatment.executeUpdate();
			}
			else {
				prepStatment = connection.prepareStatement("select id from bots where username = ?");
				prepStatment.setString(1, botUserName);
				ResultSet rSet = prepStatment.executeQuery();
				if(rSet.next())
					botId = rSet.getBigDecimal(1);
				
				prepStatment = connection.prepareStatement("insert into messages (date_to_send, message, ref_bot) values(?, ?, ?)");
				prepStatment.setDate(1, Date.valueOf(message.getDateToSend()));
				prepStatment.setString(2, message.getMessage());
				prepStatment.setBigDecimal(3,  botId);
				prepStatment.executeUpdate();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
