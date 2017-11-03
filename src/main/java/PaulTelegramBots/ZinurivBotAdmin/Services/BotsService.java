package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;
import PaulTelegramBots.ZinurovBot.InfoBot;

public class BotsService {
	private static BotsService botsService;
	private static HashMap<String, InfoBot> runingBots = new HashMap<>();
	private static TelegramBotsApi botApi = new TelegramBotsApi();
	
	private static BotModel CheckBotRunning(BotModel bot) {
		if(runingBots.containsKey(bot.getUserName()))
			bot.setIsRuning(true);
		return bot;
	}
	
	private BotsService() {
	}
	
	public static BotsService getInstance() {
		if(botsService == null) {}
		botsService = new BotsService();
		
		return botsService;
	}
	
	public static List<BotModel> getMyBots(String clientLogin){
		List<BotModel> bots = DAO.Info.getMyBots(clientLogin);
		bots = bots.stream().map(BotsService::CheckBotRunning).collect(Collectors.toList());
		return bots;
	}
	
	public static BotModel getBotSettings(String botUserName) {
		return DAO.Info.getBotSettings(botUserName);
	}
	
	public static void saveBotSettings(BotModel bot) {
		DAO.Info.saveBotSettings(bot);
	}
	
	public static void createNewBot(BotModel bot) {
		
		bot.setClientId(AuthService.getAuthenticatedUserId());
		DAO.Info.createNewBot(bot);
	}
	
	
	public static void launchBot(String botUserName) {
		InfoBot bot;
		if(!runingBots.containsKey(botUserName)) {
			String token = DAO.BotFunctions.GetToken(botUserName);
			BotModel botModel = getBotSettings(botUserName);
			HashMap<String, String> botSettings = new HashMap<>();
			botSettings.put("BotUserName", botUserName);
			botSettings.put("BotToken", token);
			botSettings.put("BotBayPhrase", botModel.getBayPhrase());
			botSettings.put("BotHelloPhrase", botModel.getHelloPhrase());
			botSettings.put("BotAuthorLink", botModel.getAuthorLink());
			
			ApiContextInitializer.init();
	        
	        try {
	        	bot = new InfoBot(botSettings);
				bot.setSession(botApi.registerBot(bot));
				runingBots.put("botUserName", bot);
			} catch (TelegramApiRequestException e) {
				e.printStackTrace();
			}
		}
	}
}
