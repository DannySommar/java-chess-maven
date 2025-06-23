package chess.core.piece;

import chess.core.Colour;

public class Pawn extends Piece
{
    
    public Pawn(Colour colour)
    {
        super(colour, 1);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♙" : "♟";
    }
}
