package PaulTelegramBots.ZinurivBotAdmin.MessageScript;

import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;



public class MessageForm extends VerticalLayout {
	@PropertyId("timeshift")
	private TextField timeShiftField = new TextField("Интервал");
	@PropertyId("msg")
	private TextArea messageField = new TextArea("Сообщение");
	private Button save = new Button("Сохранить");
	private Button delete = new Button("Удалить");
	private Binder<Message> binder = new Binder<>(Message.class);
	
	private MessageService service = MessageService.getInstance();
	private Message message;
	private Script script;
	
	public MessageForm(Script cript) {
		this.script = cript;
		
		//setSizeUndefined();
		
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		timeShiftField.setSizeFull();
		addComponents(timeShiftField, messageField, buttons);
		messageField.setSizeFull();
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		binder.bindInstanceFields(this);
	}
	
	public void setMessage(Message message) {
		this.message = message;
		binder.setBean(message);
		
		delete.setVisible(message.isPersisted());
		setVisible(true);
		timeShiftField.selectAll();
		
	}
	

	private void delete() {
		service.delete(message);
		script.updateList();
		setVisible(false);
	}
	
	private void save() {
		System.out.println(message.toString());
		service.save(message);
		script.updateList();
		setVisible(false);
	}

}
