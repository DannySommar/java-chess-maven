package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

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
    public List<Move> generateValidMoves(Position position, int file, int rank)
    {
        List<Move> validMoves = new ArrayList<>();
        
        int[][] directions = {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}};
        
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
