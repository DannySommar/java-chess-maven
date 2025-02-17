package chess.piece;

import chess.Colour;

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