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
        return getColour() == Colour.WHITE ? "w_K" : "b_K";
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
                // not yet suited for chess960
                System.out.println("king ma be able to castle kingside");
                
                if (position.isSafePath(file, rank, 5, rank) && 
                    isCastlingPathClear(position, file, 5, rank, position.getKingStartFile(), position.getKingsideRookFile()) &&
                    isCastlingPathClear(position, position.getKingsideRookFile(), 6, rank, position.getKingStartFile(), position.getKingsideRookFile()))
                {
                    System.out.println("path to Kingside rook is clear and safe");
                    validMoves.add(new CastlingMove(file, rank, position.getKingsideRookFile(), rank));
                }
            }
            if (position.canCastleQueenSide(getColour()))
            {
                System.out.println("king may be able to castle QueeenSide");
                
                if (position.isSafePath(file, rank, 3, rank) && 
                    isCastlingPathClear(position, file, 3, rank, position.getKingStartFile(), position.getQueensideRookFile()) &&
                    isCastlingPathClear(position, position.getQueensideRookFile(), 2, rank, position.getKingStartFile(), position.getQueensideRookFile()))
                {
                    System.out.println("path to Queenside rook is clear and safe");
                    validMoves.add(new CastlingMove(file, rank, position.getQueensideRookFile(), rank));
                }
            }
        }

        return validMoves;
    }

    private boolean isCastlingPathClear(Position position, int f1, int f2, int rank, int fRook, int fKing)
    {
        int fileStep = Integer.signum(f2 - f1);
        int f = f1;
        while (f != f2)
        {
            f += fileStep;
            System.out.println("current file: " + f);
            // cheecks if all squares in path except the castling pieces are empty
            if (!position.isSquareEmpty(f, rank) && !(f == fRook || f == fKing))
            {
                return false;
            }
        }

        return true;
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
