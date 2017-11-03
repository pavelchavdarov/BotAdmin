package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;
import PaulTelegramBots.ZinurivBotAdmin.Services.BotsService;

public class BotSettingsView extends CustomComponent implements View {
	String botUserName;
	
	TextField userName;
	Label userNameLabel;
	TextField authorLink;
	
	TextArea helloPhrase;
	TextArea bayPhrase;
	TextArea subscribePhrase;
	TextArea defaultAnswer;
	
	Button save;
	
	Binder<BotModel> binder;
	
	BotModel botModel;
	
	public BotSettingsView(String botusername) {
		botUserName = botusername;
		
		binder = new Binder<>(BotModel.class);

		Label label = new Label("<H1>Настройки</H1>", ContentMode.HTML);
		userName = new TextField("Имя бота:");
		userName.setEnabled(false);
		userName.setDescription("Это название Вашего бота. Сообщите его своим подписчикам");
		userNameLabel = new Label("Это название Вашего бота. Сообщите его своим подписчикам");
		userNameLabel.setPrimaryStyleName("warning");
		//HorizontalLayout userNameLayout = new HorizontalLayout(userName, userNameLabel);
		
		authorLink = new TextField("Ваше имя пользователя в Telegram:");
			
		helloPhrase = new TextArea("Приветственное сообщение:");
		
		bayPhrase = new TextArea("Сообщение при отписке:");
		
		subscribePhrase = new TextArea("Сообщение при подписке:");
		
		defaultAnswer = new TextArea("Ответ по умолчанию:");
		
		save = new Button("Сохранить", VaadinIcons.SAFE);
		save.addClickListener(e -> save());
		
		VerticalLayout main = new VerticalLayout(label, userName, userNameLabel, authorLink, helloPhrase, subscribePhrase, bayPhrase, defaultAnswer, save);
		main.setExpandRatio(helloPhrase, 1);
		main.setExpandRatio(bayPhrase, 1);
		main.setExpandRatio(defaultAnswer, 1);
		setCompositionRoot(main);
		
		main.setWidth("100%");
		userName.setWidth("30%");
		authorLink.setWidth("30%");
		helloPhrase.setWidth("100%");
		subscribePhrase.setWidth("100%");
		bayPhrase.setWidth("100%");
		defaultAnswer.setWidth("100%");
		
		binder.forField(userName).bind( BotModel::getUserName, BotModel::setUserName);
		binder.forField(authorLink).bind(BotModel::getAuthorLink, BotModel::setAuthorLink);
		binder.forField(helloPhrase).bind(BotModel::getHelloPhrase, BotModel::setHelloPhrase);
		binder.forField(subscribePhrase)
		.withConverter(value -> value.isEmpty() ? "" : value,
                value -> value == null ? "" : value.toString())
		.bind(BotModel::getSubscribePhrase, BotModel::setSubscribePhrase);
		binder.forField(bayPhrase).bind(BotModel::getBayPhrase, BotModel::setBayPhrase);
		binder.forField(defaultAnswer).bind(BotModel::getDefaultAnswer, BotModel::setDefaultAnswer);
		
		botModel = BotsService.getBotSettings(botUserName);
		
		binder.readBean(botModel);
	}
	
	private void save() {
		try {
			binder.writeBean(botModel);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		BotsService.saveBotSettings(botModel);
		binder.readBean(botModel);
	}
	
}
