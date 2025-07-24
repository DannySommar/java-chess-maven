package chess.core.move;


public class Move
{
    public int fromFile;
    public int fromRank;
    public int toFile;
    public int toRank;

    public Move(int fromFile, int fromRank, int toFile, int toRank)
    {
        this.fromFile = fromFile;
        this.fromRank = fromRank;
        this.toFile = toFile;
        this.toRank = toRank;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fromFile;
        result = prime * result + fromRank;
        result = prime * result + toFile;
        result = prime * result + toRank;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (fromFile != other.fromFile)
            return false;
        if (fromRank != other.fromRank)
            return false;
        if (toFile != other.toFile)
            return false;
        if (toRank != other.toRank)
            return false;
        return true;
    }


    public boolean matches(Move other)
    {
        return this.fromFile == other.fromFile &&
            this.fromRank == other.fromRank &&
            this.toFile == other.toFile &&
            this.toRank == other.toRank;
    }

    public String toString()
    {
        return "from " + (char)('a' + fromFile) + (fromRank+1) + " to " + (char)('a' + toFile) + (toRank+1);
    }
    
}
