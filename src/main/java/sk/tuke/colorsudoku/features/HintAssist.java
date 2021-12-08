package sk.tuke.colorsudoku.features;

import sk.tuke.colorsudoku.core.Tile;

import java.util.Random;

public class HintAssist {
    private Tile[][] currentTileset;
    private Tile[][] solutionTileset;
    private int colCount;
    private int rowCount;

    public HintAssist(Tile[][] currentTileset, int rowCount, int colCount)
    {
        this.currentTileset = currentTileset;
        this.solutionTileset = new Tile[rowCount][colCount];
        this.colCount = colCount;
        this.rowCount = rowCount;

        for (int y = 0; y < rowCount; y++)
        {
            for (int x = 0; x < colCount; x++)
            {
                this.solutionTileset[y][x] = new Tile(currentTileset[y][x].getValue(), false);
            }
        }
    }


    public Hint GiveHint()
    {
        Random randomGenerator = new Random();
        int y, x;

        do
        {
            x = randomGenerator.nextInt(colCount);
            y = randomGenerator.nextInt(rowCount);
        } while (currentTileset[y][x].getValue() != 0 || currentTileset[y][x].isEditable() == false);

        return new Hint( y, x, solutionTileset[y][x].getValue());
    }
}
