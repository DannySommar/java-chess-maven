package chess.core.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.CastlingRights;
import chess.core.Colour;
import chess.core.Position;
import chess.core.game.*;
import chess.core.move.*;

public class KingTest
{
    private Position position;
    private King king;
    
    @BeforeEach
    void setUp()
    {
        CastlingRights wCastlingRights = new CastlingRights(4, 7, 0, false, false);
        CastlingRights bCastlingRights = new CastlingRights(4, 7, 0, false, false);

        position = new Position(new Piece[8][8], Colour.WHITE, wCastlingRights, bCastlingRights, -1);
        king = new King(Colour.WHITE);
        position.placePiece(king, 4, 4);
    }

    @Test
    void generatesAll8MovesFromCenter()
    {
        List<Move> moves = king.generateValidMoves(position, 4, 4);
        
        assertEquals(8, moves.size());
        
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 3)));
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 4)));
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 5)));
        assertTrue(moves.contains(new NormalMove(4, 4, 4, 3)));
        assertTrue(moves.contains(new NormalMove(4, 4, 4, 5)));
        assertTrue(moves.contains(new NormalMove(4, 4, 5, 3)));
        assertTrue(moves.contains(new NormalMove(4, 4, 5, 4)));
        assertTrue(moves.contains(new NormalMove(4, 4, 5, 5)));
    }

    @Test
    void generatesFewerMovesFromCorner()
    {
        position.placePiece(king, 0, 0);
        List<Move> moves = king.generateValidMoves(position, 0, 0);
        
        assertEquals(3, moves.size());
        assertTrue(moves.contains(new NormalMove(0, 0, 0, 1)));
        assertTrue(moves.contains(new NormalMove(0, 0, 1, 0)));
        assertTrue(moves.contains(new NormalMove(0, 0, 1, 1)));
    }

    //@Test
    void doesNotMoveToAttackedSquares()
    {
        position.placePiece(new Rook(Colour.BLACK), 4, 7);
        List<Move> moves = king.generateValidMoves(position, 4, 4);
        
        // Moves in line of fire. sae problem as when othe rpiace getting pinned. will trhing something about wit with position method ad not piece.
        assertFalse(moves.contains(new NormalMove(4, 4, 4, 5)));
        
        // to the side
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 4)));
        assertTrue(moves.contains(new NormalMove(4, 4, 5, 4)));
    }

    @Test
    void canCaptureEnemyPieces()
    {
        position.placePiece(new Pawn(Colour.BLACK), 5, 5);
        position.placePiece(new Pawn(Colour.BLACK), 4, 5);
        List<Move> moves = king.generateValidMoves(position, 4, 4);
        
        assertTrue(moves.contains(new NormalMove(4, 4, 5, 5)));
        
        assertTrue(moves.contains(new NormalMove(4, 4, 4, 5)));
    }

    @Test
    void blockedByFriendlyPieces()
    {
        position.placePiece(new Pawn(Colour.WHITE), 5, 5);
        position.placePiece(new Pawn(Colour.WHITE), 4, 5);
        List<Move> moves = king.generateValidMoves(position, 4, 4);
        
        assertFalse(moves.contains(new NormalMove(4, 4, 5, 5)));
        assertFalse(moves.contains(new NormalMove(4, 4, 4, 5)));
        
        // should not be blocked by nothing idk anymore 
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 5)));
    }

    @Test
    void generatesCorrectNumberOfMovesFromEdge()
    {
        position.placePiece(king, 0, 4);
        List<Move> moves = king.generateValidMoves(position, 0, 4);
        
        assertEquals(5, moves.size());
        assertTrue(moves.contains(new NormalMove(0, 4, 0, 3)));
        assertTrue(moves.contains(new NormalMove(0, 4, 0, 5)));
        assertTrue(moves.contains(new NormalMove(0, 4, 1, 3)));
        assertTrue(moves.contains(new NormalMove(0, 4, 1, 4)));
        assertTrue(moves.contains(new NormalMove(0, 4, 1, 5)));
    }

    @Test
    void doesNotGenerateMovesOutsideBoard()
    {
        position.placePiece(king, 0, 0);
        List<Move> moves = king.generateValidMoves(position, 0, 0);
        
        
        assertFalse(moves.stream().anyMatch(m -> m.toFile < 0 && m.toFile >= 8 && m.toRank < 0 && m.toRank >= 8));
    }

    @Test
    void generatesCastlingMoves()
    {
        King wKing = new King(Colour.WHITE);
        King bKing = new King(Colour.BLACK);
        Rook wKingsideRook = new Rook(Colour.WHITE);
        Rook wQueensideRook = new Rook(Colour.WHITE);

        Piece[][] board = new Piece[8][8];
        board[4][0] = wKing;
        board[0][0] = wKingsideRook;
        board[7][0] = wQueensideRook;
        
        position = new Position(
            board, 
            Colour.WHITE,
            CastlingRights.standard(),
            CastlingRights.standard(),
            -1
        );

        List<Move> moves = wKing.generateValidMoves(position, 4, 0);
        
        
        assertTrue(moves.stream().anyMatch(m -> m instanceof CastlingMove && ((CastlingMove)m).isKingSide()));
        assertTrue(moves.stream().anyMatch(m -> m instanceof CastlingMove && !((CastlingMove)m).isKingSide()));

        board[2][7] = new Rook(Colour.BLACK);
        board[6][7] = new Rook(Colour.BLACK);
        assertFalse(moves.stream().anyMatch(m -> m instanceof CastlingMove));
    }
}
