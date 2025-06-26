package chess.core.piece;

import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

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


    @Override
    public boolean isValidMove(Position position, Move move) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }


    @Override
    public List<Move> generateValidMoves(Position position, int currentFile, int currentRank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateValidMoves'");
    }
}
