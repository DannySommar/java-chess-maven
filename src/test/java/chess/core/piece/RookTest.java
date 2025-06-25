package chess.core.piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;

public class RookTest
{
    @Test
    void rookCanMoveHorizontally()
    {
        Piece[][] empty = new Piece[8][8];
        Position emptyPosition = new Position(empty, Colour.WHITE);

        emptyPosition.placePiece(new Rook(Colour.WHITE), 0, 0);

        assertTrue(emptyPosition.isValidMove(new Move(0, 0, 4, 0)));
        assertTrue(emptyPosition.isValidMove(new Move(0, 0, 0, 7)));

        assertFalse(emptyPosition.isValidMove(new Move(0, 0, 0, 0)));
        assertFalse(emptyPosition.isValidMove(new Move(0, 0, 1, 1)));
    }  
    
    @Test
    void rookCantJump()
    {
        Piece[][] empty = new Piece[8][8];
        Position emptyPosition = new Position(empty, Colour.WHITE);

        emptyPosition.placePiece(new Rook(Colour.WHITE), 0, 0);
        emptyPosition.placePiece(new Pawn(Colour.WHITE), 0, 1);

        assertFalse(emptyPosition.isValidMove(new Move(0, 0, 0, 2)));
        assertFalse(emptyPosition.isValidMove(new Move(0, 0, 0, 1)));
    }

    @Test 
    void rookCanTake()
    {
        Piece[][] empty = new Piece[8][8];
        Position emptyPosition = new Position(empty, Colour.WHITE);

        emptyPosition.placePiece(new Rook(Colour.WHITE), 4, 6);
        emptyPosition.placePiece(new Pawn(Colour.BLACK), 4, 1);

        assertTrue(emptyPosition.isValidMove(new Move(4, 6, 4, 1)));
    }

}
