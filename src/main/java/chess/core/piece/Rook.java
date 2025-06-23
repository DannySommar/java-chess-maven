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

        int fileStep = Integer.signum(move.toFile - move.fromFile);
        int rankStep = Integer.signum(move.toRank - move.fromRank);

        int currFile = move.fromFile;
        int currRank = move.fromRank;

        while (currFile != currRank)
            continue; //finnish tomorrow

        return true;
    }
}
