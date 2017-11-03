package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatDetailModel;
import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatModel;

public class POstStatService {

	public static List<PostStatModel> getPostStat(String botUserName){
		return DAO.Statistics.getPostsStat(botUserName);
	}
	
	public static List<PostStatDetailModel> getpostStatDetails(String botUserName, Long chatID){
		return DAO.Statistics.getPostDetails(botUserName, chatID);
	}
	
	public static List<PostStatDetailModel> getSubscribers(String botUserName){
		return DAO.Statistics.getSubscribers(botUserName);
	}
}
