package PaulTelegramBots.ZinurovBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

public class InfoBot extends TelegramLongPollingBot {
    private String BotToken;// = "394479438:AAGgxN1tqUZgrtJVqe619tTetgJh4awZO6w";
    private String BotUsername;// = "ZinurovInfoPosting_bot";
    private BotSession session;
    
    private HashMap<String, String> botSettings;
    //private ReplyKeyboard repKeyboard;
    //private Connection connection;
    public InfoBot() {
        super();
        //repKeyboard = createKeyboard();

        // установка соединения с базой
//        try {
//            connection = HikariCP.getDataSource().getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    
    public InfoBot(HashMap<String, String> settings) {
    	super();
    	botSettings = settings;
    }

    private void addSubscriber(CallbackQuery callbackQuery){
        //PreparedStatement prepStatment;
        Long chatId = callbackQuery.getMessage().getChatId();
        System.out.println("chatId: " + chatId);
        /*
        try {
            prepStatment = connection.prepareStatement("select exists(select true from subscribers  where chat_id = ?) res");
            prepStatment.setLong(1, chatId);
            ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
                if(!(rs.getBoolean("res"))){
                    prepStatment = connection.prepareStatement("INSERT INTO subscribers (chat_id, subscribe_date) VALUES (?, current_date)");
                    prepStatment.setBigDecimal(1, BigDecimal.valueOf(chatId));
                    int res = prepStatment.executeUpdate();
                    System.out.println("Добавлено " + res + " записей");
                }
                else {
                    System.out.println("Собеседник " + chatId + " уже есть в базе");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        DAO.BotFunctions.AddSubscriber(chatId);
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery()
                            .setCallbackQueryId(callbackQuery.getId())
                            .setText("Вы подписаны на инфо-рассылку!")
                            .setShowAlert(true);
        try {
            this.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        sendInlineMessageToChat("Пока сюда будет валиться всякая тестовая байда\nпо расписанию", chatId);

    }

    private void addSubscriber(Message pMessage){
        //PreparedStatement prepStatment;
        long chatId = pMessage.getChatId();
        System.out.println("chatId: " + chatId);
        /*
        try {
            prepStatment = connection.prepareStatement("select exists(select true from subscribers  where chat_id = ?) res");
            prepStatment.setLong(1, chatId);
            ResultSet rs = prepStatment.executeQuery();
            while(rs.next()){
                if(!(rs.getBoolean("res"))){
                    prepStatment = connection.prepareStatement("INSERT INTO subscribers (chat_id, subscribe_date) VALUES (?, current_date)");
                    prepStatment.setLong(1, chatId);
                    int res = prepStatment.executeUpdate();
                    System.out.println("Добавлено " + res + " записей");
                }
                else {
                    System.out.println("Собеседник " + chatId + " уже есть в базе");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        DAO.BotFunctions.AddSubscriber(chatId);
        
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        ArrayList<KeyboardRow> keys = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyRow.add(0,"Управление подпиской");
        keyRow.add(1, "О боте");
        keyRow.add(2, "Контакты");
        keys.add(keyRow);

        keyboardMarkup.setKeyboard(keys);
        keyboardMarkup.setResizeKeyboard(true);

        sendMessageToChat("Вы подписаны на инфо-рассылку!", chatId, keyboardMarkup);
        sendInlineMessageToChat("Пока сюда будет валиться всякая тестовая байда\nпо расписанию", chatId);

    }

    private void removeSubscriber(CallbackQuery callbackQuery){
        //PreparedStatement prepStatment;
        long chatId = callbackQuery.getMessage().getChatId();
        /*
        try {
            prepStatment = connection.prepareStatement("DELETE FROM subscribers WHERE  chat_id = ?");
            prepStatment.setBigDecimal(1, BigDecimal.valueOf(chatId));
            prepStatment.executeUpdate();
            System.out.println("Клиент " + chatId + " отписался.");
        } catch (Exception e) {
            e.printStackTrace();
        }
		*/
        DAO.BotFunctions.RemoveSubscriber(chatId);
        
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                //.setText("Вы отписались от рассылки \uD83D\uDC4B")
                .setText(botSettings.get("BotBayPhrase"))
                .setShowAlert(true);

        try {
            this.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

         ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

         ArrayList<KeyboardRow> keys = new ArrayList<>();
         KeyboardRow keyRow = new KeyboardRow();
         keyRow.add(0,"Подписаться");
         keyRow.add(1, "О боте");

         keys.add(keyRow);

         keyboardMarkup.setKeyboard(keys);
         keyboardMarkup.setResizeKeyboard(true);

         sendMessageToChat("Вы отписались от рассылки, но в любой момент можете " +
                 "подписаться снова!", chatId, keyboardMarkup);
    }

    private void clientGreetings(Message pMessage){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        ArrayList<KeyboardRow> keys = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyRow.add(0,"Подписаться");
        keyRow.add(1, "О боте");
        keys.add(keyRow);

        keyboardMarkup.setKeyboard(keys);
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage()
                .setChatId(pMessage.getChatId())
                //.setText("Доброе время, " + pMessage.getFrom().getFirstName() + "!\nРад преветствовать тебя в моем боте.\nhttp://zinurov.ru")
                .setText(botSettings.get("BotHelloPhrase"))
                .setReplyMarkup(keyboardMarkup);
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void onUpdateReceived(Update update) {
        String command, textCommand;
        Message msg;
        CallbackQuery callbackQuery;
        if(update.hasMessage()){
            msg = update.getMessage();
            System.out.println(msg.getText());
            if(msg.isCommand()){
                command = msg.getText();
                System.out.println("command: " + command);
                switch (command) {
                    case "/start":          clientGreetings(msg);
                                            break;
//                    case "/Подписаться":    addSubscriber(msg);
//                                            break;
//                    case "/Отписаться":     removeSubscriber(msg);
//                        break;
                    }
            }
            else if(msg.hasText()){
                textCommand = msg.getText();
                System.out.println("command: " + textCommand);
                switch (textCommand) {
                    case "Подписаться":    addSubscriber(msg);
                                            break;
//                    case "Управление подпиской":    addSubscriber(msg);
//                        break;
//                    case "О боте":    addSubscriber(msg);
//                        break;
//                    case "Контакты":    addSubscriber(msg);
//                        break;
                }
            }

        }
        else if(update.hasCallbackQuery()){
            callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            switch (data){
                case "subscribe":   addSubscriber(callbackQuery);
                                    break;
                case "unsubscribe": removeSubscriber(callbackQuery);
            }

        }


    }

    void sendInlineMessageToChat(String pMessage, long chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        ArrayList<InlineKeyboardButton> buttonRow = new ArrayList<>();
        InlineKeyboardButton callbackBtn = new InlineKeyboardButton("www.Zinurov.ru").setUrl("http://zinurov.ru");
        buttonRow.add(callbackBtn);

        //callbackBtn = new InlineKeyboardButton("@Zinurovru").setUrl("https://t.me/zinurovru");
        callbackBtn = new InlineKeyboardButton(botSettings.get("BotAuthorLink")).setUrl(botSettings.get("BotAuthorLink"));
        buttonRow.add(callbackBtn);

        callbackBtn = new InlineKeyboardButton("Отписаться").setCallbackData("unsubscribe");
        buttonRow.add(callbackBtn);
        buttons.add(buttonRow);

        inlineKeyboardMarkup.setKeyboard(buttons);

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(pMessage)
                .enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup);
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    void sendMessageToChat(String pMessage, long chatId, ReplyKeyboard keyboard){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        ArrayList<KeyboardRow> keys = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyRow.add(0,"Управление подпиской");
        keyRow.add(1, "О боте");
        keyRow.add(2, "Контакты");
        keys.add(keyRow);

        keyboardMarkup.setKeyboard(keys);
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(pMessage)
                .setReplyMarkup(keyboard);
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    
    }

    public String getBotUsername() {
        return botSettings.get("BotUserName");
    }

    public String getBotToken() {
        return botSettings.get("BotToken");
    }

	public BotSession getSession() {
		return session;
	}

	public void setSession(BotSession session) {
		this.session = session;
	}

}
