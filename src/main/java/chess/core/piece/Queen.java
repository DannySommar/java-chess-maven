package chess.core.piece;

import chess.core.Colour;

public class Queen extends Piece
{
    
    public Queen(Colour colour)
    {
        super(colour, 9);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♕" : "♛";
    }
}
