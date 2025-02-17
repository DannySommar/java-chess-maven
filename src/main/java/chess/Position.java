package chess;

import chess.Colour;
import chess.piece.*;
import chess.move.*;

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

    public void doMove(Move move)
    {
        //later
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

    public Colour getTurn()
    {
        return turn;
    }

}
