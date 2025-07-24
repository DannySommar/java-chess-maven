package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

public class Rook extends Piece
{
    
    public Rook(Colour colour)
    {
        super(colour, 5);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "w_R" : "b_R";
    }

    @Override
    public List<Move> generateValidMoves(Position position, int file, int rank)
    {
        List<Move> validMoves = new ArrayList<>();
        
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        
        for (int[] dir : directions)
        {
            int currFile = file + dir[0];
            int currRank = rank + dir[1];
            
            while (position.isInBounds(currFile, currRank)) {
                Piece target = position.getPiece(currFile, currRank);
                
                if (target == null)
                {
                    validMoves.add(new NormalMove(file, rank, currFile, currRank));
                }
                else // furthest move blocked by a piece, checks if can take
                {
                    if (target.getColour() != this.getColour())
                    {
                        validMoves.add(new NormalMove(file, rank, currFile, currRank));
                    }
                    break;
                }
                
                currFile += dir[0];
                currRank += dir[1];
            }
        }
        
        return validMoves;
    }


    @Override
    public boolean isAttackingSquare(Position pos, int currentFile, int currentRank, int targetFile, int targetRank)
    {
        int fileDiff = Math.abs(targetFile - currentFile);
        int rankDiff = Math.abs(targetRank - currentRank);
        
        return (fileDiff == 0 || rankDiff == 0) && 
                pos.isClearPath(currentFile, currentRank, targetFile, targetRank);
    }

    @Override
    public Rook copy()
    {
        Rook copy = new Rook(this.getColour());
        return copy;
    }
}
