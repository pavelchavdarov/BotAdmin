package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.lang.invoke.LambdaConversionException;
import java.util.Optional;

import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
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
	private Button save = new Button("Сохранить");
	private Button delete = new Button("Удалить");
	private Binder<Post> binder = new Binder<>(Post.class);
	
	private PostService service = PostService.getInstance();
	private Post message;
	private PostScript script;
	
	private VerticalLayout droppableLayout;
	
	private Keyboard keyboard;
	
	public PostForm(PostScript script) {
		this.script = script;
		
		//setSizeUndefined();
		//Label draggableLabel = new Label("You can grab and drag me1");
//		draggableLabel.setValue("Enother value");
/*		draggableLabel.addContextClickListener(new ContextClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void contextClick(ContextClickEvent event) {
				Notification.show("ContextClick on panel in coordinates ("
						+ event.getClientX() + ", " + event.getClientY() + ")");
			}
		});
*/
//		TextField draggableTextField = new TextField();
		Button commandButton = new Button();
		commandButton.setCaption("<Command>");
		commandButton.setData(MessageButton.createButton().setBtnTypes(ButtonTypes.Callback));
		
		Button URLButton = new Button();
		URLButton.setCaption("<URL>");
		URLButton.setData(MessageButton.createButton().setBtnTypes(ButtonTypes.Web));
		
//		draggableTextField.setReadOnly(true);
		//draggableTextField.addBlurListener(e -> draggableTextField.setReadOnly(true));
		//draggableTextField.addContextClickListener(e -> draggableTextField.setReadOnly(false));
		droppableLayout = new VerticalLayout();
		droppableLayout.setSizeUndefined();
		//droppableLayout.setSizeFull();
		Panel keyboardPanel = new Panel(droppableLayout);
		keyboardPanel.setWidth(WIDTH, Unit.PIXELS);
		keyboardPanel.setHeight(HEIGHT/3, Unit.PIXELS);
		
		VerticalLayout dragComponentsLayout = new VerticalLayout();
		dragComponentsLayout.addComponents(commandButton, URLButton);
		Panel dragComponents = new Panel(dragComponentsLayout);
		dragComponentsLayout.setSizeFull();
		dragComponents.setSizeFull();
		
		DragSourceExtension<Button> dragSourceCommandButton = new DragSourceExtension<>(commandButton);
		DragSourceExtension<Button> dragSourceURLButton = new DragSourceExtension<>(URLButton);

		DropTargetExtension<Panel> dropTraget = new DropTargetExtension<>(keyboardPanel);
//		DropTargetExtension<VerticalLayout> dropTragetLayout = new DropTargetExtension<>(dragButtons);
		
		dropTraget.addDropListener( new KeyboardDropListener(droppableLayout));
		/*
		dropTraget.addDropListener(Event -> {
			Optional<AbstractComponent> dSource = Event.getDragSourceComponent();
			if (dSource.isPresent() && dSource.get() instanceof Button){
				Button button = new Button(dSource.get().getCaption());
				button.setData(dSource.get().getData());
				
				HorizontalLayout row = new HorizontalLayout(button);
				row.setWidth("100%");
				DragSourceExtension<Button> DragSourceButton = new DragSourceExtension<>(button);
				DragSourceButton.setEffectAllowed(EffectAllowed.MOVE);
				DragSourceButton.addDragEndListener(e -> {
					row.removeComponent(e.getComponent());
					if(row.getComponentCount() == 0)
						droppableLayout.removeComponent(row);
				});
				
				DropTargetExtension<HorizontalLayout> dropRow = new DropTargetExtension<>(row);
				dropRow.addDropListener(DropEvent -> {
					Optional<AbstractComponent> dragSource = DropEvent.getDragSourceComponent();
					if (dragSource.isPresent() && dragSource.get() instanceof Button){
						Button rowButton;
						if(dragSource.get().getParent().getClass() == HorizontalLayout.class) {
							rowButton = (Button) dragSource.get();
							row.addComponent(rowButton);
						}
						else {
							rowButton = new Button(dSource.get().getCaption());
							rowButton.setData(dSource.get().getData());
							row.addComponent(rowButton);
						}
						DragSourceExtension<Button> DragSourceRowButton = new DragSourceExtension<>(rowButton);
						DragSourceRowButton.setEffectAllowed(EffectAllowed.MOVE);
						DragSourceRowButton.addDragEndListener(e -> {
							row.removeComponent(e.getComponent());
							if(row.getComponentCount() == 0)
								droppableLayout.removeComponent(row);
						});
					}
				});
				droppableLayout.addComponent(row);
			}
		});
		*/
		// set the allowed effect
		dragSourceCommandButton.setEffectAllowed(EffectAllowed.MOVE);
		dragSourceURLButton.setEffectAllowed(EffectAllowed.MOVE);
		dropTraget.setDropEffect(DropEffect.COPY);
		dropTraget.setDropEffect(DropEffect.MOVE);

		// set the text to transfer

		// set other data to transfer (in this case HTML)
		
		VerticalLayout MessageForm = new VerticalLayout(timeShiftField, messageField, keyboardPanel);
		MessageForm.setSizeFull();
		Panel MessagePanel = new Panel(MessageForm);
		MessagePanel.setSizeFull();
		HorizontalLayout hLayout = new HorizontalLayout(MessagePanel, dragComponents);
		hLayout.setExpandRatio(MessagePanel, 1);
		hLayout.setExpandRatio(dragComponents, 3);
		
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		timeShiftField.setWidth("100%");
		addComponents(hLayout, buttons);
		messageField.setHeight(HEIGHT, Unit.PIXELS);
		messageField.setWidth(WIDTH, Unit.PIXELS);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		binder.bindInstanceFields(this);
	}
	
	public void setMessage(Post message) {
		this.message = message;
		binder.setBean(message);
		
		delete.setVisible(message.isPersisted());
		//setVisible(true);
		timeShiftField.selectAll();
		
		droppableLayout.removeAllComponents();
		
	}
	

	private void delete() {
		service.delete(message);
		script.updateList();
	}
	
	private void save() {
		service.save(message, script.getBotUserName());
		script.updateList();
	}

}
