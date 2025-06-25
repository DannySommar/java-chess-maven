package chess.core.piece;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

public class BishopTest
{
    private Position position;
    private Bishop bishop;

    @BeforeEach
    void setUp()
    {
        position = new Position(new Piece[8][8], Colour.WHITE);
        bishop = new Bishop(Colour.WHITE);

        position.placePiece(bishop, 3, 3);
    }

     @Test
    void validDiagonalMove()
    {
        assertTrue(bishop.isValidMove(position, new Move(3, 3, 6, 6)));
        assertTrue(bishop.isValidMove(position, new Move(3, 3, 2, 2)));
        assertTrue(bishop.isValidMove(position, new Move(3, 3, 4, 2)));
        assertTrue(bishop.isValidMove(position, new Move(3, 3, 1, 5)));
    }

    @Test
    void invalidStraightMove()
    {
        assertFalse(bishop.isValidMove(position, new Move(3, 3, 4, 6)));
        assertFalse(bishop.isValidMove(position, new Move(3, 3, 6, 4)));
    }

    @Test
    void blockedPath()
    {
        position.placePiece(new Pawn(Colour.WHITE), 5, 5);
        assertFalse(bishop.isValidMove(position, new Move(3, 3, 6, 6)));
    }

    @Test
    void captureEnemy()
    {
        position.placePiece(new Pawn(Colour.BLACK), 6, 6);
        assertTrue(bishop.isValidMove(position, new Move(3, 3, 6, 6)));
    }

}
