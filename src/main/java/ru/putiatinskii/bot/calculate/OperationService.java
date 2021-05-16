package ru.putiatinskii.bot.calculate;

import ru.putiatinskii.bot.LEVEL;
import ru.putiatinskii.bot.Operations;
import ru.putiatinskii.bot.generation_doc.WordGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationService {
    private final WordGenerator fileProcessor;
    private final Calculator calculator;

    public OperationService(WordGenerator fileProcessor, Calculator calculator) {
        this.fileProcessor = fileProcessor;
        this.calculator = calculator;
    }

    /**
     * ������������ ����� � ��������� �� �������� �/��� ���������
     * @param operations ������ ����� �������� (�������� �/��� ���������)
     * @param lvl ��������� ������������ �����
     */
    public FileInputStream getOperationFile(List<Operations> operations, LEVEL lvl)
            throws IOException {
        List<String> taskList = new ArrayList<>();
        taskList.addAll(getTaskList(operations, lvl));

        if (taskList.isEmpty()) {
            throw new IllegalArgumentException(String.format("�� ���������� �������� �� ������� ������� �� ����� ������ "));
        }
        return fileProcessor.createWordFile(taskList);
    }

    /**
     * ������������ ������ �����
     * @param operations ���� �������� (��������, �������� � ���������)
     * @param lvl ��������� ������������ �����
     */
    private List<String> getTaskList(List<Operations> operations, LEVEL lvl) {
        int taskCount = getOperationTaskCount(operations.size());
        List<String> taskList = new ArrayList<>();
        for (Operations operation : operations) {
            fillTaskList(taskList, operation, taskCount, lvl.getMin(), lvl.getMax(),
                    calculateUniqueTaskCount(lvl.getMin(), lvl.getMax()));
        }
        Collections.shuffle(taskList);
        return taskList;
    }

    private static int calculateUniqueTaskCount(int min, int max) {
        int uniqueTaskCount = 0;
        if (max - 2 * min + 1 >= 0) {
            uniqueTaskCount = ((max - 2 * min + 2) * (max - 2 * min + 1 ))/2;
        }
        if (uniqueTaskCount < 0) {
            throw new IllegalArgumentException(String.format("�������� �������� %s - %s ������� ������. " +
                    "����� ���������� ����� �� ������� � Integer", min, max));
        }
        if (uniqueTaskCount == 0) {
            throw new IllegalArgumentException(String.format("\uD83D\uDCA9 ��� ���� ����� %s - %s �� ����������" +
                    " �������� � ���������, ��������� ������� �������� � �������� ����� ����", min, max));
        }
        return uniqueTaskCount;
    }

    /**
     * ���������� ������ �����. � ������, ���� ���������� ����� ������� ��� ���������� �����, ������������ ������
     * ���������� ������. � ��������� ������ ������� �����������
     * @param taskList ������ �����
     * @param operation ��� �������� (�������� ��� ���������)
     * @param taskCount ��������� ���������� ����� � ������
     * @param min ����������� ��������
     * @param max ������������ ��������
     * @param uniqueTaskCount ����� ���������� �����, �������� ����� ������������ � �������������� ��������� �����
     *                        �� min �� max
     */
    private void fillTaskList(List<String> taskList, Operations operation, int taskCount, int min, int max,
                              int uniqueTaskCount) {
        if (taskCount <= uniqueTaskCount) {
            taskList.addAll(calculator.getTaskSet(operation, min, max, taskCount));
        } else {
            taskList.addAll(calculator.getTaskSet(operation, min, max, uniqueTaskCount));
            int remainingNumbers = taskCount - uniqueTaskCount;
            fillTaskList(taskList, operation, remainingNumbers, min, max, uniqueTaskCount);
        }
    }

    /**
     * ������ ���������� ����� ��� ������ ��������. ���� ������������� ������ 2 �������� - ������
     * @param operationsCount ���������� ��������
     */
    private int getOperationTaskCount(int operationsCount) {
        //���������� �����, ����������� �� ���� �������� �4 � �������������� ���������� ��� ��������� ������� �
        //���� ������ (��. ����� setRunParameters ������ WordFileProcessorImpl)
        int linesCount = 52;
        if (operationsCount == 1) {
            return linesCount;
        } else if (operationsCount == 2) {
            return linesCount / 2;
        } else {
            throw new IllegalArgumentException("���������� �������� ��� ������������ ����� � ��������� �� " +
                    "��������-��������� ������ 2");
        }
    }
}
