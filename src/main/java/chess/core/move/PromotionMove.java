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

    public Piece getPromotedTo()
    {
        return promotedTo;
    }

    // @Override
    // public boolean matches(Move other)
    // {
    //     if (!(other instanceof PromotionMove))
    //         return false;
    //     PromotionMove otherPromotion = (PromotionMove)other;
    //     return super.matches(other) && promotedTo.getClass() == otherPromotion.promotedTo.getClass();
    // }

    @Override
    public String toString()
    {
        return "from " + (char)('a' + fromRank) + (fromFile+1) + " to " + (char)('a' + toRank) + (toFile+1);
    }
}
