package chess.game;


import chess.piece.*;
import chess.Position;
import chess.Colour;

public class Chess960 extends Game
{

    public Chess960(String p1, String p2)
    {
        super(p1, p2);
    }

    
    @Override
    protected void initializePosition()
    {
        Piece[][] board = new Piece[8][8];


        // Set the starting position
        this.startingPosition = new Position(board, Colour.WHITE);
    }
}
