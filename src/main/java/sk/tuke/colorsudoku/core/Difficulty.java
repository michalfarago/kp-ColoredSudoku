package sk.tuke.colorsudoku.core;

public enum Difficulty {
    HARD(1),
    MEDIUM(2),
    EASY(3);

    Difficulty(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }
}
