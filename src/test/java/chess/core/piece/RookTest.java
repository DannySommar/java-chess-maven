package chess.core.piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

public class RookTest
{

    private Position position;
    private Rook rook;

    @BeforeEach
    void setUp()
    {
        position = new Position(new Piece[8][8], Colour.WHITE);
        rook = new Rook(Colour.WHITE);
    }

    @Test
    void generatesHorizontalAndVerticalMoves()
    {
        position.placePiece(rook, 0, 0);
        List<Move> moves = rook.generateValidMoves(position, 0, 0);
        
        assertEquals(14, moves.size());
        
        
        for (int file = 1; file < 8; file++)
            assertTrue(moves.contains(new NormalMove(0, 0, file, 0)));
        
        for (int rank = 1; rank < 8; rank++)
            assertTrue(moves.contains(new NormalMove(0, 0, 0, rank)));
        
        assertFalse(moves.contains(new NormalMove(0, 0, 1, 1)));
    }
    
    @Test
    void doesNotGenerateMovesBeyondBlockedPaths()
    {
        position.placePiece(rook, 4, 4);
        position.placePiece(new Pawn(Colour.WHITE), 4, 6);
        position.placePiece(new Pawn(Colour.BLACK), 2, 4);
        
        List<Move> moves = rook.generateValidMoves(position, 4, 4);
        
        // can't take fren nor go past
        assertTrue(moves.contains(new NormalMove(4, 4, 4, 5)));
        assertFalse(moves.contains(new NormalMove(4, 4, 4, 6)));
        assertFalse(moves.contains(new NormalMove(4, 4, 4, 7)));
        
        for (int rank = 3; rank >= 0; rank--)
            assertTrue(moves.contains(new NormalMove(4, 4, 4, rank)));
        
        // can take but not go past
        assertTrue(moves.contains(new NormalMove(4, 4, 3, 4)));
        assertTrue(moves.contains(new NormalMove(4, 4, 2, 4)));
        assertFalse(moves.contains(new NormalMove(4, 4, 1, 4)));
        assertFalse(moves.contains(new NormalMove(4, 4, 0, 4)));
        
        for (int file = 5; file < 8; file++)
            assertTrue(moves.contains(new NormalMove(4, 4, file, 4)));
    }

    @Test
    void generatesAll14MovesFromEverySquareOnEmptyBoard()
    {
        // Test all 64 squares
        for (int file = 0; file < 8; file++)
        {
            for (int rank = 0; rank < 8; rank++)
            {
                position.placePiece(rook, file, rank);
                List<Move> moves = rook.generateValidMoves(position, file, rank);
                
                assertEquals(14, moves.size());
                
                position.placePiece(null, file, rank);
            }
        }
    }

}
