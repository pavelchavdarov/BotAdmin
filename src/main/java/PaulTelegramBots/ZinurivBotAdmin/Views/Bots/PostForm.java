package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

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

import PaulTelegramBots.ZinurivBotAdmin.Models.Post;
import PaulTelegramBots.ZinurivBotAdmin.Services.PostService;



public class PostForm extends VerticalLayout {
	@PropertyId("dayDelay")
	private TextField timeShiftField = new TextField("Интервал");
	@PropertyId("message")
	private TextArea messageField = new TextArea("Сообщение");
	private Button save = new Button("Сохранить");
	private Button delete = new Button("Удалить");
	private Binder<Post> binder = new Binder<>(Post.class);
	
	private PostService service = PostService.getInstance();
	private Post message;
	private PostScript script;
	
	public PostForm(PostScript script) {
		this.script = script;
		
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
	
	public void setMessage(Post message) {
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
		service.save(message, script.getBotUserName());
		script.updateList();
		setVisible(false);
	}

}
