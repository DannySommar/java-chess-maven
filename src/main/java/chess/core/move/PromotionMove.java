package chess.core.move;

import chess.core.piece.Piece;

public class PromotionMove extends Move
{
    public final Piece promotedTo;

    public PromotionMove(int fromFile, int fromRank, int toFile, int toRank, Piece promotedTo)
    {
        super(fromFile, fromRank, toFile, toRank);
        this.promotedTo = promotedTo;
    }
}
