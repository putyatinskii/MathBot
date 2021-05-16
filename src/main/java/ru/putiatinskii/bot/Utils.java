package ru.putiatinskii.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {
    /**
     * ������������ ����� ������������
     * @param msg ���������
     */
    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    /**
     * ������������ ����� ������������. ���� �������� �������, ���������� ���. ���� ��� - ���������� ������� � ���
     * @param user ������������
     */
    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }
}
