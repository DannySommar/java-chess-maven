package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.CastlingMove;
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
    public List<Move> generateValidMoves(Position position, int file, int rank)
    {
        List<Move> validMoves = new ArrayList<>();
        
        int[][] directions = {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}};
        
        for (int[] dir : directions)
        {
            if (position.isInBounds(file + dir[0], rank + dir[1])) {

                Piece target = position.getPiece(file + dir[0], rank + dir[1]);


                if (target == null || target.getColour() != this.getColour())
                {
                    validMoves.add(new Move(file, rank, file + dir[0], rank + dir[1]));
                }
            }
        }

        
        // I know its wrong, but i need to fix the entire board setup for this.
        // create castling right class for the position, contains starting position of rooks and kings
        if (!position.isSquareAttacked(file, rank))
        {
            if (position.canCastleKingSide(getColour()))
            {
                int currentFile = file - 1;
                if (position.getPiece(currentFile, rank) == null || position.getPiece(currentFile, rank).getColour() == getColour().getOpposite()) // normal move, so no castling
                    currentFile --;

                while (position.isInBounds(currentFile, rank))
                {
                    validMoves.add(new CastlingMove(file, rank, currentFile, rank));
                    currentFile --;
                }
            }
            if (position.canCastleQueenSide(getColour()))
            {
                int currentFile = file + 1;
                if (position.getPiece(currentFile, rank) == null || position.getPiece(currentFile, rank).getColour() == getColour().getOpposite()) // normal move, so no castling
                    currentFile ++;

                while (position.isInBounds(currentFile, rank))
                {
                    validMoves.add(new CastlingMove(file, rank, currentFile, rank));
                    currentFile ++;
                }
            }
        }

        return validMoves;
    }


    @Override
    public boolean isAttackingSquare(Position pos, int currentFile, int currentRank, int targetFile, int targetRank)
    {
        int fileDiff = Math.abs(targetFile - currentFile);
        int rankDiff = Math.abs(targetRank - currentRank);

        return (fileDiff <= 1) && (rankDiff <= 1);
    }
}
