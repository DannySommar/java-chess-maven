package chess.core.move;

public class CastlingMove extends Move
{

    public CastlingMove(int fromFile, int fromRank, int toFile, int toRank)
    {
        super(fromFile, fromRank, toFile, toRank);
    }
    
    public boolean isKingSide()
    {
        return fromRank < toRank;
    }
}
