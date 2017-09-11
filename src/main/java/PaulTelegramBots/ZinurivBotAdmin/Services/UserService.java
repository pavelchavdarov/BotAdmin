package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.generators.SCrypt;

public class UserService {
	private static SecureRandom random = new SecureRandom();
	
	private static Map<String, String> rememberedUsers = new HashMap<>();
	
	public static boolean isAuthenticUser(String username, String password) {
		SCrypt sCrypt = new SCrypt();
		String sCryptHash = new String(sCrypt.generate(password.getBytes(), username.getBytes(), 2, 2, 2, 32), StandardCharsets.UTF_8);
		return DAO.Auth.CheckClient(username, sCryptHash); //username.equals("zinurov") && password.equals("Bot");
	}
	
	public static String rememberUser(String username) {
		String randomId = new BigInteger(130, random).toString(32);
		rememberedUsers.put(username, randomId);
		return randomId;
	}
	
	public static String getRememberedUser(String id) {
		return rememberedUsers.get(id);
	}
	
	public static void removeRememberedUser(String id) {
		rememberedUsers.remove(id);
	}
}
