package PaulTelegramBots.ZinurivBotAdmin.Services;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

public class AuthService {
	private static final String COOKIE_NAME = "remember-me";
	private static final String SESSION_USERNAME = "username";
	
	public static boolean isAuthenticated() {
		return VaadinSession.getCurrent().getAttribute(SESSION_USERNAME) != null || loginRememberedUser(); 
	}
	
	public static boolean login(String username, String password, boolean rememberMe) {
		if(UserService.isAuthenticUser(username, password)) {
			VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
			
			if(rememberMe) {
				rememberUser(username);
			}
			return true;
		}
		return false;
	}
	
	public static void logOut() {
		Optional<Cookie> cookie = getRememberMeCookie();
		if(cookie.isPresent()) {
			String id = cookie.get().getValue();
			UserService.removeRememberedUser(id);
			deletedRememberMeCookie();
		}
		VaadinSession.getCurrent().close();
		Page.getCurrent().setLocation("");
	}
	
	public static Optional<Cookie> getRememberMeCookie() {
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		
		return Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst();
	}
	
	private static boolean loginRememberedUser() {
		Optional<Cookie> rememberMeCookie = getRememberMeCookie();
		if(rememberMeCookie.isPresent()) {
			String id = rememberMeCookie.get().getValue();
			String username = UserService.getRememberedUser(id);
			if (username != null) {
				VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
				return true;
			}
		}
		return false;
	}
	
	private static void rememberUser(String username) {
		String id = UserService.rememberUser(username);
		
		Cookie cookie = new Cookie(COOKIE_NAME, id);
		cookie.setPath("/");
		cookie.setMaxAge(60*60); // one hour
		VaadinService.getCurrentResponse().addCookie(cookie);
	}
	
	private static void deletedRememberMeCookie() {
		Cookie cookie = new Cookie(COOKIE_NAME, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		VaadinService.getCurrentResponse().addCookie(cookie);
	}
	
	public static String getAuthenticatedUserName() {
		return String.valueOf(VaadinSession.getCurrent().getAttribute(SESSION_USERNAME));
	}
	
	public static Long getAuthenticatedUserId() {
		return DAO.Info.getClientIdByLogin(getAuthenticatedUserName());
	}
}
