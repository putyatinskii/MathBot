package ru.putiatinskii.bot.commands.operation_commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.putiatinskii.bot.Operations;
import ru.putiatinskii.bot.Utils;

import java.util.Collections;

public class MinusCommand extends  OperationCommand {
    private Logger LOGGER = LoggerFactory.getLogger(MinusCommand.class);

    public MinusCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        LOGGER.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), Collections.singletonList(Operations.SUBTRACTION), this.getDescription(),
                this.getCommandIdentifier(), userName);
        LOGGER.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
