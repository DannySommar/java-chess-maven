package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

public abstract class Piece
{
    private Colour colour;
    private int relativeValue; // the amount of pawns that piece is worth

    public Piece(Colour colour, int relativeValue)
    {
        this.colour = colour;
        this.relativeValue = relativeValue;
    }



    public Colour getColour()
    {
        return colour;
    }

    public int getRelativeValue()
    {
        return relativeValue;
    }

    public abstract boolean isValidMove(Position position, Move move);
    
}
