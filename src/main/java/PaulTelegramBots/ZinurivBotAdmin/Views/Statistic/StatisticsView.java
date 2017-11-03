package PaulTelegramBots.ZinurivBotAdmin.Views.Statistic;

import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.BotModel;
import PaulTelegramBots.ZinurivBotAdmin.Services.AuthService;
import PaulTelegramBots.ZinurivBotAdmin.Services.BotsService;
import PaulTelegramBots.ZinurivBotAdmin.Views.Statistic.BotStatisticView;

public class StatisticsView extends VerticalLayout {
	private VerticalLayout main;
	private TabSheet tabSheet;
	
	public StatisticsView() {
		tabSheet = new TabSheet();
		addComponent(tabSheet);
		
		List<BotModel> botList = BotsService.getMyBots(AuthService.getAuthenticatedUserName());
		System.out.println("Bots: " +  botList.size());
		for(BotModel bot: botList) {
			//tabSheet.addTab(new BotDetailsView(bot.getUserName()), bot.getUserName(), VaadinIcons.AUTOMATION);
			tabSheet.addTab(new BotStatisticView(bot.getUserName()), bot.getUserName(), VaadinIcons.AUTOMATION);
		}

		tabSheet.addSelectedTabChangeListener((event) -> {
			TabSheet tabSheet = event.getTabSheet();
			Layout tab = (Layout) tabSheet.getSelectedTab();
			tab.removeAllComponents();
			tab.addComponent(new BotStatisticView(tabSheet.getTab(tab).getCaption()));
		});
	}
}
