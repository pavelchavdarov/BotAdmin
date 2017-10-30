package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ui.NotificationRole;
import com.vaadin.ui.themes.ValoTheme;

import PaulTelegramBots.ZinurivBotAdmin.MyUI;
import PaulTelegramBots.ZinurivBotAdmin.Services.AuthService;

import com.vaadin.ui.*;

public class LoginComponent extends VerticalLayout implements View {
	
	private TextField username;
	private PasswordField password;
	private CheckBox rememberMe;
	private Button button;
	 
	
	public LoginComponent() {
		username = new TextField("Пользователь:");
		username.setIcon(VaadinIcons.USER);
		
		password = new PasswordField("Пароль:");
		password.setIcon(VaadinIcons.KEY);
		
		rememberMe = new CheckBox("Запомнить");
		
		button = new Button("Войти", e -> onLogin(username.getValue(), password.getValue(), rememberMe.getValue()));
		button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		Button registration = new Button("Регистрация", e -> onRegistration());		
		registration.setStyleName(ValoTheme.BUTTON_LINK);
		
		FormLayout formLayout = new FormLayout(username, password,rememberMe, new HorizontalLayout(button, registration));
		formLayout.setSizeUndefined();
		//VerticalLayout layout = new VerticalLayout(formLayout);
		addComponents(formLayout);
		
		setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		setSizeFull();
		//setCompositionRoot(layout);
		

		
		/*
		setFirstComponent(LogoLayout);
		setSecondComponent(loginLayout);
		setSplitPosition(25, Unit.PERCENTAGE);
		setLocked(true);
		setSizeFull();
		*/
	}
	
	private void onLogin(String username, String password, boolean rememberMe) {
		if(AuthService.login(username, password, rememberMe)) {
			MyUI ui = (MyUI) UI.getCurrent();
			 ui.getNavigator().navigateTo("account");
		}
		else {
			Notification.show("Введена неверная комбинация логин/пароль", Notification.Type.ERROR_MESSAGE);
		}
	}
	
	private void onRegistration() {
		MyUI ui = (MyUI) UI.getCurrent();
		 ui.getNavigator().navigateTo("registration");
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		View.super.enter(event);
		System.out.println("Аутентификация");
	}
	
}
