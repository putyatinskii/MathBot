package ru.putiatinskii.bot.commands.service_commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.putiatinskii.bot.Utils;

public class HelpCommand extends ServiceCommand {
    private Logger LOGGER = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        LOGGER.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Привет! Я бот который поможет научится считать.\n\n" +
                        "Я уменю создавать word-файл с простыми (и не очень) примерами для решения." +
                        " Вы можете распечатать этот документ и дать своим детям для тренировки счета\n\n" +
                        "\n/plus - получить задания на сложение\n" +
                        "\n/minus - получить задания на вычитание\n" +
                        "\n/multiplication - получить задания на умножение\n" +
                        "\n/division - получить задания на деление\n" +
                        "\n/squaring - получить задания на возведение в квадрат\n" +
                        "\n/mix - получить задания на cложение, вычитание, умножение и деление\n\n" +
                        "Введите цифру от 1 до 4, чтобы выбрать нужный уровень\n\n" +
                        "На Уровне 1 числа от 1 до 10\n" +
                        "На Уровне 2 числа от 10 до 100\n" +
                        "На Уровне 3 числа от 100 до 1000\n" +
                        "На Уровне 4 числа от 1000 до 10000\n");
        LOGGER.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
