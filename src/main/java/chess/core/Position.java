package chess.core;

import chess.core.move.*;
import chess.core.piece.*;
import chess.game.*;

public class Position
{
    //private static final int BOARD_SIZE = 8;

    private Piece[][] board = new Piece[8][8]; // empty board
    private Colour turn;
     

    public Position(Piece[][] board, Colour turn)
    {
        this.turn = turn;
        //deepcopy the Piece matrix for 'board'
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void placePiece(Piece piece, int file, int rank)
    {
        board[file][rank] = piece;
    }

    public Piece getPiece(int file, int rank)
    {
        return board[file][rank];
    }

    public Colour getTurn()
    {
        return turn;
    }

    public Position doMove(Move move)
    {
        //later
        return null;
    }

    public void printBoard()
    {
        // nice representation of board for debugging
        for(int row = 0; row < board.length; row ++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                if(board[row][col] == null)
                {
                    System.out.print("□ ");
                }else
                {
                    System.out.print(board[row][col]+ " ");
                }
            }

            System.out.println();
        }
    }

    public boolean isValidMove(Move move)
    {
        if (!isInBounds(move))
            return false;

        Piece piece = getPiece(move.fromFile, move.fromRank);
        if (piece == null || piece.getColour() != turn)
            return false;

        return piece.isValidMove(this, move);
    }

    public boolean isInBounds(Move move)
    {
        return  move.fromFile >= 0 && move.fromFile < 8
            &&  move.fromRank >= 0 && move.fromRank < 8
            &&  move.toFile >= 0 && move.toFile < 8
            &&  move.toRank >= 0 && move.toRank < 8;
    }
}
