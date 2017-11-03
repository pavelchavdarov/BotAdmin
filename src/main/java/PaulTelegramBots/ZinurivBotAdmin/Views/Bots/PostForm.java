package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.awt.Component;
import java.lang.invoke.LambdaConversionException;
import java.util.HashMap;
import java.util.Optional;

import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.DropEvent;
import com.vaadin.ui.themes.ValoTheme;

import PaulTelegramBots.ZinurivBotAdmin.Models.Post;
import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.ButtonTypes;
import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.Keyboard;
import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.KeyboardTypes;
import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.MessageButton;
import PaulTelegramBots.ZinurivBotAdmin.Services.PostService;
import javassist.expr.NewArray;

import com.vaadin.event.ContextClickEvent;



public class PostForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	
	@PropertyId("dayDelay")
	private TextField timeShiftField = new TextField("Отправить через (дни):");
	@PropertyId("message")
	private TextArea messageField = new TextArea("Сообщение:");
	private Keyboard keyboard;
	
	private Button save = new Button("Сохранить");
	private Button delete = new Button("Удалить");
	private Button cancel = new Button("Отменить");
	
	private Binder<Post> binder = new Binder<>(Post.class);
	
	private PostService service = PostService.getInstance();
	private Post message;
	private PostScript script;
	
	private VerticalLayout keyboardLayout;
	
	private HashMap<String, AbstractComponent> form;
	
	
	
	public PostForm(PostScript script) {
		this.script = script;
		messageField = new TextArea("Сообщение:");
		
		form = new HashMap<>();
		
		//Vertical divider
        VerticalLayout vDivider = new VerticalLayout();
        vDivider.setWidth(2,Unit.PIXELS); //I need 2px separator 
        vDivider.setHeight(100,Unit.PERCENTAGE);
        vDivider.addStyleName("color_ligth"); //Ligth color for separator
        vDivider.setSpacing(false);
        vDivider.setMargin(false);
		
        TextArea messageText = new TextArea();
		messageText.setValue("Текстовое сообщение");
		form.put("messageText", messageText);
		
		Button commandButton = new Button();
		commandButton.setCaption("<Command>");
		commandButton.setData(MessageButton.createButton().setBtnTypes(ButtonTypes.Callback));
		DragSourceExtension<Button> dragSourceCommandButton = new DragSourceExtension<>(commandButton);
		dragSourceCommandButton.setEffectAllowed(EffectAllowed.MOVE);
		form.put("commandButton", commandButton);
		
		Button URLButton = new Button();
		URLButton.setCaption("<URL>");
		URLButton.setData(MessageButton.createButton().setBtnTypes(ButtonTypes.Web));
		DragSourceExtension<Button> dragSourceURLButton = new DragSourceExtension<>(URLButton);
		dragSourceURLButton.setEffectAllowed(EffectAllowed.MOVE);
		form.put("URLButton", URLButton);
		
		Image image = new Image("Изображение");
		image.setIcon(VaadinIcons.PALETE);
		form.put("image", image);
		
		VerticalLayout dragComponentsLayout = new VerticalLayout();
		dragComponentsLayout.addComponents(	//new Label("Тело:"),
											messageText, 
											image, 
											//vDivider,
											//new Label("Кнопки:"),
											commandButton, 
											URLButton);
		Panel dragComponentsPanel = new Panel(dragComponentsLayout);
		dragComponentsPanel.setCaption("Элементы сообщения:");
		dragComponentsPanel.setIcon(VaadinIcons.AUTOMATION);
		dragComponentsPanel.setPrimaryStyleName(ValoTheme.PANEL_WELL);
		
		dragComponentsLayout.setSizeFull();
		dragComponentsPanel.setSizeFull();
		
		Panel componentPropertiesPanel = new Panel();
		componentPropertiesPanel.setCaption("Свойства элемента");
		componentPropertiesPanel.setIcon(VaadinIcons.CUBES);
		componentPropertiesPanel.setPrimaryStyleName(ValoTheme.PANEL_WELL);
		form.put("properties", componentPropertiesPanel);
		
		VerticalLayout constructorLayout = new VerticalLayout(dragComponentsPanel, componentPropertiesPanel);
		Panel constructorPanel = new Panel(constructorLayout);
		//constructorLayout.setExpandRatio(dragComponentsPanel, 1);
//#########################
		
		keyboardLayout = new VerticalLayout();
		keyboardLayout.setSizeUndefined();
		Panel keyboardPanel = new Panel(keyboardLayout);
		keyboardPanel.setCaption("Кнопки в сообщении");
		keyboardPanel.setIcon(VaadinIcons.KEYBOARD);
		keyboardPanel.setPrimaryStyleName(ValoTheme.PANEL_WELL);
		keyboardPanel.setWidth(WIDTH, Unit.PIXELS);
		keyboardPanel.setHeight(HEIGHT/2, Unit.PIXELS);
		
		DropTargetExtension<Panel> dropTragetKeyboardPanel = new DropTargetExtension<>(keyboardPanel);
		dropTragetKeyboardPanel.setDropEffect(DropEffect.MOVE);
		dropTragetKeyboardPanel.addDropListener( new KeyboardDropListener(keyboardLayout));
		
		VerticalLayout MessageForm = new VerticalLayout(timeShiftField, messageField, keyboardPanel);
		MessageForm.setSizeFull();
		MessageForm.setExpandRatio(timeShiftField, 1);
		MessageForm.setExpandRatio(messageField, 3);
		MessageForm.setExpandRatio(keyboardPanel, 2);
		Panel MessagePanel = new Panel(MessageForm);
		
		HorizontalLayout hLayout = new HorizontalLayout(MessagePanel, constructorPanel);
		hLayout.setExpandRatio(MessagePanel, 3);
		hLayout.setExpandRatio(constructorPanel, 1);
		
		HorizontalLayout buttons = new HorizontalLayout(save, delete, cancel);
		timeShiftField.setWidth("100%");
		addComponents(hLayout, buttons);
		messageField.setHeight(HEIGHT, Unit.PIXELS);
		messageField.setWidth(WIDTH, Unit.PIXELS);

		save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addClickListener(e -> save());
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
		delete.addClickListener(e -> delete());
		cancel.setStyleName(ValoTheme.BUTTON_QUIET);
		cancel.addClickListener(e -> cancel());
		
		binder.bindInstanceFields(this);
	}
	
	public void setMessage(Post message) {
		this.message = message;
		binder.setBean(message);
		
		delete.setVisible(message.isPersisted());
		//setVisible(true);
		timeShiftField.selectAll();
		
		keyboardLayout.removeAllComponents();
		
	}
	

	private void delete() {
		service.delete(message);
		script.updateList();
	}
	
	private void save() {
		service.save(message, script.getBotUserName());
		script.updateList();
	}
	
	private void cancel() {
		script.updateList();
	}

}
