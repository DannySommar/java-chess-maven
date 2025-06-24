package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

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


    @Override
    public boolean isValidMove(Position position, Move move) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }
}
