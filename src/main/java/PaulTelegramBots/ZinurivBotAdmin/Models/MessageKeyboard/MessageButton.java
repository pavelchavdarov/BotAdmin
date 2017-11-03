package PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard;

import javafx.scene.control.ButtonType;

public class MessageButton {
    private String caption;
    private ButtonTypes btnType;
    private String value;

    public static MessageButton createButton() {
        return new MessageButton();
    }

    public static MessageButton createButton(MessageButton button4copy) {
    	MessageButton button = new MessageButton();
    	button.setBtnTypes(button4copy.getBtnType());
    	button.setCaption(button4copy.getCaption());
    	button.setValue(button4copy.getValue());
    	return button;
    	
    }
    
    public String getCaption() {
        return caption;
    }

    public MessageButton setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public ButtonTypes getBtnType() {
        return btnType;
    }

    public MessageButton setBtnTypes(ButtonTypes btnType) {
        this.btnType = btnType;
        return this;
    }

    public String getValue() {
        return value;
    }

    public MessageButton setValue(String value) {
        this.value = value;
        return this;
    }

    private MessageButton() {
    }
}
