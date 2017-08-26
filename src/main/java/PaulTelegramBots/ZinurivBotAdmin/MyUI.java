package PaulTelegramBots.ZinurivBotAdmin;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//import PaulTelegramBots.ZinurivBotAdmin.PostScript.Script;
import PaulTelegramBots.ZinurivBotAdmin.Views.MessageScript;
import PaulTelegramBots.ZinurivBotAdmin.Views.PersonalAccountView;
import PaulTelegramBots.ZinurivBotAdmin.Auth.AuthService;
import PaulTelegramBots.ZinurivBotAdmin.Views.LoginComponent;
import PaulTelegramBots.ZinurivBotAdmin.Views.MainPageView;

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
	
	private Navigator navigator;
	Panel panelMain;// = new Panel(main);
	Panel panelDisplay;// = new Panel(display);
	private MenuBar mainMenu;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
		panelDisplay = new Panel();
		panelDisplay.setSizeFull();
		
		setMainMenu(new MenuBar());
		MenuItem mainPageItem = getMainMenu().addItem("Главная", null);
		
		MenuItem personalAccountItem = getMainMenu().addItem("Личный кабинет", null, null);
		
		MenuBar.Command personalAccountCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("account");
			}
		};
		personalAccountItem.setCommand(personalAccountCommand);
		
		MenuBar.Command mainPageCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("");
			}
		};
		mainPageItem.setCommand(mainPageCommand);
		
//		MenuItem botsItem = personalAccountItem.addItem("Боты", null, personalAccountCommand);
//		personalAccountItem.addItem("Настройки", null, personalAccountCommand);
//		personalAccountItem.addItem("Контакты", null, personalAccountCommand);
//		personalAccountItem.addItem("Справка", null, personalAccountCommand);
		
		MenuItem contactsItem = getMainMenu().addItem("Контакты", null);
		MenuItem aboutItem = getMainMenu().addItem("О сервисе", null);
		
		getMainMenu().setSizeUndefined();
		
		VerticalLayout layout = new VerticalLayout(getMainMenu(), panelDisplay);
		
//		layout.setExpandRatio(panelMain, 1f);
		layout.setExpandRatio(panelDisplay, 1f);
		
		layout.setSizeFull();
		
		setContent(layout);
		
		
		navigator = new Navigator(this, panelDisplay);
		navigator.addView("", new MainPageView());
    	navigator.addView("login", new LoginComponent());
    	navigator.addView("account", new PersonalAccountView());
    	
    	/*
    	if(AuthService.isAuthenticated()) {
    		showPersonalAccount();
    	}
    	else {
    		showPublicPage();
    	}
    	*/
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

//    public void showPublicPage() {
//		setContent(new PublicComponent(new LogoComponent(), new LoginComponent()));
//	}
    
//    public void showPersonalAccount() {
//		setContent(new MessageScript());
//	}

	public Navigator getNavifator() {
		return navigator;
	}

	public void setNavifator(Navigator navifator) {
		this.navigator = navifator;
	}

	public MenuBar getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuBar mainMenu) {
		this.mainMenu = mainMenu;
	}
}
