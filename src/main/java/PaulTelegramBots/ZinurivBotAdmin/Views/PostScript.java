package PaulTelegramBots.ZinurivBotAdmin.Views;

import java.util.List;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import PaulTelegramBots.ZinurivBotAdmin.Models.Post;
import PaulTelegramBots.ZinurivBotAdmin.Services.PostService;

public class PostScript extends CustomComponent {
	
	private PostService service = PostService.getInstance();
    private Grid<Post> grid = new Grid<>(Post.class);
    private PostForm form = new PostForm(this);
    
	public PostScript() {
		Button addMessage = new Button("Добавить сообщение");
    	addMessage.addClickListener(e ->{
    		grid.asSingleSelect().clear();
    		form.setMessage(new Post());
    	});
    	Label label = new Label("<H1>Программа рассылки</H1>", ContentMode.HTML);
    	label.setSizeUndefined();
    	HorizontalLayout labelLayout = new HorizontalLayout(label);
    	labelLayout.setSizeFull();
    	labelLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    	
    	HorizontalLayout menu = new HorizontalLayout(new MenuBar());
    	HorizontalLayout toolbar = new HorizontalLayout(addMessage);
    	
    	grid.setColumns("dayDelay", "message");
    	grid.getDefaultHeaderRow().getCell("dayDelay").setText("Интервал");
    	grid.getDefaultHeaderRow().getCell("message").setText("Сообщение");
    	grid.getColumn("dayDelay").setExpandRatio(1);
    	grid.getColumn("message").setExpandRatio(4);

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
    	layout.setSizeFull();
        layout.addComponents(labelLayout, toolbar, main);
        setCompositionRoot(layout);
        //setSizeFull();
     // fetch list of Customers from service and assign it to Grid
        updateList();
	}
	
	public void updateList() {
    	List<Post> customers = service.findAll();
        grid.setItems(customers);

    }
}