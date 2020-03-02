package hw2;

public class MyArrayDataException extends Exception {
    private int col;
    private int row;

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public MyArrayDataException(String message, int row, int col) {
        super(message);
        this.col = col;
        this.row = row;
    }
}
