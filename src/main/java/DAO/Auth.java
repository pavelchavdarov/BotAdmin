package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
	private static Connection connection;
	private static PreparedStatement pStatement;
	
//	private static BigDecimal getIdByLogin(String clientLogin) {
//		BigDecimal clientId = null;
//		//TODO: добавить memcached
//		if(!clientsMap.containsKey(clientLogin)) {
//			try {
//				if (connection == null || connection.isClosed())
//					connection = HikariCP.getDataSource().getConnection();
//				pStatement = connection.prepareStatement("select id from clients where client_login = ?");
//				pStatement.setString(1, clientLogin);
//				rs = pStatement.executeQuery();
//		        if(rs.next())
//		        	clientId = rs.getBigDecimal("id");
//		        connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			clientsMap.put(clientLogin, clientId);
//		}
//		else
//			clientId = clientsMap.get(clientLogin);
//		
//        return clientId;
//	}
//	
//	public static void CreateClientEnv(String clientLogin) {
//		BigDecimal clientId = getIdByLogin(clientLogin);
//		try {
//            if (clientId != null) {
//				pStatement = connection.prepareStatement("create table + Bots_" + clientId
//					+ "(id BIGSERIAL NOT NULL CONSTRAINT Bots_" + clientId + "_pkey PRIMARY KEY."
//					+ "username   VARCHAR(50) NOT NULL,"
//					+ "token VARCHAR(100) NOT NULL,"
//					+ "hello_message text,"
//					+ "bay_message text,"
//					+ "author_url text"
//					+ ")");
//				pStatement.executeQuery();
//				
//				pStatement = connection.prepareStatement("CREATE TABLE subscribers_" + clientId
//					+"("
//					+ "id BIGSERIAL NOT NULL CONSTRAINT subscribers_"+ clientId + "_pkey PRIMARY KEY,"
//					+ "chat_id        BIGINT    NOT NULL,"  // id чата подписчика
//					+ "script_id      BIGINT,"				// подписка
//					+ "bot_id      	  BIGINT,"				// бот, через которую подписчик будет получать сообщения из подписки
//					+ "subscribe_date DATE      NOT NULL,"	// дата подписки
//					+ "last_post      BIGINT"				// последнее полученное сообщение из подписки
//					+")");
//				pStatement.executeQuery();
//				
//				pStatement = connection.prepareStatement("CREATE TABLE \"Posts_" + clientId + "\""
//					+"("
//					+ "\"ID\" BIGSERIAL NOT NULL CONSTRAINT \"Posts_" + clientId + "_pkey\" PRIMARY KEY,"
//					+ "\"ScriptID\" BIGINT,"
//					+ "\"Message\"  TEXT      NOT NULL,"
//					+ "\"DayDelay\" INTEGER   NOT NULL"
//					+")");
//				pStatement.executeQuery();
//				
//				pStatement = connection.prepareStatement("CREATE TABLE messages_" + clientId  
//					+"(" 
//					+ "  id           BIGSERIAL NOT NULL CONSTRAINT messages_" + clientId + "_pkey PRIMARY KEY," 
//					+ "  message      TEXT,"
//					+ "  date_to_send DATE," 
//					+ "  is_sent      BOOLEAN"
//					+ ")");
//				pStatement.executeQuery();
//            }	
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void CreateClientEnv(BigDecimal clientId) {
//		try {
//			if (connection == null || connection.isClosed())
//				connection = HikariCP.getDataSource().getConnection();
//			pStatement = connection.prepareStatement("create table + Bots_" + clientId
//				+ "(id BIGSERIAL NOT NULL CONSTRAINT Bots_" + clientId + "_pkey PRIMARY KEY."
//				+ "username   VARCHAR(50) NOT NULL,"
//				+ "token VARCHAR(100) NOT NULL,"
//				+ "hello_message text,"
//				+ "bay_message text,"
//				+ "author_url text"
//				+ ")");
//			pStatement.executeQuery();
//			
//			pStatement = connection.prepareStatement("CREATE TABLE subscribers_" + clientId
//				+"("
//				+ "id BIGSERIAL NOT NULL CONSTRAINT subscribers_"+ clientId + "_pkey PRIMARY KEY,"
//				+ "chat_id        BIGINT    NOT NULL,"  // id чата подписчика
//				+ "script_id      BIGINT,"				// подписка
//				+ "bot_id      	  BIGINT,"				// бот, через которую подписчик будет получать сообщения из подписки
//				+ "subscribe_date DATE      NOT NULL,"	// дата подписки
//				+ "last_post      BIGINT"				// последнее полученное сообщение из подписки
//				+")");
//			pStatement.executeQuery();
//			
//			pStatement = connection.prepareStatement("CREATE TABLE \"Posts_" + clientId + "\""
//				+"("
//				+ "\"ID\" BIGSERIAL NOT NULL CONSTRAINT \"Posts_" + clientId + "_pkey\" PRIMARY KEY,"
//				+ "\"ScriptID\" BIGINT,"
//				+ "\"Message\"  TEXT      NOT NULL,"
//				+ "\"DayDelay\" INTEGER   NOT NULL"
//				+")");
//			pStatement.executeQuery();
//			
//			pStatement = connection.prepareStatement("CREATE TABLE messages_" + clientId  
//				+"(" 
//				+ "  id           BIGSERIAL NOT NULL CONSTRAINT messages_" + clientId + "_pkey PRIMARY KEY," 
//				+ "  message      TEXT,"
//				+ "  date_to_send DATE," 
//				+ "  is_sent      BOOLEAN"
//				+ ")");
//			pStatement.executeQuery();
//			
//			connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void CreateClient(String firstName, String lastName, String login, String passfrace, String phoneNumber, String email) { 
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			pStatement = connection.prepareStatement(
					"INSERT INTO clients (first_name, last_name, client_login, password, phone_number, email) "
					+ "VALUES (?, ?, ?, ?, ?, ?)"
			);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setString(3, login);
			pStatement.setString(4, passfrace);
			pStatement.setString(5, phoneNumber);
			pStatement.setString(6, email);
			pStatement.executeUpdate();
//			if(rs.next()) {
//				CreateClientEnv(rs.getBigDecimal(1));
//			}
//			CreateClientEnv(login);
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean CheckClient(String login, String passfrace) {
		int dummy = 0;
		try {
			if (connection == null || connection.isClosed())
				connection = HikariCP.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement("select count(id) from clients where client_login = ? and password = ?");
			ps.setString(1, login);
			ps.setString(2, passfrace);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				dummy = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dummy == 1;
	} 
}
