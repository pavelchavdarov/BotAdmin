package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Views.Statistic.BotStatisticView;
import PaulTelegramBots.ZinurivBotAdmin.Views.Statistic.StatisticsView;

public class BotDetailsView extends VerticalLayout /*implements View*/ {
	private String botUserName;
	private Panel panelDisplay;
	private MenuBar menuBar;
	
	private BotSettingsView botSettingsView;
	private PostScript postScript;
	private MessageScript messageScript;
	private BotStatisticView statisticsView;
	
	public BotDetailsView(String userName) {
		botUserName = userName;
		panelDisplay = new Panel();
		panelDisplay.setWidth("100%");
		
		menuBar = new MenuBar();
		
		MenuItem settingsMenu = menuBar.addItem("Настройки", null);
		MenuItem postsMenu = menuBar.addItem("Рассылки", null);
		MenuItem messagesMenu = menuBar.addItem("Отправить сообщение", null);
		MenuItem statisticMenu = menuBar.addItem("Статистика", null);
		
		//VerticalLayout verticalLayout = new VerticalLayout(menuBar, panelDisplay);
		addComponents(menuBar, panelDisplay);
		menuBar.setSizeUndefined();
		panelDisplay.setWidth("100%");
		//verticalLayout.setExpandRatio(panelDisplay, 1.0f);
		setExpandRatio(panelDisplay, 1.0f);
		//setCompositionRoot(verticalLayout);
		//verticalLayout.setSizeFull();
		//setSizeFull();
		
		botSettingsView = new BotSettingsView(botUserName);
		postScript = new PostScript(botUserName);
		messageScript = new MessageScript(botUserName);
		statisticsView = new BotStatisticView(botUserName);
		
		MenuBar.Command postsMenuCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				panelDisplay.setContent(postScript);
				postScript.updateList();
			}
		};
		postsMenu.setCommand(postsMenuCommand);
		
		MenuBar.Command messagesMenuCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				panelDisplay.setContent(messageScript);
				messageScript.updateList();
			}
		};
		messagesMenu.setCommand(messagesMenuCommand);
		
		MenuBar.Command settingsCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				panelDisplay.setContent(botSettingsView);
			}
		}; 
		settingsMenu.setCommand(settingsCommand);
		
		MenuBar.Command statisticCommand = new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
			panelDisplay.setContent(statisticsView);
				
			}
		};
		statisticMenu.setCommand(statisticCommand);
		
		panelDisplay.setContent(botSettingsView);
	}
}
