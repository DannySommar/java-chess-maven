package chess.core.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.core.Colour;
import chess.core.Position;
import chess.core.move.Move;
import chess.core.move.NormalMove;

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
    void generatesValidDiagonalMoves()
    {
        List<Move> moves = bishop.generateValidMoves(position, 3, 3);
        
        assertTrue(moves.contains(new NormalMove(3, 3, 6, 6)));
        assertTrue(moves.contains(new NormalMove(3, 3, 2, 2)));
        assertTrue(moves.contains(new NormalMove(3, 3, 4, 2)));
        assertTrue(moves.contains(new NormalMove(3, 3, 1, 5)));

        assertFalse(moves.stream().anyMatch(m -> m.toRank == 3));
        assertFalse(moves.stream().allMatch(m -> m.toRank == 3));
    }

    @Test
    void doesNotGenerateMovesBeyondBlockedPaths()
    {
        position.placePiece(new Pawn(Colour.WHITE), 5, 5);
        List<Move> moves = bishop.generateValidMoves(position, 3, 3);

        assertFalse(moves.contains(new NormalMove(3, 3, 6, 6)));
        assertTrue(moves.contains(new NormalMove(3, 3, 4, 4)));
    }

    @Test
    void generatesCaptureMoves()
    {
        position.placePiece(new Pawn(Colour.BLACK), 6, 6);
        List<Move> moves = bishop.generateValidMoves(position, 3, 3);
        
        assertTrue(moves.contains(new NormalMove(3, 3, 6, 6)));
    }

    @Test
    void doesNotGenerateMovesOutsideBoard()
    {
        List<Move> moves = bishop.generateValidMoves(position, 3, 3);
        
        
        assertTrue(moves.stream().allMatch(m -> m.toFile >= 0 && m.toFile < 8 && m.toRank >= 0 && m.toRank < 8));
    }

    @Test
    void doesNotGenerateMovesWhileKingInCheck() // will need to somehow change Position.getLegalMoveOrNull for this becuase its important I set this as a reminder. so Include the valid check in getLegalMoveOrNull so that it makes sure it's legal there.
    {
        position.placePiece(new King(Colour.WHITE), 0, 0);
        position.placePiece(new Rook(Colour.BLACK), 7, 0);
        var possibleMoves = bishop.generateValidMoves(position, 3, 3);
        
        List<Move> moves = new ArrayList<>();
        for (Move move : possibleMoves)
        {
            if (position.returnLegalMoveOrNull(move) != null)
                moves.add(move);
        }
        
        assertFalse(moves.contains(new NormalMove(3, 3, 4, 4)));
    }

    @Test
    void generatesCorrectNumberOfMovesFromCenter()
    {
        assertEquals(13, bishop.generateValidMoves(position, 3, 3).size());
    }

}
