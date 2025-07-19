package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class HorseTest
{
    private Position position;
    private Horse horse;

    @BeforeEach
    void setUp()
    {
        position = new Position(new Piece[8][8], Colour.WHITE);
        horse = new Horse(Colour.WHITE);
        position.placePiece(horse, 4, 4);
    }

    @Test
    void canMoveAllEightDirectionsWhenUnblocked() 
    {
        List<Move> moves = horse.generateValidMoves(position, 4, 4);
        assertEquals(8, moves.size());
        
        assertTrue(moves.contains( new NormalMove(4, 4, 6, 5)));
        assertTrue(moves.contains( new NormalMove(4, 4, 5, 6)));
        assertTrue(moves.contains( new NormalMove(4, 4, 2, 5)));
        assertTrue(moves.contains( new NormalMove(4, 4, 3, 6)));
        assertTrue(moves.contains( new NormalMove(4, 4, 6, 3)));
        assertTrue(moves.contains( new NormalMove(4, 4, 5, 2)));
        assertTrue(moves.contains( new NormalMove(4, 4, 2, 3)));
        assertTrue(moves.contains( new NormalMove(4, 4, 3, 2)));
    }

    @Test
    void doesNotGenerateInvalidMoves()
    {
        List<Move> moves = horse.generateValidMoves(position, 4, 4);
        
        assertFalse(moves.stream().anyMatch(m -> m.toRank == 4));
        assertFalse(moves.stream().allMatch(m -> m.toRank == 4));
    }

    @Test
    void edgeOfBoardHasFewerOptions()
    {
        position.placePiece(horse, 0, 0);
        List<Move> moves = horse.generateValidMoves(position, 0, 0);
        
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new NormalMove(0, 0, 1, 2)));
        assertTrue(moves.contains(new NormalMove(0, 0, 2, 1)));
    }

    @Test
    void canCaptureEnemyButNotFriendly()
    {
        
        position.placePiece(new Pawn(Colour.BLACK), 6, 5);
        position.placePiece(new Pawn(Colour.WHITE), 5, 6);
        
        List<Move> moves = horse.generateValidMoves(position, 4, 4);
        
        assertTrue(moves.contains(new NormalMove(4, 4, 6, 5)));
        assertFalse(moves.contains(new NormalMove(4, 4, 5, 6)));
    }

    @Test
    void canJumpOverPieces()
    {
        position.placePiece(new Pawn(Colour.WHITE), 5, 4);
        position.placePiece(new Pawn(Colour.WHITE), 5, 5);
        position.placePiece(new Pawn(Colour.WHITE), 4, 5);
        position.placePiece(new Pawn(Colour.WHITE), 3, 5);
        position.placePiece(new Pawn(Colour.WHITE), 3, 4);
        position.placePiece(new Pawn(Colour.WHITE), 3, 3);
        position.placePiece(new Pawn(Colour.WHITE), 4, 3);
        position.placePiece(new Pawn(Colour.WHITE), 5, 3);
        
        List<Move> moves = horse.generateValidMoves(position, 4, 4);
        assertEquals(8, moves.size());
    }
}
