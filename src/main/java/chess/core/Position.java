package chess.core;

import java.util.ArrayList;
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
        this.board = board;
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

        Colour newTurn = turn.getOpposite();
        int newEnPassantFile = -1;
        boolean newCanWhiteCastle = canWhiteCastle;
        boolean newCanBlackCastle = canBlackCastle;

        //I know some stuff repeats, but I prefer it that way
        if (move instanceof NormalMove)
        {
            newBoard[move.toFile][move.toRank] = piece;
            newBoard[move.fromFile][move.fromRank] = null;
        }
        else if (move instanceof CastlingMove)
        {
            if (((CastlingMove)move).isKingSide())
            {
                // move rook
                int rookFromFile = findRookFile(move.fromRank, move.fromFile + 1, 7);
                Piece rook = newBoard[rookFromFile][move.fromRank];
                newBoard[rookFromFile][move.fromRank] = null;
                newBoard[5][move.fromRank] = rook;

                // move king
                newBoard[move.fromFile][move.fromRank] = null;
                newBoard[6][move.fromRank] = piece;
            }
            else{
                // move rook
                int rookFromFile = findRookFile(move.fromRank, 0, move.fromFile - 1);
                Piece rook = newBoard[rookFromFile][move.fromRank];
                newBoard[rookFromFile][move.fromRank] = null;
                newBoard[3][move.fromRank] = rook;

                // move king
                newBoard[move.fromFile][move.fromRank] = null;
                newBoard[2][move.fromRank] = piece;
            }
        }
        else if (move instanceof PromotionMove)
        {
            piece = ((PromotionMove)move).getPromotedTo();
            newBoard[move.toFile][move.toRank] = piece;
            newBoard[move.fromFile][move.fromRank] = null;
        }
        else if (move instanceof IlVaticanoMove)
        {
            Piece bishop1 = newBoard[move.fromFile][move.fromRank];
            Piece bishop2 = newBoard[move.toFile][move.toRank];

            boolean isHorizontal = (move.fromRank == move.toRank);
            boolean isVertical = (move.fromFile == move.toFile);
            if (!isHorizontal && !isVertical)
                throw new IllegalStateException("Il Vaticano: elephants must be aligned");

            int distance = isHorizontal ? Math.abs(move.toFile - move.fromFile) : Math.abs(move.toRank - move.fromRank);
            
            int fileStep = Integer.signum(move.toFile - move.fromFile);
            int rankStep = Integer.signum(move.toRank - move.fromRank);
            int currFile = move.fromFile + fileStep;
            int currRank = move.fromRank + rankStep;

            while (currFile != move.toFile || currRank != move.toRank)
            {
                newBoard[currFile][currRank] = null;
                currFile += fileStep;
                currRank += rankStep;
            }

            // MUST BE CLOCKWISE!!!!!!!!!!!!!!!!!!!
            newBoard[move.toFile][move.toRank] = bishop1;
            newBoard[move.fromFile][move.fromRank] = bishop2;
        }

        if (piece instanceof King)
        {
            if (piece.getColour() == Colour.WHITE)
                newCanWhiteCastle = false;
            else 
                newCanBlackCastle = false;
        }
        else if (piece instanceof Pawn && Math.abs(move.toRank - move.fromRank) == 2)
        {
            newEnPassantFile = move.fromFile;
        }

        return new Position(
            newBoard,
            newTurn,
            newCanWhiteCastle,
            newCanBlackCastle,
            newEnPassantFile
        );
    }

    private int findRookFile(int rank, int startFile, int endFile)
    {
        int step = startFile <= endFile ? 1 : -1;
        for (int f = startFile; f != endFile + step; f += step)
        {
            Piece p = board[f][rank];
            if (p instanceof Rook && p.getColour() == turn)
                return f;
        }
        throw new IllegalStateException("no rook");
    }

    public Piece[][] deepCopyBoard()
    {
        Piece[][] newBoard = new Piece[8][8];

        for(int row = 0; row < board.length; row ++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                newBoard[row][col] = board[row][col];
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
            {
                Position newPosition = this.doMove(move);

                Square kingSquare = newPosition.getKingSquare(turn); // get square of THIS king

                if (newPosition.isSquareAttacked(kingSquare.file, kingSquare.rank))
                    return null;

                return validMove;
            }
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

    public Square getKingSquare(Colour colour)
    {
        for (int f = 0; f < 8; f++)
        {
            for (int r = 0; r < 8; r++)
            {
                Piece p = getPiece(f, r);
                if (p != null && p instanceof King && p.getColour() == colour) {
                    return new Square(f, r);
                }
            }
        }
        throw new IllegalStateException("King not found"); 
    }

    public boolean isSquareAttacked(int attackedFile, int attackedRank)
    {
        for (int f = 0; f < 8; f++)
        {
            for (int r = 0; r < 8; r++)
            {
                Piece piece = getPiece(f, r);

                if (piece != null && piece.getColour() == turn) // for next posotion, it's colour is correct to check if the opposite king is attacked
                {
                    if (piece.isAttackingSquare(this, f, r, attackedFile, attackedRank))
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

    // helper for certain piece isAttackingSquare()
    public boolean isClearPath(int fromFile, int fromRank, int toFile, int toRank)
    {
        int fileStep = Integer.signum(toFile - fromFile);
        int rankStep = Integer.signum(toRank - fromRank);
        int f = fromFile + fileStep;
        int r = fromRank + rankStep;

        while (f != toFile || r != toRank)
        {
            if (getPiece(f, r) != null)
                return false;
            f += fileStep;
            r += rankStep;
        }
        return true;
    }
}
