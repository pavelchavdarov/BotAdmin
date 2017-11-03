package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.ButtonTypes;
import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.MessageButton;

public class ButtonEditForm extends VerticalLayout {
	private TextField caption;
	private TextField value;
	private ComboBox<ButtonTypes> type;
	private Button save;
	private Button cancel;
	
	public ButtonEditForm(Button button, Window wnd) {
		MessageButton btn = (MessageButton) button.getData();
		caption = new TextField("Надпись");
		if (btn.getCaption() != null)
			caption.setValue(btn.getCaption());
		
		value = new TextField();
		if (btn.getBtnType() == ButtonTypes.Web)
			value.setCaption("URL-адрес");
		else if (btn.getBtnType() == ButtonTypes.Callback) 
			value.setCaption("Команда");
		if (btn.getValue() != null)
			value.setValue(btn.getValue());

		List<ButtonTypes> typesList = new ArrayList<>();
		typesList.add(ButtonTypes.Callback);
		typesList.add(ButtonTypes.Web);
		type = new ComboBox<>("Тип");
		type.setItems(typesList);
		if (btn.getBtnType() != null)
			type.setSelectedItem(btn.getBtnType());
		
		save = new Button("Сохранить");
		save.addClickListener(e -> {
			btn.setCaption(caption.getValue());
			btn.setValue(value.getValue());
			btn.setBtnTypes(type.getSelectedItem().get());
			button.setCaption(btn.getCaption());
			wnd.close();
			UI.getCurrent().removeWindow(wnd);
		});
		save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		cancel = new Button("Отмена");
		cancel.addClickListener(e -> {
			wnd.close();
			UI.getCurrent().removeWindow(wnd);
		});
		cancel.setStyleName(ValoTheme.BUTTON_QUIET);
		
		HorizontalLayout buttons = new HorizontalLayout(save, cancel);
		
		addComponents(caption, value, type, buttons);
	}
}
