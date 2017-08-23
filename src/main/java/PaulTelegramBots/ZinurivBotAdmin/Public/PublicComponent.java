package PaulTelegramBots.ZinurivBotAdmin.Public;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PublicComponent extends CustomComponent{
	private Panel panelMain;
	private Panel panelDisplay;

	public PublicComponent(Layout main, Layout display) {
		
		panelMain = new Panel(main);
		panelDisplay = new Panel(display);
		panelMain.setSizeFull();
		panelDisplay.setSizeFull();
		panelDisplay.setCaption("Название панели");
		
				
		HorizontalLayout layout = new HorizontalLayout(panelMain, panelDisplay);
		
		layout.setExpandRatio(panelMain, 1f);
		layout.setExpandRatio(panelDisplay, 3f);
		
		setCompositionRoot(layout);
		layout.setSizeFull();
		setSizeFull();

	}
}
