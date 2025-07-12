package chess.core;

public class CastlingRights
{
    private final int kingFile;
    private final int kingsideRookFile;
    private final int queensideRookFile;
    
    private final boolean kingsideAllowed;
    private final boolean queensideAllowed;
    
    public CastlingRights(int kingFile, int kingsideRookFile, int queensideRookFile, boolean kingsideAllowed, boolean queensideAllowed)
    {
        this.kingFile = kingFile;
        this.kingsideRookFile = kingsideRookFile;
        this.queensideRookFile = queensideRookFile;
        this.kingsideAllowed = kingsideAllowed;
        this.queensideAllowed = queensideAllowed;
    }
    
    
    public static CastlingRights standard()
    {
        return new CastlingRights(4, 7, 0, true, true);
    }
    
    public static CastlingRights forChess960(int kingFile, int kingsideRookFile, int queensideRookFile)
    {
        return new CastlingRights(kingFile, kingsideRookFile, queensideRookFile, true, true);
    }
    
    public int getKingFile() { return kingFile; }
    public int getKingsideRookFile() { return kingsideRookFile; }
    public int getQueensideRookFile() { return queensideRookFile; }
    public boolean isKingsideAllowed() { return kingsideAllowed; }
    public boolean isQueensideAllowed() { return queensideAllowed; }
    
    // modifiers
    public CastlingRights disableKingside()
    {
        return new CastlingRights(kingFile, kingsideRookFile, queensideRookFile, false, queensideAllowed);
    }
    
    public CastlingRights disableQueenside()
    {
        return new CastlingRights(kingFile, kingsideRookFile, queensideRookFile, kingsideAllowed, false);
    }
    
    public CastlingRights disableBoth() 
    {
        return new CastlingRights(kingFile, kingsideRookFile, queensideRookFile, false, false);
    }
}
