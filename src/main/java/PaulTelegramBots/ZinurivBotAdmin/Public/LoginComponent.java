package PaulTelegramBots.ZinurivBotAdmin.Public;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ui.NotificationRole;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.MyUI;

import com.vaadin.ui.*;

public class LoginComponent extends VerticalLayout {
	
	private TextField username;
	private PasswordField password;
	private CheckBox rememberMe;
	private Button button;
	 
	
	public LoginComponent() {
		username = new TextField("Пользователь:");
		password = new PasswordField("Пароль:");
		rememberMe = new CheckBox("Запомнить");
		button = new Button("Войти", e -> onLogin(username.getValue(), password.getValue(), rememberMe.getValue()));
		button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
				
		FormLayout formLayout = new FormLayout(username, password,rememberMe, button);
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
			 ui.showPersonalAccount();
		}
		else {
			Notification.show("Введена неверная комбинация логин/пароль", Notification.Type.ERROR_MESSAGE);
		}
	}
	
}
