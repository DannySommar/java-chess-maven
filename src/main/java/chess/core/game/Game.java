package chess.core.game;

import java.util.ArrayList;
import java.util.List;

import chess.core.Colour;
import chess.core.InvalidMoveException;
import chess.core.Position;
import chess.core.move.*;
import chess.core.piece.Horse;
import chess.core.piece.Pawn;
import chess.core.piece.Piece;

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
        return cachedCurrentPosition.getTurn();
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
        System.out.println("♘"); // for print testing purposes on windows terminal
        System.out.println("\u2658");
        
        Game game = new StandardGame("w", "b");

        System.out.println("White Player: " + game.getWhitePlayer());
        System.out.println("Black Player: " + game.getBlackPlayer());

        System.out.println("Starting Position:");
        System.out.println(game.getCurrentPosition());
        game.getCurrentPosition().printBoard();


       // Create a custom empty board position
        Piece[][] emptyBoard = new Piece[8][8]; // Create an empty board
        emptyBoard[0][1] = new Horse(Colour.WHITE);
        Position customPosition = new Position(emptyBoard, Colour.BLACK); // Initialize custom position
        System.out.println("Custom Position:");
        customPosition.printBoard();

        // Create a Puzzle game with the custom position
        Game puzzle = new Puzzle("White", "Black", customPosition);
        System.out.println("Puzzle Starting Position:");
        System.out.println(puzzle.startingPosition);
        puzzle.getStartingPosition().printBoard();
    }
}
