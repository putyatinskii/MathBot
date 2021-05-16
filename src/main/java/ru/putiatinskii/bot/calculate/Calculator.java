package ru.putiatinskii.bot.calculate;

import ru.putiatinskii.bot.Operations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Calculator {
    /**
     * Формирования перечня уникальных заданий для 1 страницы итогового документа
     * @param operation операция (например, сложение)
     * @param min минимальное значение, которое должно использоваться в заданиях
     * @param max максимально значение, которое должно использоваться в заданиях
     * @param count количество заданий
     */
    Set<String> getTaskSet(Operations operation, int min, int max, int count) {
        Set<String> tasks = new HashSet<>();
        while (tasks.size() < count) {
            addTaskToSet(tasks, operation, min, max);
        }
        return tasks;
    }

    /**
     * Формирование задания, его проверка на попадание в заданные интервал и добавление в перечень
     * @param tasks список заданий
     * @param operation операция (например, сложение)
     * @param min минимальное значение, которое должно использоваться в заданиях
     * @param max максимально значение, которое должно использоваться в заданиях
     */
    private void addTaskToSet(Set<String> tasks, Operations operation, int min, int max) {
        int first = getRandomIntBetweenRange(min, max);
        int second = 2;

        if (!operation.equals(Operations.SQUARING) && !operation.equals(Operations.DIVISION))
            second = getRandomIntBetweenRange(min, max);
        else if (operation.equals(Operations.DIVISION))
            second = getDivisor(first);

        if ((operation.equals(Operations.SUBTRACTION))
                && first < second) {
            first += second;
            second = first - second;
            first -= second;
        }
        int result = calculate(operation, first, second);
        tasks.add(generateTask(operation, first, second));
    }

    /**
     * Получение случайного числа, попадающего в интервал
     */
    private int getRandomIntBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private int getDivisor(int x) {
        List<Integer> listDivisor = new ArrayList<>();
        for(int i = 2; i*i < x; i++) {
            if (x % i == 0) listDivisor.add(i);
        }
        if (listDivisor.size() !=0)
            return listDivisor.get(getRandomIntBetweenRange(0, listDivisor.size()-1));
        else
            return x;
    }

    /**
     * Расчёт результата
     * @param operation операция (например, сложение)
     * @param first первый аргумент
     * @param second второй аргумент
     */
    private int calculate(Operations operation, int first, int second) {
        switch (operation) {
            case SUBTRACTION:
                return first - second;
            case MULTIPLICATION:
                return first * second;
            case DIVISION:
                return first / second;
            case SQUARING:
                return (int) Math.pow(first, second);
            case ADDITION:
            default:
                return first + second;
        }
    }

    /**
     * Формирование строки задания
     */
    private String generateTask(Operations operation, int first, int second) {
        switch (operation) {
            case SUBTRACTION:
                return String.format("%s - %s =", first, second);
            case MULTIPLICATION:
                return String.format("%s * %s =", first, second);
            case DIVISION:
                return String.format("%s / %s =", first, second);
            case SQUARING:
                return String.format("%s ^ %s =", first, second);
            case ADDITION:
            default:
                return String.format("%s + %s =", first, second);
        }
    }
}
