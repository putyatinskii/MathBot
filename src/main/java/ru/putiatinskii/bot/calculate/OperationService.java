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
     * ‘ормирование файла с задани€ми на сложение и/или вычитание
     * @param operations список типов операций (сложение и/или вычитание)
     * @param lvl настройки выгружаемого файла
     */
    public FileInputStream getOperationFile(List<Operations> operations, LEVEL lvl)
            throws IOException {
        List<String> taskList = new ArrayList<>();
        taskList.addAll(getTaskList(operations, lvl));

        if (taskList.isEmpty()) {
            throw new IllegalArgumentException(String.format("ѕо непон€тным причинам не удалось создать ни одной строки "));
        }
        return fileProcessor.createWordFile(taskList);
    }

    /**
     * ‘ормирование списка задач
     * @param operations типы операций (например, сложение и вычитание)
     * @param lvl настройки выгружаемого файла
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
            throw new IllegalArgumentException(String.format("«аданные значени€ %s - %s слишком велики. " +
                    "„исло уникальных задач не влезает в Integer", min, max));
        }
        if (uniqueTaskCount == 0) {
            throw new IllegalArgumentException(String.format("\uD83D\uDCA9 ƒл€ пары чисел %s - %s не существует" +
                    " сложений и вычитаний, результат которых попадает в интервал между ними", min, max));
        }
        return uniqueTaskCount;
    }

    /**
     * Ќаполнение списка задач. ¬ случае, если уникальных задач хватает дл€ наполнени€ листа, используютс€ только
     * уникальные задачи. ¬ противном случае задани€ повтор€ютс€
     * @param taskList список задач
     * @param operation тип операции (сложение или вычитание)
     * @param taskCount требуемое количество задач в списке
     * @param min минимальное значение
     * @param max максимальное значение
     * @param uniqueTaskCount число уникальных задач, которыне можно сформировать с использованием интервала чисел
     *                        от min до max
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
     * –асчЄт количества задач дл€ каждой операции. ≈сли запрашиваетс€ больше 2 операций - ошибка
     * @param operationsCount количество операций
     */
    private int getOperationTaskCount(int operationsCount) {
        //количество строк, вмещающихс€ на одну страницу ј4 в горизонтальной ориентации при выбранных размере и
        //типе шрифта (см. метод setRunParameters класса WordFileProcessorImpl)
        int linesCount = 52;
        if (operationsCount == 1) {
            return linesCount;
        } else if (operationsCount == 2) {
            return linesCount / 2;
        } else {
            throw new IllegalArgumentException(" оличество операций дл€ формировани€ файла с задани€ми на " +
                    "сложение-вычитание больше 2");
        }
    }
}
