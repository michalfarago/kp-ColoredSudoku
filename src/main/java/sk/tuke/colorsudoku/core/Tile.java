package sk.tuke.colorsudoku.core;

public class Tile {
    private int value;
    private boolean editable;

    public Tile(int value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
