package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.ui.PopupView;

import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatDetailModel;
import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatModel;

public class Statistics {
	private static DateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	
	public static Long getSubscribersAmount(String botUserName) {
		PreparedStatement prepStatment;
		Connection connection;
		Long amount = null;
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select count(1) from subscribers sub, bots bot "
					+ "where bot.username = ? and sub.ref_bot = bot.id");
			prepStatment.setString(1, botUserName);
			ResultSet resultSet = prepStatment.executeQuery();
			if(resultSet.next())
				amount = resultSet.getLong(1);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return amount;
	}
	
	public static Long getTotalPostsSendAmount(String botUserName) {
		PreparedStatement prepStatment;
		Connection connection;
		Long amount = null;
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select count(1) from posting_statistic posts, bots bot "
					+ "where bot.username = ? and posts.ref_bot = bot.id");
			prepStatment.setString(1, botUserName);
			ResultSet resultSet = prepStatment.executeQuery();
			if(resultSet.next())
				amount = resultSet.getLong(1);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return amount;
	}
	
	public static HashMap<String, Long> getSubscribeStat(String botUserName){
		PreparedStatement prepStatment;
		Connection connection;
		HashMap<String, Long> resultMap = new HashMap<>();
		//String dummyDate;
		//DateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select sub_stat.event_date, count(sub_stat.subscriber_name) " + 
					"from subscribe_statistic sub_stat, bots bot " + 
					"WHERE   bot.username = ?" +
					"    and sub_stat.event = 1 " + 
					"    and sub_stat.ref_bot = bot.id " + 
					"GROUP BY sub_stat.event_date " + 
					"ORDER BY sub_stat.event_date");
			prepStatment.setString(1, botUserName);
			ResultSet resultSet =  prepStatment.executeQuery();
			while(resultSet.next()) 
				resultMap.put(dFormat.format(resultSet.getDate(1)), resultSet.getLong(2));
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	public static HashMap<String, Long> getUnSubscribeStat(String botUserName){
		PreparedStatement prepStatment;
		Connection connection;
		HashMap<String, Long> resultMap = new HashMap<>();
		//String dummyDate;
		//DateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select sub_stat.event_date, count(sub_stat.subscriber_name) " + 
					"from subscribe_statistic sub_stat ,bots bot " + 
					"WHERE   bot.username = ? " +
					"    and sub_stat.event = 0 " + 
					"    and sub_stat.ref_bot = bot.id " + 
					"GROUP BY sub_stat.event_date " + 
					"ORDER BY sub_stat.event_date");
			prepStatment.setString(1, botUserName);
			ResultSet resultSet =  prepStatment.executeQuery();
			while(resultSet.next()) 
				resultMap.put(dFormat.format(resultSet.getDate(1)), resultSet.getLong(2));
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	public static List<PostStatModel> getPostsStat(String botUserName){
		PreparedStatement prepStatment;
		Connection connection;
		List<PostStatModel> resultList = new ArrayList<>();
		PostStatModel postStatModel;
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement("select ps.ref_chat_id, ps.subscriber_name, count(1) " + 
					"from public.posting_statistic ps, public.bots b " + 
					"where b.username = ? and b.id = ps.ref_bot " + 
					"group by ref_chat_id, subscriber_name");
			prepStatment.setString(1, botUserName);
			ResultSet resultSet =  prepStatment.executeQuery();
			while(resultSet.next()) {
				postStatModel = new PostStatModel();
				postStatModel.setCharId(resultSet.getLong(1));
				postStatModel.setSubscriberName(resultSet.getString(2));
				postStatModel.setPostsAmount(resultSet.getLong(3));
				resultList.add(postStatModel);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	
	public static List<PostStatDetailModel> getPostDetails(String botUserName, Long chatId){
		PreparedStatement prepStatment;
		Connection connection;
		List<PostStatDetailModel> resultList = new ArrayList<>();
		PostStatDetailModel detailModel;
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement(
					"select substr(p.message,1,20)||'...', to_char(ps.send_date, 'dd.MM.yyyy hh24:mm:ss') " + 
					"from	public.posting_statistic ps, " + 
					"		public.posts p, " + 
					"		public.bots b " + 
					"where	b.username = ? " + 
					"	and	ps.ref_bot = b.id " + 
					"	and	ps.ref_chat_id = ? " + 
					"	and ps.ref_post_id = p.id");
			
			prepStatment.setString(1, botUserName);
			prepStatment.setLong(2, chatId);
			ResultSet resultSet =  prepStatment.executeQuery();
			while(resultSet.next()) {
				detailModel = new PostStatDetailModel();
				detailModel.setPostHeader(resultSet.getString(1));
				detailModel.setSendTime(resultSet.getString(2));
				resultList.add(detailModel);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	public static List<PostStatDetailModel> getSubscribers(String botUserName){
		PreparedStatement prepStatment;
		Connection connection;
		List<PostStatDetailModel> resultList = new ArrayList<>();
		PostStatDetailModel subscriber;
		
		try {
			connection = HikariCP.getDataSource().getConnection();
			prepStatment = connection.prepareStatement(
					"select s.subscriber_name, to_char(s.subscribe_date, 'dd.MM.yyyy') " + 
					"from subscribers s, bots bots " + 
					"where	bots.username = 'ZinurovInfoPosting_bot' " + 
					"	and	s.ref_bot = bots.id"
					);
			ResultSet resultSet =  prepStatment.executeQuery();
			while(resultSet.next()) {
				subscriber = new PostStatDetailModel();
				subscriber.setPostHeader(resultSet.getString(1));
				subscriber.setSendTime(resultSet.getString(2));
				resultList.add(subscriber);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
}
