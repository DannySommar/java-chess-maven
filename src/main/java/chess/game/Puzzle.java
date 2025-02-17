package chess.game;

import chess.piece.*;
import chess.Position;
import chess.Colour;

// Makes a custom game with a given position
public class Puzzle extends Game
{

    public Puzzle(String p1, String p2, Position position)
    {
        super(p1, p2, position);
    }


    @Override
    protected void initializePosition()
    // If called without board, gives an error
    {
        if (startingPosition == null)
        {
            throw new IllegalArgumentException("Custom position is none");
        }
    }
    


}
