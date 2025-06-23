package chess.core.piece;

import chess.core.Colour;

public class King extends Piece
{
    
    public King(Colour colour)
    {
        super(colour, 0);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♔" : "♚";
    }
}
