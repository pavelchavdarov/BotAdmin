package PaulTelegramBots.ZinurivBotAdmin.Views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import PaulTelegramBots.ZinurivBotAdmin.MyUI;
import PaulTelegramBots.ZinurivBotAdmin.Models.Registration;
import PaulTelegramBots.ZinurivBotAdmin.Services.ClientRegistrationService;

public class RegistrationView extends CustomComponent implements View {

	private TextField firstName = new TextField("Имя:");
	private TextField lastName = new TextField("Фамилия:");
	private TextField login = new TextField("Логин:");
	private PasswordField password = new PasswordField("Пароль:");
	private PasswordField passComfirm = new PasswordField("Подтверждение:");
	private TextField phone = new TextField("Телефон:");
	private TextField email = new TextField("E-mail:");
	
	private Button registration = new Button("Регистрация");
	Binder<Registration> binder = new Binder<>(Registration.class);
	
	public RegistrationView() {
		FormLayout regFormLayout = new FormLayout();
		setCompositionRoot(regFormLayout);
		regFormLayout.addComponents(firstName, lastName, login, password, passComfirm, phone, email, registration);
		
		registration.addClickListener(e -> registration());
		
		binder.forField(firstName)
		.bind(Registration::getFirstName, Registration::setFirstName);
		
		binder.forField(lastName)
		.bind(Registration::getLastName, Registration::setLastName);
		
		binder.forField(login)
		.bind(Registration::getLogin, Registration::setLogin);
		
		binder.forField(password)
		.bind(Registration::getPassword, Registration::setPassword);
		
		binder.forField(phone)
		.bind(Registration::getPhone, Registration::setPhone);
		
		binder.forField(email)
		.withValidator(new EmailValidator(
			    "Это некорректный адрес e-mail!"))
		.bind(Registration::getEmail, Registration::setEmail);
	}
	
	private void registration() {
		Registration registration = new Registration();
		try {
			binder.writeBean(registration);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(registration.getFirstName());
		System.out.println(registration.getLastName());
		ClientRegistrationService.getInstance().register(registration);
		MyUI ui = (MyUI) UI.getCurrent();
		 ui.getNavigator().navigateTo("login");
		
	}
}
