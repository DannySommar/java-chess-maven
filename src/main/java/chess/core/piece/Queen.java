package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

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


    @Override
    public boolean isValidMove(Position position, Move move) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }
}
