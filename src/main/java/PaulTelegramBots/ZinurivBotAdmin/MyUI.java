package PaulTelegramBots.ZinurivBotAdmin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import PaulTelegramBots.ZinurivBotAdmin.MessageScript.Script;
import PaulTelegramBots.ZinurivBotAdmin.Public.AuthService;
import PaulTelegramBots.ZinurivBotAdmin.Public.LoginComponent;
import PaulTelegramBots.ZinurivBotAdmin.Public.LogoComponent;
import PaulTelegramBots.ZinurivBotAdmin.Public.PublicComponent;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

//	private MessageService service = MessageService.getInstance();
//    private Grid<Message> grid = new Grid<>(Message.class);
//    private MessageForm form = new MessageForm(this);
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	System.out.println("INIT");
    	if(AuthService.isAuthenticated()) {
    		showPersonalAccount();
    	}
    	else {
    		showPublicPage();
    	}
    	setSizeFull();
    	/*
    	final VerticalLayout layout = new VerticalLayout();
    	
    	Button addMessage = new Button("Добавить сообщение");
    	addMessage.addClickListener(e ->{
    		grid.asSingleSelect().clear();
    		form.setMessage(new Message());
    	});
    	
    	HorizontalLayout toolbar = new HorizontalLayout(addMessage);
    	
    	grid.setColumns("timeshift", "msg");
    	grid.getDefaultHeaderRow().getCell("timeshift").setText("Интервал");
    	grid.getDefaultHeaderRow().getCell("msg").setText("Сообщение");

    	HorizontalLayout main = new HorizontalLayout(grid, form);
    	main.setSizeFull();
    	grid.setSizeFull();
    	main.setExpandRatio(grid, 1);
    	
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
        layout.addComponents(toolbar, main);

        // fetch list of Customers from service and assign it to Grid
        updateList();
        
        setContent(layout);
        */
    }
    
/*  
    public void updateList() {
    	List<Message> customers = service.findAll();
        grid.setItems(customers);

    }
*/
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public void showPublicPage() {
		setContent(new PublicComponent(new LogoComponent(), new LoginComponent()));
	}
    
    public void showPersonalAccount() {
		setContent(new Script());
	}
}
