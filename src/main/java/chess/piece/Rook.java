package chess.piece;

import chess.Colour;

public class Rook extends Piece
{
    
    public Rook(Colour colour)
    {
        super(colour, 5);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♖" : "♜";
    }
}