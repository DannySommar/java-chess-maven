package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public List<Move> generateValidMoves(Position position, int file, int rank)
    {
        List<Move> validMoves = new ArrayList<>();
        
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        
        for (int[] dir : directions)
        {
            int currFile = file + dir[0];
            int currRank = rank + dir[1];
            
            while (position.isInBounds(currFile, currRank)) {
                Piece target = position.getPiece(currFile, currRank);
                
                if (target == null)
                {
                    validMoves.add(new Move(file, rank, currFile, currRank));
                }
                else // furthest move blocked by a piece, checks if can take
                {
                    if (target.getColour() != this.getColour())
                    {
                        validMoves.add(new Move(file, rank, currFile, currRank));
                    }
                    break;
                }
                
                currFile += dir[0];
                currRank += dir[1];
            }
        }
        
        return validMoves;
    }
}
