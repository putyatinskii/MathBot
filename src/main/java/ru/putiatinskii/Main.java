package ru.putiatinskii;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.putiatinskii.bot.Bot;

public class Main {

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("qwerdimabot", "1824498002:AAH8wPgSTh4iNbS6CuBW7o6nv38-5im7ArU"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
