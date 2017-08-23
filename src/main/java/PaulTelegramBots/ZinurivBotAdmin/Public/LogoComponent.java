package PaulTelegramBots.ZinurivBotAdmin.Public;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LogoComponent extends VerticalLayout {
	private Label pageLogo;
	
	public LogoComponent() {
		pageLogo = new Label("<H1>Zinurov posting service</H1>", ContentMode.HTML);
		pageLogo.setSizeUndefined();
		
		//VerticalLayout layout = new VerticalLayout(pageLogo);
		addComponents(pageLogo);
		setComponentAlignment(pageLogo, Alignment.MIDDLE_CENTER);
		setSizeFull();
		//setCompositionRoot(layout);
		

		

	}
}
