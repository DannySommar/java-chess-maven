package chess.piece;

import chess.Colour;

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
