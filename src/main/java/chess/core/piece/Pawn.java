package chess.core.piece;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.*;

public class Pawn extends Piece
{
    
    public Pawn(Colour colour)
    {
        super(colour, 1);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "w_P" : "b_P";
    }



    @Override
    public List<Move> generateValidMoves(Position position, int file, int rank) {
        List<Move> moves = new ArrayList<>();

        int forward = (getColour() == Colour.WHITE) ? 1 : -1; // normal (includes promotion)
        int promotionRank = getColour() == Colour.WHITE ? 7 : 0;

        // System.out.println("forward: " + forward);
        // System.out.println("file: " + rank + " rank: " + file);
        // System.out.println("file: " + rank + " next rank: " + (file+forward));
        
        if (position.isInBounds(file, rank + forward) && position.getPiece(file, rank + forward) == null) // double starter
        {
            if(rank + forward == promotionRank) // promotion
            {
                moves.add(new PromotionMove(file, rank, file, rank + forward, new Horse(getColour())));
                moves.add(new PromotionMove(file, rank, file, rank + forward, new Queen(getColour())));
                moves.add(new PromotionMove(file, rank, file, rank + forward, new Rook(getColour())));
                moves.add(new PromotionMove(file, rank, file, rank + forward, new Bishop(getColour())));
            }
            else // not promotion
            {
                moves.add(new NormalMove(file, rank, file, rank + forward));

                if ((rank == 1 && getColour() == Colour.WHITE) || (rank == 6 && getColour() == Colour.BLACK))
                {
                    if (position.getPiece(file, rank + 2 * forward) == null)
                    {
                        moves.add(new NormalMove(file, rank, file, rank + 2 * forward));
                    }
                }
            }
        }

        int[] captureFiles = {file - 1, file + 1}; // attacking
        for (int f : captureFiles) {
            if (position.isInBounds(f, rank))
            {
                Piece target = position.getPiece(f, rank + forward);// normal attacking

                if (target != null && target.getColour() != getColour())
                {
                    if(rank + forward == promotionRank) // promotion
                    {
                        moves.add(new PromotionMove(file, rank, f, rank + forward, new Horse(getColour())));
                        moves.add(new PromotionMove(file, rank, f, rank + forward, new Queen(getColour())));
                        moves.add(new PromotionMove(file, rank, f, rank + forward, new Rook(getColour())));
                        moves.add(new PromotionMove(file, rank, f, rank + forward, new Bishop(getColour())));
                    }

                    else
                        moves.add(new NormalMove(file, rank, f, rank + forward));
                }
                
                if ((rank + 3*forward == 0 || rank + 3*forward == 7) && f == position.getEnPassantFile())
                {
                    moves.add(new EnPassantMove(file, rank, f, rank + forward));
                }
            }
        }
        return moves;
    }


    @Override
    public boolean isAttackingSquare(Position pos, int currentFile, int currentRank, int targetFile, int targetRank)
    {
        int forward = (getColour() == Colour.WHITE) ? 1 : -1;

        if (currentRank + forward == targetRank)
        {
            return Math.abs(targetFile - currentFile) == 1;
        }

        return false;
    }

    @Override
    public Piece copy()
    {
        Pawn copy = new Pawn(this.getColour());
        return copy;
    }
}
