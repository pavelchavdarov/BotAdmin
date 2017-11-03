package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;
import PaulTelegramBots.ZinurivBotAdmin.Services.AuthService;
import PaulTelegramBots.ZinurivBotAdmin.Services.BotsService;
import PaulTelegramBots.ZinurivBotAdmin.Views.AddBotView;

public class BotsView extends VerticalLayout /*implements View*/ {
	//private HorizontalLayout toolbar;// = new HorizontalLayout();
	private VerticalLayout main;
	
	//private Button addBot;// = new Button(VaadinIcons.PLUS_CIRCLE);
	private TabSheet tabSheet;
	
	private BotsService botsService;
	private AuthService authServcse; 
	
	public BotsView() {
		//addBot = new Button("Добавить бота", VaadinIcons.PLUS_CIRCLE_O);
		//toolbar = new HorizontalLayout(addBot);
		//toolbar.setWidth("100%");
		tabSheet = new TabSheet();
		//main = new VerticalLayout(tabSheet);
		addComponent(tabSheet);
		
		//setCompositionRoot(main);

		
		List<BotModel> botList = BotsService.getMyBots(AuthService.getAuthenticatedUserName());
		System.out.println("Bots: " +  botList.size());
		for(BotModel bot: botList) {
			//tabSheet.addTab(new BotDetailsView(bot.getUserName()), bot.getUserName(), VaadinIcons.AUTOMATION);
			tabSheet.addTab(new BotDetailsView(bot.getUserName()), bot.getUserName(), VaadinIcons.AUTOMATION);
		}
		
		tabSheet.addTab(new AddBotView(), "Добавить бота", VaadinIcons.PLUS_CIRCLE);
		tabSheet.addSelectedTabChangeListener((event) -> {
			TabSheet tabSheet = event.getTabSheet();
			
			if (tabSheet.getSelectedTab().getClass() != AddBotView.class) {
				Layout tab = (Layout) tabSheet.getSelectedTab();
				tab.removeAllComponents();
				tab.addComponent(new BotDetailsView(tabSheet.getTab(tab).getCaption()));
			}
		});
		/*
		tabSheet.addSelectedTabChangeListener((event) -> {
			TabSheet tabSheet = event.getTabSheet();
			Layout tab = (Layout) tabSheet.getSelectedTab();
			if (tab.getClass() == AddBotLayout.class) {
				
			}
		});
		*/
	}
	/*
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		View.super.enter(event);
		if(!AuthService.isAuthenticated()) {
			UI.getCurrent().getNavigator().navigateTo("login");
		}
		System.out.println("Боты");

	}
	*/
	
}
