package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.MyUI;

public class MainPageView extends CustomComponent implements View {
	
	
	public MainPageView() {
		Label label = new Label("Главная страница");
		VerticalLayout layout = new VerticalLayout(label);
		label.setSizeFull();
		setCompositionRoot(layout);
		setSizeFull();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		View.super.enter(event);
		System.out.println("Главная страница");
		MyUI myUI = (MyUI)UI.getCurrent();
		myUI.getPanelDisplay().setCaption("Главная страница");
	}
}
