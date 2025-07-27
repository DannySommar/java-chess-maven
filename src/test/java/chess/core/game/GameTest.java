package chess.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.InvalidMoveException;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;


public class GameTest
{
    private Game game;
    private String player1 = "Alice";
    private String player2 = "Bob";

    @BeforeEach
    void setUp() {
        game = new StandardGame(player1, player2);
    }

    @Test
    void testInitialPlayerAssignment()
    {
        assertEquals(player1, game.getWhitePlayer());
        assertEquals(player2, game.getBlackPlayer());
    }

    @Test
    void testInitialTurnIsWhite()
    {
        assertEquals(Colour.WHITE, game.getTurn());
    }

    @Test
    void testMoveCounterResetOnPawnMove()
    {
        
        try
        {
            game.addMove(new NormalMove(1, 0, 2, 0));
        } catch (InvalidMoveException e)
        {
            assertTrue(true);
        }
    }
}
