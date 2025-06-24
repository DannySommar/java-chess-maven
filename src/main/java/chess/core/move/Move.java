package chess.core.move;

import chess.core.Colour;
import chess.core.Position;

public class Move
{
    public int fromFile;
    public int fromRank;
    public int toFile;
    public int toRank;

    public Move(int fromFile, int fromRank, int toFile, int toRank)
    {
        this.fromFile = fromFile;
        this.fromRank = fromRank;
        this.toFile = toFile;
        this.toRank = toRank;
    }


    
}
