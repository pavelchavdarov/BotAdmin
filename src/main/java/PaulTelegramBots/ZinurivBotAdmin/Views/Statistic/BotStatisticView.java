package PaulTelegramBots.ZinurivBotAdmin.Views.Statistic;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.GroupLayout.Alignment;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatDetailModel;
import PaulTelegramBots.ZinurivBotAdmin.Models.PostStatModel;
import PaulTelegramBots.ZinurivBotAdmin.Services.POstStatService;

public class BotStatisticView extends VerticalLayout {
	private String botUserName;
	private ChartJs chartJs;
	private Grid<PostStatModel> postStatgrid;
	private Grid<PostStatDetailModel> postDetailsGrid; 
	private Grid<PostStatDetailModel> subscribersGrid;
	private VerticalLayout popupContent;
	private PopupView postDetailPopup;
	
	public BotStatisticView(String userName) {
		Label label = new Label("<H1>Статистика</H1>", ContentMode.HTML);
		botUserName = userName;
		Long subscribersAmount = DAO.Statistics.getSubscribersAmount(botUserName);
		Label subsAmountLabel = new Label("Количество подписчиков: " + subscribersAmount);
		Long postsAmount = DAO.Statistics.getTotalPostsSendAmount(botUserName);
		Label postsAmountLabel = new Label("Сделано рассылок: " + postsAmount);
		
		chartJs = SubscribingStatistic.getChart(userName);

		chartJs.setWidth("100%");
		
		subscribersGrid = new Grid<PostStatDetailModel>(PostStatDetailModel.class);
		subscribersGrid.getDefaultHeaderRow().getCell("postHeader").setText("Подписчик");
		subscribersGrid.getDefaultHeaderRow().getCell("sendTime").setText("Дата подписки");
		subscribersGrid.getColumn("postHeader").setExpandRatio(4);
		subscribersGrid.getColumn("sendTime").setExpandRatio(1);
		subscribersGrid.setWidth("100%");
		subscribersGrid.setCaption("Подписчики");
		
		HorizontalLayout postsLayout  = new HorizontalLayout(chartJs, subscribersGrid);
		postsLayout.setWidth("100%");
		postsLayout.setExpandRatio(chartJs, 1);
		postsLayout.setExpandRatio(subscribersGrid, 1);
		
		postStatgrid = new Grid<>(PostStatModel.class);
		postStatgrid.setColumns("subscriberName", "postsAmount");
    	postStatgrid.getDefaultHeaderRow().getCell("subscriberName").setText("Подписчик");
    	postStatgrid.getDefaultHeaderRow().getCell("postsAmount").setText("Получено");
    	postStatgrid.getColumn("postsAmount").setExpandRatio(1);
    	postStatgrid.getColumn("subscriberName").setExpandRatio(4);
		postStatgrid.setWidth("100%");
		postStatgrid.setCaption("Рассылки");
		
		postDetailsGrid = new Grid<PostStatDetailModel>(PostStatDetailModel.class);
		postDetailsGrid.getDefaultHeaderRow().getCell("postHeader").setText("Сообщение");
		postDetailsGrid.getDefaultHeaderRow().getCell("sendTime").setText("Дата отправки");
		postDetailsGrid.getColumn("postHeader").setExpandRatio(1);
		postDetailsGrid.getColumn("sendTime").setExpandRatio(2);
		postDetailsGrid.setWidth("50%");
		postDetailsGrid.setCaption("Детализация");
		VerticalLayout fl = new VerticalLayout(new HorizontalLayout(postDetailsGrid));

		fl.setWidth("50%");
		postDetailPopup = new PopupView(null, fl);
		postDetailPopup.setHideOnMouseOut(false);
		
//		HorizontalLayout postsStatLayout = new HorizontalLayout(postStatgrid);
//		postsStatLayout.setWidth("100%");
//		postsStatLayout.setExpandRatio(postStatgrid, 2);
//		postsStatLayout.setExpandRatio(postDetailsGrid, 5);
		//postsLayout.setWidth("100%");
//		popupContent = new VerticalLayout(postDetailsGrid);
		
//		postDetailPopup = new PopupView("123123123", popupContent);
//		postDetailPopup.setHideOnMouseOut(false);
		
		postStatgrid.asSingleSelect().addValueChangeListener(
				Event -> {
					if(Event.getValue() != null) {
						postDetailsGrid.setItems(POstStatService.getpostStatDetails(botUserName, Event.getValue().getCharId()));
						postDetailPopup.setWidth("100%");
						postDetailPopup.setPopupVisible(true);
					}
				}
				);
		//grid.setItems(POstStatService.getPostStat(userName));
		addComponents(label, subsAmountLabel, postsLayout, postsAmountLabel, postDetailPopup, postStatgrid);		
		
//		PopupView subscriberList = new PopupView(subsAmountLabel, );
		refresh();
	}
	
	public void refresh() {
		//chartJs = SubscribingStatistic.getChart(botUserName);
		//hartJs.setWidth("50%");
		chartJs.refreshData();
		postStatgrid.setItems(POstStatService.getPostStat(botUserName));
		subscribersGrid.setItems(POstStatService.getSubscribers(botUserName));
//		HashMap<Long, PostStatModel> postsMap = DAO.Statistics.getPostsStat(botUserName);
//		for(Long key : postsMap.keySet()) {
			
		}
	
}
