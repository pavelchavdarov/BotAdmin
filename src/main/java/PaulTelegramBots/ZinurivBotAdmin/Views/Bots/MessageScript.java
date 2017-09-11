package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.MyUI;
import PaulTelegramBots.ZinurivBotAdmin.Models.Message;
import PaulTelegramBots.ZinurivBotAdmin.Services.MessageService;

public class MessageScript extends CustomComponent{
	
	private MessageService service = MessageService.getInstance();
    private Grid<Message> grid = new Grid<>(Message.class);
    private MessageForm form = new MessageForm(this);
    
    private String botUserName;
    
	public MessageScript(String userName) {
		Button addMessage = new Button("Добавить сообщение");
    	addMessage.addClickListener(e ->{
    		grid.asSingleSelect().clear();
    		form.setMessage(new Message());
    	});
    	setBotUserName(userName);
    	
    	Label label = new Label("<H1>Единоразовая рассылки</H1>", ContentMode.HTML);
    	label.setSizeUndefined();
    	HorizontalLayout labelLayout = new HorizontalLayout(label);
    	labelLayout.setSizeFull();
    	labelLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    	
    	HorizontalLayout toolbar = new HorizontalLayout(addMessage);
    	
    	grid.setColumns("dateToSend", "message");
    	grid.getDefaultHeaderRow().getCell("dateToSend").setText("Дата");
    	grid.getDefaultHeaderRow().getCell("message").setText("Сообщение");
    	grid.getColumn("dateToSend").setExpandRatio(1);
    	grid.getColumn("message").setExpandRatio(8);

    	HorizontalLayout main = new HorizontalLayout(grid, form);
    	main.setSizeFull();
    	grid.setSizeFull();
    	main.setExpandRatio(grid, 2f);
    	main.setExpandRatio(form, 1f);
    	
    	form.setVisible(false);
    	grid.asSingleSelect().addValueChangeListener(Event -> {
    		if(Event.getValue() == null) {
    			form.setVisible(false);
    		}
    		else {
    			form.setMessage(Event.getValue());
    		}
    	});
        // add Grid to the layout
    	VerticalLayout layout = new VerticalLayout();
    	//layout.setSizeFull();
        layout.addComponents(labelLayout, toolbar, main);
        setCompositionRoot(layout);
        //setSizeFull();
     // fetch list of Customers from service and assign it to Grid
        setSizeFull();
        updateList();
	}
	
	public void updateList() {
    	List<Message> customers = service.findAll(getBotUserName());
        grid.setItems(customers);

    }

	public String getBotUserName() {
		return botUserName;
	}

	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
	}
	
}
