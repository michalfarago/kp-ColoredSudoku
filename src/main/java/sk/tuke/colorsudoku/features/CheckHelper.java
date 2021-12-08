package sk.tuke.colorsudoku.features;

import sk.tuke.colorsudoku.core.Tile;

public class CheckHelper {
    private Tile[][] tileset;
    private int squareSize;
    private boolean status;

    public CheckHelper(Tile[][] tileSet, int SquareSize)
    {
        this.tileset = tileSet;
        this.squareSize = SquareSize;
    }

    public boolean CheckTile(int y, int x, int value)
    {
        int previousValue = tileset[y][x].getValue();
        tileset[y][x].setValue(value);

        int totalNumbers = squareSize * squareSize;
        int[] numbersInRow = new int[totalNumbers + 1];
        int[] numbersInCol = new int[totalNumbers + 1];

        ClearIntArray(numbersInCol);
        ClearIntArray(numbersInRow);

        for (int index = 0; index < totalNumbers; index++)       //Look up numbers in a row and column
        {
            numbersInRow[tileset[y][index].getValue()]++;
            numbersInCol[tileset[index][x].getValue()]++;
        }

        for (int index = 1; index < totalNumbers; index++)       //Look up numbers in a row and column
        {
            if(numbersInRow[index] > 1 || numbersInCol[index] > 1)  //Entered wrong color
            {
                tileset[y][x].setValue(previousValue);
                return false;
            }
        }

        ClearIntArray(numbersInCol);

        for (int squareRow = 0; squareRow < squareSize; squareRow++) //Check specific square
        {
            for (int squareCol = 0; squareCol < squareSize; squareCol++)
            {
                numbersInCol[tileset[(y / squareSize) * squareSize + squareRow][(x / squareSize) * squareSize + squareCol].getValue()]++;
            }
        }

        for (int index = 1; index < totalNumbers; index++)       //Look up numbers in a row and column
        {
            if(numbersInCol[index] > 1)  //Entered wrong color
            {
                tileset[y][x].setValue(previousValue);
                return false;
            }
        }

        tileset[y][x].setValue(previousValue);
        return true;
    }

    private void ClearIntArray(int[] array) //Sets every item in array to 0
    {
        for (int i = 0; i < array.length; i++)
        {
            array[i] = 0;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
