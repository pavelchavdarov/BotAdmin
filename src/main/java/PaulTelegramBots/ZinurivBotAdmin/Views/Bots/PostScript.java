package PaulTelegramBots.ZinurivBotAdmin.Views.Bots;

import java.util.List;

import com.vaadin.external.org.slf4j.helpers.Util;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import PaulTelegramBots.ZinurivBotAdmin.Models.Post;
import PaulTelegramBots.ZinurivBotAdmin.Services.PostService;

public class PostScript extends CustomComponent {
	
	private PostService service = PostService.getInstance();
    private Grid<Post> grid = new Grid<>(Post.class);
    private PostForm form = new PostForm(this);
    
    private String botUserName;
    
    //private PopupView popupForm;
    private Window postForm;
    
	public PostScript(String userName) {
		Button addMessage = new Button("Добавить сообщение");
    	addMessage.addClickListener(e ->{
    		grid.asSingleSelect().clear();
    		form.setMessage(new Post());
    		//popupForm.setPopupVisible(true);
    		UI.getCurrent().addWindow(postForm);
    	});
    	setBotUserName(userName);
    	
    	Label label = new Label("<H1>Программа рассылки</H1>", ContentMode.HTML);
    	label.setSizeUndefined();
    	HorizontalLayout labelLayout = new HorizontalLayout(label);
    	labelLayout.setSizeFull();
    	labelLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    	
    	//popupForm = new PopupView(null, form);
    	postForm = new Window("Редактироание сообщения", form);
    	postForm.setResizable(false);
    	postForm.setModal(true);
    	postForm.setDraggable(true);
    	//popupForm.setHideOnMouseOut(false);
    	HorizontalLayout toolbar = new HorizontalLayout(addMessage);
    	
    	grid.setColumns("dayDelay", "message");
    	grid.getDefaultHeaderRow().getCell("dayDelay").setText("Интервал");
    	grid.getDefaultHeaderRow().getCell("message").setText("Сообщение");
    	grid.getColumn("dayDelay").setExpandRatio(1);
    	grid.getColumn("message").setExpandRatio(4);

    	HorizontalLayout main = new HorizontalLayout(grid);
    	main.setSizeFull();
    	grid.setSizeFull();
    	main.setExpandRatio(grid, 1f);
    	
    	grid.asSingleSelect().addValueChangeListener(Event -> {
    		if(Event.getValue() == null) {
    			//popupForm.setPopupVisible(false);
    			postForm.close();
    		}
    		else {
    			form.setMessage(Event.getValue());
    			//popupForm.setPopupVisible(true);
    			UI.getCurrent().addWindow(postForm);
    		}
    	});
        // add Grid to the layout
    	VerticalLayout layout = new VerticalLayout();
//    	layout.setSizeFull();
    	
        //layout.addComponents(labelLayout, popupForm, toolbar, main);
        layout.addComponents(labelLayout, toolbar, main);
        //layout.setComponentAlignment(popupForm, Alignment.MIDDLE_CENTER);
        setCompositionRoot(layout);
        
     // fetch list of Customers from service and assign it to Grid
        updateList();
        setSizeFull();
	}
	
	public void updateList() {
    	List<Post> customers = service.findAll(getBotUserName());
        grid.setItems(customers);

    }

	public String getBotUserName() {
		return botUserName;
	}

	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
	}
}
