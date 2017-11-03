package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.util.Optional;

import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.DropEvent;
import com.vaadin.ui.dnd.event.DropListener;

import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.MessageButton;

public class KeyboardDropListener implements DropListener<Panel> {

	private static final long serialVersionUID = 1L;
	private VerticalLayout droppableLayout;
	
	public KeyboardDropListener(VerticalLayout layout) {
		super();
		droppableLayout = layout;
	}
	
	public void drop(DropEvent<Panel> Event) {
		Optional<AbstractComponent> dSource = Event.getDragSourceComponent();
		if (dSource.isPresent() && dSource.get() instanceof Button){
			Button button = new Button(dSource.get().getCaption());
			button.setData(MessageButton.createButton((MessageButton) dSource.get().getData()));
			button.addContextClickListener(new ButtonContextClickListener());
			
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
			dropRow.addDropListener(new KeyrowDropListener(row));
			droppableLayout.addComponent(row);
		}
	}

}
