package chess.core;

import java.util.ArrayList;
import java.util.List;

import chess.core.game.*;
import chess.core.move.*;
import chess.core.piece.*;

public class Position
{
    //private static final int BOARD_SIZE = 8;

    private Piece[][] board = new Piece[8][8]; // empty board
    private Colour turn;

    private CastlingRights whiteCastling;
    private CastlingRights blackCastling;
    protected int enPassantFile; // we only need the file, as we can deterine the rank by Colour.WHITE ? 4 : 6
     

    public Position(Piece[][] board, Colour turn, CastlingRights whiteCastling, CastlingRights blackCastling, int enPassantFile)
    {
        this.board = board;
        this.turn = turn;
        this.whiteCastling = whiteCastling;
        this.blackCastling = blackCastling;
        this.enPassantFile = enPassantFile;
    }

    public Position(Piece[][] board, Colour turn)
    {
        this(board, turn, CastlingRights.standard(), CastlingRights.standard(), -1);
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
        CastlingRights newWhiteCastling = whiteCastling;
        CastlingRights newBlackCastling = blackCastling;

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
                Piece rook = newBoard[move.fromFile][getKingsideRookFile()];
                newBoard[move.fromFile][getKingsideRookFile()] = null;
                newBoard[move.fromFile][move.fromRank] = null;

                newBoard[move.fromFile][6] = piece;
                newBoard[move.fromFile][5] = rook;
            }
            else{
                Piece rook = newBoard[move.fromFile][getQueensideRookFile()];
                newBoard[move.fromFile][getQueensideRookFile()] = null;
                newBoard[move.fromFile][move.fromRank] = null;
                
                newBoard[move.fromFile][2] = piece;
                newBoard[move.fromFile][3] = rook;
            }
        }
        else if (move instanceof PromotionMove)
        {
            System.out.println("promotion move");
            piece = ((PromotionMove)move).getPromotedTo();
            newBoard[move.toFile][move.toRank] = piece;
            newBoard[move.fromFile][move.fromRank] = null;
        }
        else if (move instanceof EnPassantMove)
        {
            newBoard[move.toFile][move.toRank] = piece;
            newBoard[move.fromFile][move.fromRank] = null;
            newBoard[move.fromFile][enPassantFile] = null;
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
                newWhiteCastling = whiteCastling.disableBoth();
            else 
                newBlackCastling = blackCastling.disableBoth();
        }
        else if (piece instanceof Pawn && Math.abs(move.toFile - move.fromFile) == 2)
        {
            newEnPassantFile = move.fromRank;
        }
        else if (piece instanceof Rook)
        {
            if (piece.getColour() == Colour.WHITE)
            {
                System.out.println("white rook moved fromrank: " + move.fromRank);
                if (move.fromRank == whiteCastling.getKingsideRookFile())
                {
                    newWhiteCastling = whiteCastling.disableKingside();
                } 
                else if (move.fromRank == whiteCastling.getQueensideRookFile())
                {
                    newWhiteCastling = whiteCastling.disableQueenside();
                }
            }
            else
            {
                if (move.fromRank == blackCastling.getKingsideRookFile())
                {
                    newBlackCastling = blackCastling.disableKingside();
                }
                else if (move.fromRank == blackCastling.getQueensideRookFile())
                {
                    newBlackCastling = blackCastling.disableQueenside();
                }
            }
        }


        Position newPosition = new Position(
            newBoard,
            newTurn,
            newWhiteCastling,
            newBlackCastling,
            newEnPassantFile
        );

        // System.out.println("next position:");
        // System.out.println(move.toString());
        // newPosition.printBoard();

        return newPosition;
    }

    public Piece[][] deepCopyBoard()
    {
        Piece[][] newBoard = new Piece[8][8];

        for(int row = 0; row < board.length; row ++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                newBoard[row][col] = board[row][col];

                // Only if pieces themselves suddently becme mutable
                // Piece oldPiece = board[row][col];
                // if (oldPiece != null)
                // {
                //     Piece newPiece = oldPiece.copy();
                //     newBoard[row][col] = newPiece;
                // }
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
            // if (move instanceof PromotionMove)
            // {
            //     System.out.println("returning it intopromotion move");
            //     move = (PromotionMove)move;
            // }
            System.out.println(validMove);
            System.out.println("chosen move: " + move);
            if (validMove.matches(move))
            {
                Position newPosition = this.doMove(validMove);

                Square kingSquare = newPosition.getKingSquare(turn); // get square of THIS king
                System.out.println("king file: " + kingSquare.file + " King rank: " + kingSquare.rank);

                if (newPosition.isSquareAttacked(kingSquare.file, kingSquare.rank, turn.getOpposite()))
                {
                    System.out.println("new position where cant make move cause king attacked");
                    newPosition.printBoard();
                    System.out.println(move.toString());
                    System.out.println("king is attacked");
                    return null;
                }
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

    public boolean isSquareAttacked(int attackedFile, int attackedRank, Colour playersTurn)
    {
        for (int f = 0; f < 8; f++)
        {
            for (int r = 0; r < 8; r++)
            {
                Piece piece = getPiece(f, r);

                if (piece != null && piece.getColour() == playersTurn) // for next posotion, it's colour is correct to check if the opposite king is attacked
                {
                    if (piece.isAttackingSquare(this, f, r, attackedFile, attackedRank))
                    {
                        //System.out.println(piece.toString());
                        return true;
                    }
                }

            }
        }

        return false;
    }

    public int getEnPassantFile()
    {
        return enPassantFile;
    }

    // will change later
    public boolean canWhiteCastleKingSide()
    {
        return whiteCastling.isKingsideAllowed();
    }

    public boolean canWhiteCastleQueenSide()
    {
        return whiteCastling.isQueensideAllowed();
    }

    public boolean canBlackCastleKingSide()
    {
        return blackCastling.isKingsideAllowed();
    }

    public boolean canBlackCastleQueenSide()
    {
        return blackCastling.isQueensideAllowed();
    }

    public boolean canCastleQueenSide(Colour c)
    {
        if (c == Colour.WHITE)
            return canWhiteCastleQueenSide();
            return canBlackCastleQueenSide();
    }

    public boolean canCastleKingSide(Colour c)
    {
        if (c == Colour.WHITE)
            return canWhiteCastleKingSide();
            return canBlackCastleKingSide();
    }

    public int getKingStartFile()
    {
        return blackCastling.getKingFile();
    }

    public int getKingsideRookFile()
    {
        return blackCastling.getKingsideRookFile();
    }
    public int getQueensideRookFile()
    {
        return blackCastling.getQueensideRookFile();
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

    // for castling
    public boolean isSafePath(int fromFile, int fromRank, int toFile, int toRank)
    {
        int fileStep = Integer.signum(toFile - fromFile);
        int rankStep = Integer.signum(toRank - fromRank);
        int f = fromFile + fileStep;
        int r = fromRank + rankStep;

        while (f != toFile || r != toRank)
        {
            if (getPiece(f, r) != null || isSquareAttacked(f, r, turn.getOpposite()))
                return false;
            f += fileStep;
            r += rankStep;
        }
        return true;
    }
}
