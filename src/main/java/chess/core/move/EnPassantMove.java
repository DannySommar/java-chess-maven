package chess.core.move;

public class EnPassantMove extends Move
{
    public EnPassantMove(int fromFile, int fromRank, int toFile, int toRank)
    {
        super(fromFile, fromRank, toFile, toRank);
    }
}
