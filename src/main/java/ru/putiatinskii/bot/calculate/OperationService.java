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

    public FileInputStream getOperationFile(List<Operations> operations, LEVEL lvl)
            throws IOException {
        List<String> taskList = new ArrayList<>();
        taskList.addAll(getTaskList(operations, lvl));

        if (taskList.isEmpty()) {
            throw new IllegalArgumentException(String.format("По непонятным причинам не удалось создать ни одной строки "));
        }
        return fileProcessor.createWordFile(taskList);
    }

    private List<String> getTaskList(List<Operations> operations, LEVEL lvl) {
        int taskCount = getOperationTaskCount(operations.size());
        int taskCountSpecific = 10;

        if (lvl.equals(LEVEL.LVL1)) {
            if (operations.size() == 4) {
                taskCount = 15;
                taskCountSpecific = 7;
            }
        }

        List<String> taskList = new ArrayList<>();
        for (Operations operation : operations) {
            if ((operation.equals(Operations.SQUARING) || operation.equals(Operations.DIVISION))
                    && lvl.equals(LEVEL.LVL1))
                fillTaskList(taskList, operation, taskCountSpecific, lvl.getMin(), lvl.getMax());
            else
                fillTaskList(taskList, operation, taskCount, lvl.getMin(), lvl.getMax());
        }
        Collections.shuffle(taskList);
        return taskList;
    }

    private void fillTaskList(List<String> taskList, Operations operation, int taskCount, int min, int max) {
        taskList.addAll(calculator.getTaskSet(operation, min, max, taskCount));
    }


    private int getOperationTaskCount(int operationsCount) {
        int linesCount = 52;
        if (operationsCount == 1) {
            return linesCount;
        } else if (operationsCount == 4) {
            return linesCount / 4;
        } else {
            throw new IllegalArgumentException("Количество операций для формирования файла с заданиями на " +
                    "сложение-вычитание больше 2");
        }
    }
}

