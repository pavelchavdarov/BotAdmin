package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DummyView extends CustomComponent implements View {
	public DummyView() {
		Label label = new Label("Раздел находится в разработке");
		VerticalLayout layout = new VerticalLayout(label);
		label.setSizeFull();
		setCompositionRoot(layout);
		setSizeFull();
	}
	
	
}
