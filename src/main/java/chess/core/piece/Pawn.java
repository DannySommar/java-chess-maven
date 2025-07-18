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
        return getColour() == Colour.WHITE ? "♙" : "♟";
    }



    @Override
    public List<Move> generateValidMoves(Position position, int file, int rank) {
        List<Move> moves = new ArrayList<>();

        int forward = (getColour() == Colour.WHITE) ? 1 : -1; // normal (includes promotion)
        int promotionRank = getColour() == Colour.WHITE ? 7 : 0;

        // I messed up rank and file :( 
        // System.out.println("forward: " + forward);
        // System.out.println("file: " + rank + " rank: " + file);
        // System.out.println("file: " + rank + " next rank: " + (file+forward));
        
        if (position.isInBounds(file + forward, rank) && position.getPiece(file + forward, rank) == null) // double starter
        {
            if(file + forward == promotionRank) // promotion
            {
                moves.add(new PromotionMove(file, rank, file + forward, rank, new Horse(getColour())));
                moves.add(new PromotionMove(file, rank, file + forward, rank, new Queen(getColour())));
                moves.add(new PromotionMove(file, rank, file + forward, rank, new Rook(getColour())));
                moves.add(new PromotionMove(file, rank, file + forward, rank, new Bishop(getColour())));
            }
            else // not promotion
            {
                moves.add(new NormalMove(file, rank, file + forward, rank));

                if ((file == 1 && getColour() == Colour.WHITE) || (file == 6 && getColour() == Colour.BLACK))
                {
                    if (position.getPiece(file + 2 * forward, rank) == null)
                    {
                        moves.add(new NormalMove(file, rank, file + 2 * forward, rank));
                    }
                }
            }
        }

        int[] captureFiles = {rank - 1, rank + 1}; // attacking
        for (int f : captureFiles) {
            if (position.isInBounds(file + forward, f))
            {
                Piece target = position.getPiece(file + forward, f);// normal attacking

                if (target != null && target.getColour() != getColour())
                {
                    if(file + forward == promotionRank) // promotion
                    {
                        moves.add(new PromotionMove(file, rank, file + forward, f, new Horse(getColour())));
                        moves.add(new PromotionMove(file, rank, file + forward, f, new Queen(getColour())));
                        moves.add(new PromotionMove(file, rank, file + forward, f, new Rook(getColour())));
                        moves.add(new PromotionMove(file, rank, file + forward, f, new Bishop(getColour())));
                    }

                    else
                        moves.add(new NormalMove(file, rank, file + forward, f));
                }
                
                if ((file + 3*forward == 0 || file + 3*forward == 7) && f == position.getEnPassantFile())
                {
                    moves.add(new EnPassantMove(file, rank, file + forward, f));
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
