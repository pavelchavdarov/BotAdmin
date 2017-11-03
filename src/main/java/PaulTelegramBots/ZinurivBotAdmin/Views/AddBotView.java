package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;
import PaulTelegramBots.ZinurivBotAdmin.Services.BotsService;

public class AddBotView extends CustomComponent {
	TextField token;
	TextField userName;
	Label userNameLabel;
	TextField authorLink;
	
	TextArea helloPhrase;
	TextArea bayPhrase;
	TextArea subscribePhrase;
	TextArea defaultAnswer;
	
	Button create;
	
	Binder<BotModel> binder;
	
	BotModel botModel;
	
	public AddBotView() {
		binder = new Binder<>(BotModel.class);
		
		token = new TextField("Токкен бота");
		
		userName = new TextField("Имя бота:");
		userName.setDescription("Это название Вашего бота. Сообщите его своим подписчикам");
		userNameLabel = new Label("!Это название Вашего бота. Сообщите его своим подписчикам");
		//HorizontalLayout userNameLayout = new HorizontalLayout(userName, userNameLabel);
		
		authorLink = new TextField("Связь с автором (ссылка):");
		
		helloPhrase = new TextArea("Приветственное сообщение:");
		
		bayPhrase = new TextArea("Сообщение при отписке:");
		
		subscribePhrase = new TextArea("Сообщение при подписке:");
		
		defaultAnswer = new TextArea("Ответ по умолчанию:");
		
		create = new Button("Создать", VaadinIcons.SAFE);
		create.addClickListener(e -> create());
		
		VerticalLayout main = new VerticalLayout(token, userName, userNameLabel, authorLink, helloPhrase, subscribePhrase, bayPhrase, defaultAnswer, create);
		main.setExpandRatio(helloPhrase, 1);
		main.setExpandRatio(bayPhrase, 1);
		main.setExpandRatio(defaultAnswer, 1);
		setCompositionRoot(main);
		
		main.setWidth("100%");
		token.setWidth("30%");
		userName.setWidth("30%");
		authorLink.setWidth("30%");
		helloPhrase.setWidth("100%");
		subscribePhrase.setWidth("100%");
		bayPhrase.setWidth("100%");
		defaultAnswer.setWidth("100%");
		
		binder.forField(token).bind(BotModel::getToken, BotModel::setToken);
		binder.forField(userName).bind( BotModel::getUserName, BotModel::setUserName);
		binder.forField(authorLink).bind(BotModel::getAuthorLink, BotModel::setAuthorLink);
		binder.forField(helloPhrase).bind(BotModel::getHelloPhrase, BotModel::setHelloPhrase);
		binder.forField(subscribePhrase).bind(BotModel::getSubscribePhrase, BotModel::setSubscribePhrase);
		binder.forField(bayPhrase).bind(BotModel::getBayPhrase, BotModel::setBayPhrase);
		binder.forField(defaultAnswer).bind(BotModel::getDefaultAnswer, BotModel::setDefaultAnswer);
		
		botModel = new BotModel();
	}
	
	private void create() {
		try {
			binder.writeBean(botModel);
			BotsService.createNewBot(botModel);
			binder.readBean(new BotModel());
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		
		
	}
}
