package ru.putiatinskii.bot;

public enum LEVEL {
    LVL1(1, 10),
    LVL2(10, 100),
    LVL3(100, 1000),
    LVL4(1000, 10000);

    private int min;
    private int max;

    LEVEL(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
