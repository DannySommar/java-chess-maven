package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.EnPassantMove; // will test it later when doing Game tests.
import chess.core.move.Move;
import chess.core.move.NormalMove;
import chess.core.move.PromotionMove;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class PawnTest
{
    private Position position;
    private Pawn whitePawn;
    private Pawn blackPawn;

    @BeforeEach
    void setUp()
    {
        position = new Position(new Piece[8][8], Colour.WHITE);
        whitePawn = new Pawn(Colour.WHITE);
        blackPawn = new Pawn(Colour.BLACK);
    }

    @Test
    void whitePawnInitialMoves()
    {
        position.placePiece(whitePawn, 4, 1);
        List<Move> moves = whitePawn.generateValidMoves(position, 4, 1);
        
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new NormalMove(4, 1, 4, 2))); // normal
        assertTrue(moves.contains(new NormalMove(4, 1, 4, 3))); // double
    }

    @Test
    void blackPawnInitialMoves()
    {
        position = new Position(new Piece[8][8], Colour.BLACK);
        position.placePiece(blackPawn, 4, 6);
        List<Move> moves = blackPawn.generateValidMoves(position, 4, 6);
        
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new NormalMove(4, 6, 4, 5)));
        assertTrue(moves.contains(new NormalMove(4, 6, 4, 4)));
    }

    @Test
    void pawnNormalMovement()
    {
        position.placePiece(whitePawn, 3, 4);
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertEquals(1, moves.size());
        assertTrue(moves.contains(new NormalMove(3, 4, 3, 5)));
    }

    @Test
    void pawnCaptureMoves()
    {
        position.placePiece(whitePawn, 4, 3);
        position.placePiece(new Pawn(Colour.BLACK), 3, 4);
        position.placePiece(new Pawn(Colour.BLACK), 5, 4); 
        
        List<Move> moves = whitePawn.generateValidMoves(position, 4, 3);
        
        assertEquals(3, moves.size());
        assertTrue(moves.contains(new NormalMove( 4, 3, 4, 4))); // forward
        assertTrue(moves.contains(new NormalMove( 4, 3, 3, 4))); //taketh
        assertTrue(moves.contains(new NormalMove( 4, 3, 5, 4)));
    }

    @Test
    void pawnCaptureSide()
    {
        position.placePiece(whitePawn, 0, 3);
        position.placePiece(new Pawn(Colour.BLACK), 1, 4);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 0, 3);
        
        assertEquals(2, moves.size());
        assertFalse(moves.contains(new NormalMove(0, 3, -1, 4)));
        assertTrue(moves.contains(new NormalMove(0, 3, 0, 4)));
        assertTrue(moves.contains(new NormalMove(0, 3, 1, 4)));
    }

    @Test
    void pawnBlocked()
    {
        position.placePiece(whitePawn, 4, 3); 
        position.placePiece(new Pawn(Colour.BLACK), 4, 4);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 4, 3);
        
        assertTrue(moves.isEmpty());
    }


    @Test
    void promotionMoves()
    {
        position.placePiece(whitePawn, 6, 6);
        List<Move> moves = whitePawn.generateValidMoves(position, 6, 6);
        
        assertEquals(4, moves.size());
        assertTrue(moves.stream().allMatch(m -> m instanceof PromotionMove));

        assertTrue(moves.contains(new PromotionMove(6, 6, 6, 7, new Queen(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 6, 6, 7, new Rook(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 6, 6, 7, new Bishop(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 6, 6, 7, new Horse(Colour.WHITE))));
    }

    @Test
    void promotionWithCapture()
    {
        position.placePiece(whitePawn, 4, 6);
        position.placePiece(new Pawn(Colour.BLACK), 3, 7);
        position.placePiece(new Pawn(Colour.BLACK), 5, 7);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 4, 6);
        
        assertEquals(12, moves.size()); // 3 squares to promote
        
        // Verify capture promotions
        assertTrue(moves.contains(new PromotionMove(4, 6, 3, 7, new Queen(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(4, 6, 5, 7, new Rook(Colour.WHITE))));
    }

    @Test
    void doesNotGenerateInvalidMoves()
    {
        position.placePiece(whitePawn, 4, 3);
        position.placePiece(new Pawn(Colour.WHITE), 3, 4);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertFalse(moves.contains(new NormalMove(4, 3, 3, 4))); // cant take friend
        assertFalse(moves.contains(new NormalMove(4, 3, 4, 2))); // Can't move back
        assertFalse(moves.contains(new NormalMove(4, 3, 5, 3))); //nor  sideways
    }
}