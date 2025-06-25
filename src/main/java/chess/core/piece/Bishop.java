package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

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


    @Override
    public boolean isValidMove(Position position, Move move)
    {
        int fileDiff = Math.abs(move.toFile - move.fromFile);
        int rankDiff = Math.abs(move.toRank - move.fromRank);
        
        if (fileDiff != rankDiff)
            return false; // diagonal
        

        int fileStep = Integer.signum(move.toFile - move.fromFile);
        int rankStep = Integer.signum(move.toRank - move.fromRank);

        int currFile = move.fromFile + fileStep;
        int currRank = move.fromRank + rankStep;

        while (currFile != move.toFile)
        {
            if (position.getPiece(currFile, currRank) != null)
                return false;
            currFile += fileStep;
            currRank += rankStep;
        }

        Piece target = position.getPiece(move.toFile, move.toRank);
        return target == null || target.getColour() != this.getColour();
    }
}
