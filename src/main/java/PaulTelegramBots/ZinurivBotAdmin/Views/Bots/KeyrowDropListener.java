package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.awt.Component;
import java.util.Optional;

import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.event.DropEvent;
import com.vaadin.ui.dnd.event.DropListener;

import PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard.MessageButton;

public class KeyrowDropListener implements DropListener<HorizontalLayout> {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout keysRow;
	
	public KeyrowDropListener(HorizontalLayout row) {
		super();
		keysRow = row;
	}
	
	@Override
	public void drop(DropEvent<HorizontalLayout> DropEvent) {
		Optional<AbstractComponent> dragSource = DropEvent.getDragSourceComponent();
		if (dragSource.isPresent() && dragSource.get() instanceof Button){
			Button rowButton;
			if(dragSource.get().getParent().getClass() == HorizontalLayout.class) {
				rowButton = (Button) dragSource.get();
				keysRow.addComponent(rowButton);
			}
			else {
				rowButton = new Button(dragSource.get().getCaption());
				rowButton.setData(MessageButton.createButton((MessageButton) dragSource.get().getData()));
				keysRow.addComponent(rowButton);
				rowButton.addContextClickListener(new ButtonContextClickListener());
			}
			
			DragSourceExtension<Button> DragSourceRowButton = new DragSourceExtension<>(rowButton);
			DragSourceRowButton.setEffectAllowed(EffectAllowed.MOVE);
			DragSourceRowButton.addDragEndListener(e -> {
				keysRow.removeComponent(e.getComponent());
				if(keysRow.getComponentCount() == 0) {
					ComponentContainer container = (ComponentContainer) keysRow.getParent();
					container.removeComponent(keysRow);
				}
					
			});
		}
	}	
		

}
