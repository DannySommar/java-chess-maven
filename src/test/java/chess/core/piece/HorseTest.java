package chess.core.piece;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HorseTest
{
    private Position position;
    private Horse horse;

    @BeforeEach
    void setUp() {
        // Initialize empty board with white to move
        position = new Position(new Piece[8][8], Colour.WHITE);
        horse = new Horse(Colour.WHITE);
        position.placePiece(horse, 4, 4); // Default position at e5
    }

    @Test
    void canMoveAllEightDirectionsWhenUnblocked() {
        assertEquals(8, horse.generateValidMoves(position, 4, 4).size());
        assertTrue(position.isMoveLegal(new Move(4, 4, 6, 5)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 5, 6)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 2, 5)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 3, 6)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 6, 3)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 5, 2)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 2, 3)));
        assertTrue(position.isMoveLegal(new Move(4, 4, 3, 2)));
    }

    @Test
    void cannotMoveToInvalidSquares() {
        assertFalse(position.isMoveLegal(new Move(4, 4, 4, 5)));
        assertFalse(position.isMoveLegal(new Move(4, 4, 5, 5)));
        assertFalse(position.isMoveLegal(new Move(4, 4, 4, 4)));
    }

    @Test
    void edgeOfBoardHasFewerOptions() {
        position.placePiece(horse, 0, 0);
        assertEquals(2, horse.generateValidMoves(position, 0, 0).size());
        assertTrue(position.isMoveLegal(new Move(0, 0, 1, 2)));
        assertTrue(position.isMoveLegal(new Move(0, 0, 2, 1)));
    }

    @Test
    void canCaptureEnemyButNotFriendly() {
        position.placePiece(new Pawn(Colour.BLACK), 6, 5);
        position.placePiece(new Pawn(Colour.WHITE), 5, 6);
        
        assertTrue(position.isMoveLegal(new Move(4, 4, 6, 5)));
        assertFalse(position.isMoveLegal(new Move(4, 4, 5, 6)));
    }
}
