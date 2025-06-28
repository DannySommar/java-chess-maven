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

        int forward = (getColour() == Colour.WHITE) ? 1 : -1; // normal
        
        if (position.isInBounds(file, rank + forward) && position.getPiece(file, rank + forward) == null) // double starter
        {
            moves.add(new Move(file, rank, file, rank + forward));

            if ((rank == 1 && getColour() == Colour.WHITE) || (rank == 6 && getColour() == Colour.BLACK))
            {
                if (position.getPiece(file, rank + 2 * forward) == null)
                {
                    moves.add(new Move(file, rank, file, rank + 2 * forward));
                }
            }
        }

        int[] captureFiles = {file - 1, file + 1}; // attacking
        for (int f : captureFiles) {
            if (position.isInBounds(f, rank + forward))
            {
                Piece target = position.getPiece(f, rank + forward);// normal attacking

                if (target != null && target.getColour() != getColour())
                {
                    moves.add(new Move(file, rank, f, rank + forward));
                }
                
                if ((rank + 3*forward == 0 || rank + 3*forward == 7) && f == position.getEnPassantFile())
                {
                    moves.add(new EnPassantMove(file, rank, f, rank + forward));
                }
            }
        }
        return moves;
    }
}
