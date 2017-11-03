package PaulTelegramBots.ZinurivBotAdmin.Models.MessageKeyboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    @SerializedName("keyboard")
    private List<MessageButton> buttonList;
    @SerializedName("type")
    private KeyboardTypes keyboardType;

    public Keyboard( KeyboardTypes type) {
        setKeyboard(new ArrayList<>());
        setKeyboardType(type);
    }


    public List<MessageButton> getKeyboard() {
        return buttonList;
    }

    public void setKeyboard(List<MessageButton> keyboard) {
        this.buttonList = keyboard;
    }

    public KeyboardTypes getKeyboardType() {
        return keyboardType;
    }

    public void setKeyboardType(KeyboardTypes keyboardType) {
        this.keyboardType = keyboardType;
    }
}
