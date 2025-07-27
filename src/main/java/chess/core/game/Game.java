package chess.core.game;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.InvalidMoveException;
import chess.core.Position;
import chess.core.move.*;
import chess.core.piece.*;

public abstract class Game
{
    protected String whitePlayer; // may store Addr of playent if i ever make it a web-application
    protected String blackPlayer;
    protected List<Move> moves;
    protected Position startingPosition;
    protected Position cachedCurrentPosition;

    private int moveCounter; // for 50 move rule

    
    public Game(String p1, String p2, Position startingPosition)
    {
        this.whitePlayer = p1;
        this.blackPlayer = p2;
        this.moves = new ArrayList<>();
        this.startingPosition = startingPosition;
    }

    // Overloaded constructor for games that generate their starting position
    public Game(String p1, String p2)
    {
        this(p1, p2, null);
        initializePosition();
    }

    protected abstract void initializePosition();

    public Position getStartingPosition()
    {
        return startingPosition;
    }

    public Position getCurrentPosition()
    { 
        // dont need to store all the positions each turn

        if (cachedCurrentPosition != null)
            return cachedCurrentPosition;
        
        // may be used for replays
        Position position = startingPosition;
        for(Move move:moves)
        {
            position.doMove(move);
        }
        cachedCurrentPosition = position;
        return position;
    }

    public String getWhitePlayer()
    {
        return whitePlayer;
    }


    public String getBlackPlayer()
    {
        return blackPlayer;
    }


    public Colour getTurn()
    {
        return getCurrentPosition().getTurn();
    }

    public void addMove(Move move) throws InvalidMoveException
    {
        Position position = getCurrentPosition();

        Move newMove = position.returnLegalMoveOrNull(move);

        //System.out.println(newMove);

        if (newMove == null)
        {
            throw new InvalidMoveException("invalid move");
        }
        //System.out.println("position.returnLegalMoveOrNull says move is legal");

        if (position.getPiece(move.fromFile, move.fromRank) instanceof Pawn)
            moveCounter = 0;
        else
            moveCounter++;

        if (moveCounter >= 100)
        {
            ;//declare draw (i'll do this aafter finishing game logic)
        }

        moves.add(newMove);
        cachedCurrentPosition = position.doMove(newMove);
    }


    public static void main(String[] args)
    {
        Position position = new Position(new Piece[8][8], Colour.WHITE);
        Bishop bishop = new Bishop(Colour.WHITE);

        position.placePiece(bishop, 3, 3);

        List<Move> moves = bishop.generateValidMoves(position, 3, 3);
        for (Move move : moves)
            System.out.println(move.toString());
    }
}
