package chess.core.piece;

import chess.core.Colour;

public class Horse extends Piece
{
    
    public Horse(Colour colour)
    {
        super(colour, 3);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♘" : "♞";
    }
}
