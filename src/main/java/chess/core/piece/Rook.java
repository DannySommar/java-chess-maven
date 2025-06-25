package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

public class Rook extends Piece
{
    
    public Rook(Colour colour)
    {
        super(colour, 5);
    }


    public String toString()
    {
        return getColour() == Colour.WHITE ? "♖" : "♜";
    }


    @Override
    public boolean isValidMove(Position position, Move move)
    {
        if (!(move.fromFile == move.toFile || move.fromRank == move.toRank))
            return false; // rook must preserve either file or rank


        int currFile = move.fromFile;
        int currRank = move.fromRank;

        if (currFile == move.toFile) // rook moves by rank
        {   
            int rankStep = Integer.signum(move.toRank - move.fromRank);
            currRank += rankStep;
            while (currRank != move.toRank)
            {
                if (position.getPiece(currFile, currRank) != null)
                    return false;
                currRank += rankStep;
            }
        }

        else // rook moves by file
        {   
            int fileStep = Integer.signum(move.toFile - move.fromFile);
            currFile += fileStep;
            while (currFile != move.toFile)
            {
                if (position.getPiece(currFile, currRank) != null)
                    return false;
                currFile += fileStep;
            }
        }

        Piece target = position.getPiece(currFile, currRank);
        return target == null || target.getColour() != this.getColour();
    }
}
