package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.MenuBar.MenuItem;

import PaulTelegramBots.ZinurivBotAdmin.Services.AuthService;
import PaulTelegramBots.ZinurivBotAdmin.Views.Bots.BotsView;
import PaulTelegramBots.ZinurivBotAdmin.Views.Statistic.StatisticsView;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PersonalAccountView extends CustomComponent implements View {
	//BotsView  botsView;
	DummyView dummyView;
	TabSheet tabPanel;
	public PersonalAccountView() {
//		if(!AuthService.isAuthenticated()) {
//			UI.getCurrent().getNavigator().navigateTo("login");
//		}
//		else {
			
			MenuBar menuBar = new MenuBar();
//			MenuItem botsMenu = menuBar.addItem("Боты", null);
//			
//			MenuItem statisticMenu = menuBar.addItem("Статистика", null);
			MenuItem logOutMenu = menuBar.addItem("Выйти", null);
//			
//			
//			Panel panelDisplay = new Panel();
			
			tabPanel = new TabSheet();
			/*
			tabPanel.addTab(new VerticalLayout(), "Боты", VaadinIcons.CUBES);
			tabPanel.addTab(new VerticalLayout(), "Статистика", VaadinIcons.BOOK);
			
			
			tabPanel.addSelectedTabChangeListener((event) -> {
				TabSheet tabSheet = event.getTabSheet();
				Layout tab = (Layout) tabSheet.getSelectedTab();
				tab.removeAllComponents();
				if (tabSheet.getTab(tab).getCaption() == "Боты")
					tab.addComponent(new BotsView());
			});
			tabPanel.setSelectedTab(1);
			*/
//			dummyView = new DummyView();
//			MenuBar.Command botsCommand = new MenuBar.Command() {
//				
//				@Override
//				public void menuSelected(MenuItem selectedItem) {
//					panelDisplay.setContent(new BotsView());
//				}
//			}; 
//			botsMenu.setCommand(botsCommand);
//			
//			MenuBar.Command statisticCommand = new MenuBar.Command() {
//				
//				@Override
//				public void menuSelected(MenuItem selectedItem) {
//					panelDisplay.setContent(dummyView);
//				}
//			}; 
//			statisticMenu.setCommand(statisticCommand);
			
			MenuBar.Command logOutCommand = new MenuBar.Command() {
				
				@Override
				public void menuSelected(MenuItem selectedItem) {
					AuthService.logOut();
					UI.getCurrent().getNavigator().navigateTo("");
				}
			}; 
			logOutMenu.setCommand(logOutCommand);
			
			VerticalLayout verticalLayout = new VerticalLayout(menuBar, tabPanel);
			verticalLayout.setComponentAlignment(menuBar, Alignment.TOP_RIGHT);
			menuBar.setSizeUndefined();
			tabPanel.setWidth("100%");
			verticalLayout.setExpandRatio(tabPanel, 1.0f);
			setCompositionRoot(verticalLayout);
			verticalLayout.setSizeFull();
			setSizeFull();
	//	}

	}	
	
	@Override
	public void enter(ViewChangeEvent event) {
		View.super.enter(event);
		if(!AuthService.isAuthenticated()) {
			UI.getCurrent().getNavigator().navigateTo("login");
		}
		else
			if(tabPanel.getComponentCount() == 0) {
				tabPanel.addTab(new BotsView(), "Боты", VaadinIcons.CUBES);
				//tabPanel.addTab(new StatisticsView(), "Статистика", VaadinIcons.BOOK);
				
				tabPanel.addSelectedTabChangeListener((e) -> {
					TabSheet tabSheet = e.getTabSheet();
					Layout tab = (Layout) tabSheet.getSelectedTab();
					tab.removeAllComponents();
					if (tabSheet.getTab(tab).getCaption() == "Боты")
						tab.addComponent(new BotsView());
//					else if (tabSheet.getTab(tab).getCaption() == "Статистика")
//						tab.addComponent(new StatisticsView());
				});
			}
		
	}
}
