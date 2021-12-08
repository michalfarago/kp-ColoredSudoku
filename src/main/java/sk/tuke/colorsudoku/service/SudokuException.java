package sk.tuke.colorsudoku.service;

public class SudokuException extends RuntimeException{
    public SudokuException() {
    }

    public SudokuException(String message) {
        super(message);
    }

    public SudokuException(String message, Throwable cause) {
        super(message, cause);
    }
}
