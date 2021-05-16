package ru.putiatinskii.bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.putiatinskii.bot.commands.operation_commands.*;
import ru.putiatinskii.bot.commands.service_commands.HelpCommand;
import ru.putiatinskii.bot.commands.service_commands.StartCommand;

public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger LOGGER = Logger.getLogger(Bot.class);
    private static LEVEL lvl = LEVEL.LVL1;
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public Bot(String botName, String botToken) {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help", "Помощь"));
        register(new PlusCommand("plus", "Сложение"));
        register(new MinusCommand("minus", "Вычитание"));
        register(new MultiplicationCommand("multiplication", "Умножение"));
        register(new DivisionCommand("division", "Деление"));
        register(new SquaringCommand("squaring", "Возведение в квадрат"));
        register(new MixCommand("mix", "Сложение, вычитание, умножение и деление"));
    }

    public static LEVEL getLvl() {
        return lvl;
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }



    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        String msgText = msg.getText();
        String answer;
        if (msgText.length() == 1 && Character.isDigit(msgText.charAt(0))) {
            int lvlInt = msgText.charAt(0) - '0';
            lvlInt--;
            if (lvlInt >=0 && lvlInt <=3) {
                this.lvl = LEVEL.values()[lvlInt];
                answer = "Уровень успешно изменен";
                setAnswer(chatId, userName, answer);
            } else {
                answer = "Такого уровня нет. Бот поддерживает 4 уровня";
                setAnswer(chatId, userName, answer);
            }
        } else {
            answer = "Бот вас не понимает. Если у вас возникли сложности, " +
                    "то вы всегда можете обратиться за помощью по команде \n/help";
            setAnswer(chatId, userName, answer);
        }
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            LOGGER.error("Ошибка при отправке ответа", e);
        }
    }
}
