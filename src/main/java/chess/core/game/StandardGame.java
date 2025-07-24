package chess.core.game;

import chess.core.Colour;
import chess.core.Position;
import chess.core.piece.*;

public class StandardGame extends Game
{

    public StandardGame(String p1, String p2)
    {
        super(p1, p2);
    }


    @Override
    protected void initializePosition()
    {
        // Create the board for standard chess
        Piece[][] board = new Piece[8][8];

        for (int file = 0; file < 8; file++) // pawns
        {
        board[file][1] = new Pawn(Colour.WHITE);
        board[file][6] = new Pawn(Colour.BLACK);
        }

        // not pawns
        board[0][0] = new Rook(Colour.WHITE);
        board[1][0] = new Horse(Colour.WHITE);
        board[2][0] = new Bishop(Colour.WHITE);
        board[3][0] = new Queen(Colour.WHITE);
        board[4][0] = new King(Colour.WHITE);
        board[5][0] = new Bishop(Colour.WHITE);
        board[6][0] = new Horse(Colour.WHITE);
        board[7][0] = new Rook(Colour.WHITE);
        
        // Black back row (rank 8, now index 7)
        board[0][7] = new Rook(Colour.BLACK);
        board[1][7] = new Horse(Colour.BLACK);
        board[2][7] = new Bishop(Colour.BLACK);
        board[3][7] = new Queen(Colour.BLACK);
        board[4][7] = new King(Colour.BLACK);
        board[5][7] = new Bishop(Colour.BLACK);
        board[6][7] = new Horse(Colour.BLACK);
        board[7][7] = new Rook(Colour.BLACK);

        // Set the starting position
        this.startingPosition = new Position(board, Colour.WHITE);
    }
}
