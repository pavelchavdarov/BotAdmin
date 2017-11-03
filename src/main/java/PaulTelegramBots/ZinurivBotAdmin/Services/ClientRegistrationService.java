package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bouncycastle.crypto.generators.SCrypt;

import DAO.HikariCP;
import PaulTelegramBots.ZinurivBotAdmin.Models.Registration;

public class ClientRegistrationService {
	private static ClientRegistrationService registrationService;
	
	private ClientRegistrationService() {
	}
	
	public static ClientRegistrationService getInstance() {
		if(registrationService == null) {
			registrationService = new ClientRegistrationService();
		}
		return registrationService;
	}
	
	public synchronized void register(Registration reg) {
		SCrypt sCrypt = new SCrypt();
		String sCryptHash = new String(sCrypt.generate(reg.getPassword().getBytes(), reg.getLogin().getBytes(), 2, 2, 2, 32), StandardCharsets.UTF_8);
		DAO.Auth.CreateClient(reg.getFirstName(), reg.getLastName(), reg.getLogin(), sCryptHash, reg.getPhone(), reg.getEmail());
		
	}
}
