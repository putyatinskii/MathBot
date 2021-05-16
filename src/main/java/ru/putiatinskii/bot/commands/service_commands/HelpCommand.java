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

        LOGGER.debug(String.format("������������ %s. ������ ���������� ������� %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "������! � ��� ������� ������� �������� �������.\n\n" +
                        "� ����� ��������� word-���� � �������� (� �� �����) ��������� ��� �������." +
                        " �� ������ ����������� ���� �������� � ���� ����� ����� ��� ���������� �����\n\n" +
                        "\n/plus - �������� ������� �� ��������\n" +
                        "\n/minus - �������� ������� �� ���������\n" +
                        "\n/multiplication - �������� ������� �� ���������\n" +
                        "\n/division - �������� ������� �� �������\n" +
                        "\n/squaring - �������� ������� �� ���������� � �������\n" +
                        "\n/mix - �������� ������� �� c�������, ���������, ��������� � �������\n\n" +
                        "������� ����� �� 1 �� 4, ����� ������� ������ �������\n\n" +
                        "�� ������ 1 ����� �� 1 �� 10\n" +
                        "�� ������ 2 ����� �� 10 �� 100\n" +
                        "�� ������ 3 ����� �� 100 �� 1000\n" +
                        "�� ������ 4 ����� �� 1000 �� 10000\n");
        LOGGER.debug(String.format("������������ %s. ��������� ���������� ������� %s", userName,
                this.getCommandIdentifier()));
    }
}
