package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;

public class Info {
	private static Connection connection;
	//private static PreparedStatement pStatement;
	//private static ResultSet rs;
	
	public static List<BotModel> getMyBots(String clientLogin){
		BotModel botModel;
		PreparedStatement pStatement;
		ArrayList<BotModel> botsList = new ArrayList<>();
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			pStatement = connection.prepareStatement(
					"SELECT b.username, b.hello_phrase, b.bay_phrase, b.author_link, b.defaultanswer " + 
					"FROM clients cl, bots b " + 
					"WHERE   cl.client_login = ? " + 
					"    and cl.id = b.ref_client_id"
			);
			pStatement.setString(1, clientLogin);
			
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()) {
				botModel = new BotModel();
				botModel.setUserName(rs.getString(1));
				botModel.setHelloPhrase(rs.getString(2));
				botModel.setBayPhrase(rs.getString(3));
				botModel.setAuthorLink(rs.getString(4));
				botModel.setDefaultAnswer(rs.getString(5));
				
				botsList.add(botModel);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return botsList;
	}
	
	public static BotModel getBotSettings(String botUsername) {
		BotModel botModel = null;
		PreparedStatement pStatement;
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			pStatement = connection.prepareStatement(
					"SELECT b.username, b.hello_phrase, b.bay_phrase, b.author_link, b.defaultanswer, b.subscribe_phrase " + 
					"FROM bots b " + 
					"WHERE   b.username = ?"
			);
			pStatement.setString(1, botUsername);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()) {
				botModel = new BotModel();
				botModel.setUserName(rs.getString(1));
				botModel.setHelloPhrase(rs.getString(2));
				botModel.setBayPhrase(rs.getString(3));
				botModel.setAuthorLink(rs.getString(4));
				botModel.setDefaultAnswer(rs.getString(5));
				botModel.setSubscribePhrase(rs.getString(6));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return botModel;
	}
	
	public static void saveBotSettings(BotModel bot) {
		PreparedStatement pStatement;
		
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			
			pStatement = connection.prepareStatement(
					"update bots"
					+ " set author_link = ?, hello_phrase = ?, bay_phrase = ?, defaultanswer = ?, subscribe_phrase = ?"
					+ " where username = ?"
			);
			pStatement.setString(1, bot.getAuthorLink());
			pStatement.setString(2, bot.getHelloPhrase());
			pStatement.setString(3, bot.getBayPhrase());
			pStatement.setString(4, bot.getDefaultAnswer());
			pStatement.setString(5, bot.getSubscribePhrase());
			pStatement.setString(6, bot.getUserName());
			
			pStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Long getClientIdByLogin(String clientLogin) {
		PreparedStatement pStatement;
		Long clientId = null;
		
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			pStatement = connection.prepareStatement("select id from clients where client_login = ?");
			pStatement.setString(1, clientLogin);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()) {
				clientId = rs.getBigDecimal(1).longValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientId;
	}
	
	public static void createNewBot(BotModel bot) { 
		PreparedStatement pStatement;
		
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			
			pStatement = connection.prepareStatement(
					"INSERT " + 
					"INTO bots (token, username, ref_client_id, hello_phrase, author_link, bay_phrase, defaultanswer, subscribe_phrase)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
			);
			pStatement.setString(1, bot.getToken());
			pStatement.setString(2, bot.getUserName());
			pStatement.setBigDecimal(3, BigDecimal.valueOf(bot.getClientId()));
			pStatement.setString(4, bot.getHelloPhrase());
			pStatement.setString(5, bot.getAuthorLink());
			pStatement.setString(6, bot.getBayPhrase());
			pStatement.setString(7, bot.getDefaultAnswer());
			pStatement.setString(8, bot.getSubscribePhrase());
			
			pStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
