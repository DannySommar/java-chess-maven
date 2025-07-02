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

        for (int i = 0; i < 8; i++) // pawns
        {
            board[1][i] = new Pawn(Colour.WHITE);
            board[6][i] = new Pawn(Colour.BLACK);
        }

        // not pawns
        board[0][0] = new Rook(Colour.WHITE);
        board[0][1] = new Horse(Colour.WHITE);
        board[0][2] = new Bishop(Colour.WHITE);
        board[0][3] = new Queen(Colour.WHITE);
        board[0][4] = new King(Colour.WHITE);
        board[0][5] = new Bishop(Colour.WHITE);
        board[0][6] = new Horse(Colour.WHITE);
        board[0][7] = new Rook(Colour.WHITE);
        
        board[7][0] = new Rook(Colour.BLACK);
        board[7][1] = new Horse(Colour.BLACK);
        board[7][2] = new Bishop(Colour.BLACK);
        board[7][3] = new Queen(Colour.BLACK);
        board[7][4] = new King(Colour.BLACK);
        board[7][5] = new Bishop(Colour.BLACK);
        board[7][6] = new Horse(Colour.BLACK);
        board[7][7] = new Rook(Colour.BLACK);

        // Set the starting position
        this.startingPosition = new Position(board, Colour.WHITE);
    }
}
