package chess.piece;

import chess.Colour;

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