package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.CastlingMove;
import chess.core.move.Move;
import chess.core.move.NormalMove;

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
                    validMoves.add(new NormalMove(file, rank, file + dir[0], rank + dir[1]));
                }
            }
        }

        System.out.println("checking if king attacked");
        
        // I know its wrong, but i need to fix the entire board setup for this.
        // create castling right class for the position, contains starting position of rooks and kings
        if (!position.isSquareAttacked(file, rank, getColour().getOpposite()))
        {
            System.out.println("checking if king can castle");
            if (position.canCastleKingSide(getColour()))
            {
                System.out.println("king ma be able to castle kingside");
                int currentRank = rank - 1;
                if (position.getPiece(file, currentRank) == null || position.getPiece(file, currentRank).getColour() == getColour().getOpposite()) // normal move, so no castling
                    currentRank --;

                while (position.isInBounds(file, currentRank))
                {
                    validMoves.add(new CastlingMove(file, rank, file, currentRank));
                    currentRank --;
                }
            }
            // if (position.canCastleQueenSide(getColour()))
            // {
            //     System.out.println("king may be able to castle Queen side");
            //     int currentFile = file + 1;
            //     if (position.getPiece(currentFile, rank) == null || position.getPiece(currentFile, rank).getColour() == getColour().getOpposite()) // normal move, so no castling
            //         currentFile ++;

            //     while (position.isInBounds(currentFile, rank))
            //     {
            //         validMoves.add(new CastlingMove(file, rank, currentFile, rank));
            //         currentFile ++;
            //     }
            // }
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

    @Override
    public Piece copy()
    {
        King copy = new King(this.getColour());
        return copy;
    }
}
