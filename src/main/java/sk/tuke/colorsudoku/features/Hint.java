package sk.tuke.colorsudoku.features;

public class Hint {
    public int x;
    public int y;
    public int value;

    public Hint(int y, int x, int value)
    {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

}
