package chess.core.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

public class QueenTest
{
    private Position position;
    private Queen queen;

    @BeforeEach
    void setUp()
    {
        position = new Position(new Piece[8][8], Colour.WHITE);
        queen = new Queen(Colour.WHITE);

        position.placePiece(queen, 3, 3);
    }

    @Test
    void generatesAllFreeMoves()
    {
        List<Move> moves = queen.generateValidMoves(position, 3, 3);
        
        // Straaight
        for (int file = 0; file < 8; file++)
        {
            if (file != 3)
            {
                assertTrue(moves.contains(new NormalMove(3, 3, file, 3)));
            }
        }
        
        for (int rank = 0; rank < 8; rank++)
        {
            if (rank != 3)
            {
                assertTrue(moves.contains(new NormalMove(3, 3, 3, rank)));
            }
        }

        // Gay
        assertTrue(moves.contains(new NormalMove(3, 3, 0, 0)));
        assertTrue(moves.contains(new NormalMove(3, 3, 7, 7)));
        assertTrue(moves.contains(new NormalMove(3, 3, 0, 6)));
        assertTrue(moves.contains(new NormalMove(3, 3, 6, 0)));
       
        assertTrue(moves.contains(new NormalMove(3, 3, 1, 1)));
        assertTrue(moves.contains(new NormalMove(3, 3, 5, 5)));
        assertTrue(moves.contains(new NormalMove(3, 3, 1, 5)));
        assertTrue(moves.contains(new NormalMove(3, 3, 5, 1)));
    }

    @Test
    void doesNotGenerateInvalidMoves()
    {
        List<Move> moves = queen.generateValidMoves(position, 3, 3);
        
        assertFalse(moves.contains(new NormalMove(3, 3, 2, 5))); // like a horse (also maybe add a future Knock piece from "Almost Chess")
        assertFalse(moves.contains(new NormalMove(3, 3, 3, 3)));
        assertFalse(moves.contains(new NormalMove(3, 3, 0, 7)));
    }

    @Test
    void doesNotGenerateBlockedByFriendlyPieces()
    {
        position.placePiece(new Pawn(Colour.WHITE), 3, 5);
        position.placePiece(new Pawn(Colour.WHITE), 5, 5);
        
        List<Move> moves = queen.generateValidMoves(position, 3, 3);
        
        // can't take nor jump
        assertFalse(moves.contains(new NormalMove(3, 3, 3, 5)));
        assertFalse(moves.contains(new NormalMove(3, 3, 3, 6)));
        assertFalse(moves.contains(new NormalMove(3, 3, 5, 5)));
        assertFalse(moves.contains(new NormalMove(3, 3, 6, 6)));
        
        // fine untill then
        assertTrue(moves.contains(new NormalMove(3, 3, 3, 4)));
        assertTrue(moves.contains(new NormalMove(3, 3, 4, 4)));
    }

    @Test
    void generatesCaptureMoves()
    {
        position.placePiece(new Pawn(Colour.BLACK), 5, 5);
        position.placePiece(new Pawn(Colour.BLACK), 3, 1);
        
        List<Move> moves = queen.generateValidMoves(position, 3, 3);
        
        // can take
        assertTrue(moves.contains(new NormalMove(3, 3, 5, 5)));
        assertTrue(moves.contains(new NormalMove(3, 3, 3, 1)));
        
        // but still can't jump
        assertFalse(moves.contains(new NormalMove(3, 3, 6, 6)));
        assertFalse(moves.contains(new NormalMove(3, 3, 3, 0)));
    }

    @Test
    void generatesCorrectMovesFromBoardEdge()
    {
        position.placePiece(new Queen(Colour.BLACK), 7, 0);

        List<Move> moves = queen.generateValidMoves(position, 7, 0);

        assertEquals(21, moves.size());
        
        
        assertTrue(moves.contains(new NormalMove(7, 0, 0, 0)));
        assertTrue(moves.contains(new NormalMove(7, 0, 7, 7)));
        assertTrue(moves.contains(new NormalMove(7, 0, 0, 7)));
        
        assertFalse(moves.contains(new NormalMove(7, 0, 3, 3)));
        assertFalse(moves.contains(new NormalMove(7, 0, 8, 0)));
        assertFalse(moves.contains(new NormalMove(7, 0, 0, 8)));
        assertFalse(moves.contains(new NormalMove(7, 0, 8, 8)));
        assertFalse(moves.contains(new NormalMove(7, 0, 7, -1)));
        assertFalse(moves.contains(new NormalMove(7, 0, 8, -1)));
    }


    @Test
    void generates27MovesFromCenterOnEmptyBoard()
    {
        List<Move> moves = queen.generateValidMoves(position, 3, 3);

        assertEquals(27, moves.size());
    }
}
