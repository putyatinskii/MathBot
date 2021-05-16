package ru.putiatinskii.bot.commands.service_commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.putiatinskii.bot.Utils;

public class StartCommand extends ServiceCommand {

    private Logger LOGGER = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        LOGGER.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Приступаю к работе. Для вызова справки нажмите /help \n\n" +
                        "Для генерации файла с операцией сложения воспользуйтесь командой /plus\n" +
                        "Для генерации файла с операцией вычитания воспользуйтесь командой /minus\n" +
                        "Для генерации файла с операцией умножения воспользуйтесь командой /multiplication\n" +
                        "Для генерации файла с операцией деления воспользуйтесь командой /division\n" +
                        "Для генерации файла с операцией возведения в квадрат воспользуйтесь командой /squaring\n" +
                        "Для генерации файла с операциями сложения, вычитания, умножения и деления" +
                        " воспользуйтесь командой /mix");
        LOGGER.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
