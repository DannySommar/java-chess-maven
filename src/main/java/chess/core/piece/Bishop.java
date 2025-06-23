package chess.core.piece;

import chess.core.Colour;

public class Bishop extends Piece
{
    
    public Bishop(Colour colour)
    {
        super(colour, 3);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♗" : "♝";
    }
}
