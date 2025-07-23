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
        position.placePiece(whitePawn, 1, 4);
        List<Move> moves = whitePawn.generateValidMoves(position, 1, 4);
        
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new NormalMove(1, 4, 2, 4))); // normal
        assertTrue(moves.contains(new NormalMove(1, 4, 3, 4))); // double
    }

    @Test
    void blackPawnInitialMoves()
    {
        position = new Position(new Piece[8][8], Colour.BLACK);
        position.placePiece(blackPawn, 6, 4);
        List<Move> moves = blackPawn.generateValidMoves(position, 6, 4);
        
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new NormalMove(6, 4, 5, 4)));
        assertTrue(moves.contains(new NormalMove(6, 4, 4, 4)));
    }

    @Test
    void pawnNormalMovement()
    {
        position.placePiece(whitePawn, 3, 4);
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertEquals(1, moves.size());
        assertTrue(moves.contains(new NormalMove(3, 4, 4, 4)));
    }

    @Test
    void pawnCaptureMoves()
    {
        position.placePiece(whitePawn, 3, 4);
        position.placePiece(new Pawn(Colour.BLACK), 4, 3);
        position.placePiece(new Pawn(Colour.BLACK), 4, 5); 
        
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertEquals(3, moves.size());
        assertTrue(moves.contains(new NormalMove(3, 4, 4, 4))); // forward
        assertTrue(moves.contains(new NormalMove(3, 4, 4, 3))); //taketh
        assertTrue(moves.contains(new NormalMove(3, 4, 4, 5)));
    }

    @Test
    void pawnCaptureSide()
    {
        position.placePiece(whitePawn, 3, 0);
        position.placePiece(new Pawn(Colour.BLACK), 4, 1);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 0);
        
        assertEquals(2, moves.size());
        assertFalse(moves.contains(new NormalMove(3, 0, 4, -1)));
        assertTrue(moves.contains(new NormalMove(3, 0, 4, 0)));
        assertTrue(moves.contains(new NormalMove(3, 0, 4, 1)));
    }

    @Test
    void pawnBlocked()
    {
        position.placePiece(whitePawn, 3, 4); 
        position.placePiece(new Pawn(Colour.BLACK), 4, 4);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertTrue(moves.isEmpty());
    }


    @Test
    void promotionMoves()
    {
        position.placePiece(whitePawn, 6, 4);
        List<Move> moves = whitePawn.generateValidMoves(position, 6, 4);
        
        assertEquals(4, moves.size());
        assertTrue(moves.stream().allMatch(m -> m instanceof PromotionMove));

        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 4, new Queen(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 4, new Rook(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 4, new Bishop(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 4, new Horse(Colour.WHITE))));
    }

    @Test
    void promotionWithCapture()
    {
        position.placePiece(whitePawn, 6, 4);
        position.placePiece(new Pawn(Colour.BLACK), 7, 3);
        position.placePiece(new Pawn(Colour.BLACK), 7, 5);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 6, 4);
        
        assertEquals(12, moves.size()); // 3 squares to promote
        
        // Verify capture promotions
        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 3, new Queen(Colour.WHITE))));
        assertTrue(moves.contains(new PromotionMove(6, 4, 7, 5, new Rook(Colour.WHITE))));
    }

    @Test
    void doesNotGenerateInvalidMoves()
    {
        position.placePiece(whitePawn, 3, 4);
        position.placePiece(new Pawn(Colour.WHITE), 4, 3);
        
        List<Move> moves = whitePawn.generateValidMoves(position, 3, 4);
        
        assertFalse(moves.contains(new NormalMove(3, 4, 4, 3))); // cant take friend
        assertFalse(moves.contains(new NormalMove(3, 4, 2, 4))); // Can't move back
        assertFalse(moves.contains(new NormalMove(3, 4, 3, 5))); //nor  sideways
    }
}