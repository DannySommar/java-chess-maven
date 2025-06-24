package chess.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.core.piece.Piece;

public class PositionTest
{
    
    @Test
    void correctTurn()
    {
        Piece[][] empty = new Piece[8][8];
        Position emptyPosition = new Position(empty, Colour.WHITE);
        assertEquals(emptyPosition.getTurn(), Colour.WHITE);
    }
}
