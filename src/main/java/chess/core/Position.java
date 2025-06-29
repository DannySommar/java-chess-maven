package chess.core;

import java.util.List;

import chess.core.move.*;
import chess.core.piece.*;
import chess.game.*;

public class Position
{
    //private static final int BOARD_SIZE = 8;

    private Piece[][] board = new Piece[8][8]; // empty board
    private Colour turn;

    public boolean canWhiteCastle;
    public boolean canBlackCastle ;
    protected int enPassantFile; // we only need the file, as we can deterine the rank by Colour.WHITE ? 4 : 6
     

    public Position(Piece[][] board, Colour turn, boolean canWhiteCastle, boolean canBlackCastle, int enPassantFile)
    {
        this.turn = turn;
        this.canBlackCastle = canBlackCastle;
        this.canWhiteCastle = canWhiteCastle;
        this.enPassantFile = enPassantFile;
    }

    public Position(Piece[][] board, Colour turn)
    {
        this(board, turn, true, true, -1);
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
        Piece[][] newBoard = deepCopyBoard();

        Piece piece = newBoard[move.fromFile][move.fromRank];
        newBoard[move.fromFile][move.fromRank] = null;
        newBoard[move.toFile][move.toRank] = piece;

        int newEnPassantFile = -1;

        if (move instanceof EnPassantMove)
        {
            enPassantFile = -1;
            //remove the pawn 
        }

        // also check if king can castle;
        boolean newCanWhiteCastle = true;
        boolean newCanBlackCastle = true;


        Colour newTurn = turn == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        return new Position(
            newBoard,
            newTurn,
            newCanWhiteCastle,
            newCanBlackCastle,
            newEnPassantFile
        );
    }

    public Piece[][] deepCopyBoard()
    {
        Piece[][] newBoard = new Piece[8][8];

        for(int row = 0; row < board.length; row ++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                Piece piece = board[row][col];

                //newBoard[row][col] = new Piece();
            }
        }

        return newBoard;
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

    public Move returnLegalMoveOrNull(Move move)
    {
        if (!isInBounds(move))
            return null;
        
        Piece piece = getPiece(move.fromFile, move.fromRank);
        if (piece == null || piece.getColour() != turn)
            return null;

        List<Move> validMoves = piece.generateValidMoves(this, move.fromFile, move.fromRank);
        
        for (Move validMove : validMoves)
        {
            if (validMove.matches(move))
                return validMove;
        }

        Position newPosition = this.doMove(move);


        Square kingSquare = getKingSquare();

        if (newPosition.isSquareAttacked(kingSquare.file, kingSquare.rank)) {
            return null;
    }

        return null;
    }

    public boolean isInBounds(Move move)
    {
        return isInBounds(move.fromFile, move.fromRank) && isInBounds(move.toFile, move.toRank);
    }

    public boolean isInBounds(int file, int rank)
    {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    public boolean isSquareAttacked(int file, int rank)
    {
        for (int f = 0; f < 8; f++)
        {
            for (int r = 0; r < 8; r++)
            {
                Piece piece = getPiece(f, r);

                if (piece != null && piece.getColour() != turn)
                {
                    if (piece.isAttackingSquare(this, f, r, file, rank))
                        return true;
                }

            }
        }

        return false;
    }

    public int getEnPassantFile()
    {
        return enPassantFile;
    }
}
