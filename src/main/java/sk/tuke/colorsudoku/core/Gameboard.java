package sk.tuke.colorsudoku.core;

import sk.tuke.colorsudoku.features.CheckHelper;
import sk.tuke.colorsudoku.features.HintAssist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Gameboard {
    private Tile[][] tileset;
    private final int colCount;
    private final int rowCount;
    private final int squareSize;
    private final Difficulty difficulty;
    private int points;
    private CheckHelper checkHelper;
    private HintAssist hintAssist;

    public Gameboard(int squareSize, Difficulty difficulty) {
        this.colCount = squareSize * squareSize;
        this.rowCount = squareSize * squareSize;
        this.squareSize = squareSize;
        this.difficulty = difficulty;
        this.points = squareSize * 100 * (4 - difficulty.ordinal());
        this.tileset = new Tile[rowCount][colCount];
        generateTiles();
        this.checkHelper = new CheckHelper(this.tileset,squareSize);
        this.hintAssist = new HintAssist(this.tileset, rowCount, colCount);
        generateCloues((colCount * rowCount * difficulty.ordinal())/4);
//        generateCloues(colCount * rowCount - 1);
        if(difficulty == Difficulty.HARD){
            this.checkHelper.setStatus(false);
        }else{
            this.checkHelper.setStatus(true);
        }
    }

    public int getPoints() {
        return points;
    }

    private void generateTiles(){
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        //Fill tileset wit Tiles
        for(int squareRow = 0; squareRow < squareSize; squareRow++){
            shiftListItemsToLeft(numbers,1);
            for(int i = 0; i < squareSize; i++){
                //Fill row
                for(int x = 0; x < colCount; x++){
                    tileset[squareRow*squareSize + i ][x] = new Tile(numbers.get(x),false);
                }
                if(i != squareSize - 1)
                    shiftListItemsToLeft(numbers,3);
            }
        }
    }

    private void shiftListItemsToLeft(List<Integer> list, int shift){
        for(int i = 0;  i < shift; i++){
            list.add(list.size()-1,list.remove(0));
        }
    }

    private void generateCloues(int clueClount){
        Random random = new Random();

        if (clueClount < 17) clueClount = 17;

        int clueX, clueY;

        for (int i = 0; i < colCount * rowCount - clueClount; i++)
        {
            do
            {
                clueX = random.nextInt(colCount);
                clueY = random.nextInt(rowCount);
            } while(tileset[clueY][clueX].getValue() == 0);
            tileset[clueY][clueX].setValue(0);
            tileset[clueY][clueX].setEditable(true);
        }
    }

    public boolean IsGameSolved()
    {
        if (CheckRowsAndCols() && CheckSquares())
        {
            return true;
        }
        return false;
    }

    private boolean CheckRowsAndCols()
    {
        int totalNumbers = squareSize * squareSize;
        int[] numbersInRow = new int[totalNumbers + 1];
        int[] numbersInCol = new int[totalNumbers + 1];

        for (int diagonalIndex = 0; diagonalIndex < totalNumbers; diagonalIndex++)
        {
            ClearIntArray(numbersInCol);
            ClearIntArray(numbersInRow);

            for (int x = 0; x < totalNumbers; x++)       //Look up numbers in a row and column
            {
                numbersInRow[tileset[diagonalIndex][x].getValue()]++;
                numbersInCol[tileset[x][diagonalIndex].getValue()]++;
            }

            //If there are any 0 (empty spaces) no reason to check rest => return false
            if (numbersInRow[0] != 0 || numbersInCol[0] != 0) return false;

            for (int x = 1; x <= totalNumbers; x++)     //Check if all numbers are present in a row and column
            {
                if (numbersInRow[x] != 1 || numbersInCol[x] != 1) return false;
            }
        }
        return true;
    }

    private boolean CheckSquares()
    {
        int totalNumbers = squareSize * squareSize;
        int[] lookUpNumbers = new int[totalNumbers + 1];

        for (int row = 0; row < squareSize; row++)                          //Loop trough whole grid
        {
            for (int column = 0; column < squareSize; column++)
            {
                ClearIntArray(lookUpNumbers);                   //Clean array

                for (int squareRow = 0; squareRow < squareSize; squareRow++) //Check specific square
                {
                    for (int squareCol = 0; squareCol < squareSize; squareCol++)
                    {
                        lookUpNumbers[tileset[row * squareSize + squareRow][column * squareSize + squareCol].getValue()]++;
                    }
                }

                if (lookUpNumbers[0] != 0) return false;                   //If there are any 0 (empty spaces) no reasun to check rest => return false

                for (int x = 1; x <= totalNumbers; x++)                    //Check if all numbers are present in a square
                {
                    if (lookUpNumbers[x] != 1) return false;
                }
            }
        }
        return true;
    }

    private void ClearIntArray(int[] array) //Sets every item in array to 0
    {
        for (int i = 0; i < array.length; i++)
        {
            array[i] = 0;
        }
    }

    public void printGameBoard(){
        for(int y = 0; y < rowCount; y++){
            for(int x = 0; x < colCount; x++){
                System.out.print(tileset[y][x].getValue());
            }
            System.out.println();
        }
    }

    public int WriteToTile(int Xcoordinate, int Ycoordinate, int value)
    {
        if (Xcoordinate >= rowCount || Xcoordinate < 0) { return 0; }
        if (Ycoordinate >= colCount || Ycoordinate < 0) { return 0; }
        if (value > squareSize * squareSize || value < 1) { return 0; }

        if (tileset[Ycoordinate][Xcoordinate].isEditable())
        {
            if (this.checkHelper.isStatus())
            {
                if (!this.checkHelper.CheckTile(Ycoordinate, Xcoordinate, value))  //If player entered wrong color for sure
                {
                    points -= difficulty.ordinal() * 5;
                    return 1;
                }
            }
            if(tileset[Ycoordinate][Xcoordinate].getValue() != 0) points -= difficulty.getValue() * 10;    //Affect player score
            tileset[Ycoordinate][Xcoordinate].setValue(value);
            return 1;
        }
        return 0;
    }

    public int getColCount() {
        return colCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public Tile[][] getTileset() {
        return tileset;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public CheckHelper getCheckHelper() {
        return checkHelper;
    }

    public HintAssist getHintAssist() { return hintAssist; }
}
