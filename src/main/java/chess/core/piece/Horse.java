package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

public class Horse extends Piece
{
    
    public Horse(Colour colour)
    {
        super(colour, 3);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♘" : "♞";
    }



    @Override
    public List<Move> generateValidMoves(Position position, int file, int rank)
    {
        List<Move> validMoves = new ArrayList<>();
        
        int[][] directions = {{1,2}, {2,1}, {-1,2}, {2,-1}, {1,-2}, {-2,1}, {-1,-2}, {-2,-1}};
        
        for (int[] dir : directions)
        {
            if (position.isInBounds(file + dir[0], rank + dir[1])) {

                Piece target = position.getPiece(file + dir[0], rank + dir[1]);


                if (target == null || target.getColour() != this.getColour())
                {
                    validMoves.add(new NormalMove(file, rank, file + dir[0], rank + dir[1]));
                }
            }
        }
        
        return validMoves;
    }


    @Override
    public boolean isAttackingSquare(Position pos, int currentFile, int currentRank, int targetFile, int targetRank)
    {
        int[][] directions = {{1,2}, {2,1}, {-1,2}, {2,-1}, {1,-2}, {-2,1}, {-1,-2}, {-2,-1}};
        
        for (int[] dir : directions)
        {
            if (currentFile + dir[0] == targetFile && currentRank + dir[1] == targetRank)
                return true;
        }
        return false;
    }
}
