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

        LOGGER.debug(String.format("������������ %s. ������ ���������� ������� %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "��������� � ������. ��� ������ ������� ������� /help \n\n" +
                        "��� ��������� ����� � ��������� �������� �������������� �������� /plus\n" +
                        "��� ��������� ����� � ��������� ��������� �������������� �������� /minus\n" +
                        "��� ��������� ����� � ��������� ��������� �������������� �������� /multiplication\n" +
                        "��� ��������� ����� � ��������� ������� �������������� �������� /division\n" +
                        "��� ��������� ����� � ��������� ���������� � ������� �������������� �������� /squaring\n" +
                        "��� ��������� ����� � ���������� ��������, ���������, ��������� � �������" +
                        " �������������� �������� /mix");
        LOGGER.debug(String.format("������������ %s. ��������� ���������� ������� %s", userName,
                this.getCommandIdentifier()));
    }
}
