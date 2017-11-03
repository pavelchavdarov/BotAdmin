package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.shared.ui.window.WindowServerRpc;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.MessageButton;

public class ButtonContextClickListener implements ContextClickListener {

	@Override
	public void contextClick(ContextClickEvent event) {
		// TODO Auto-generated method stub
		Button button = (Button) event.getSource();
		//MessageButton messageButton = (MessageButton) button.getData();
		Window window = new Window("Свойства кнопки");
		window.setContent(new ButtonEditForm(button, window));
		window.setDraggable(true);
		window.setSizeUndefined();
		window.setModal(true);
		window.setResizable(false);
		UI.getCurrent().addWindow(window);
		
	}

}
