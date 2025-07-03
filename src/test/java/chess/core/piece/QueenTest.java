// package chess.core.piece;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import chess.core.Colour;
// import chess.core.Position;
// import chess.core.move.Move;

// public class QueenTest
// {
//     private Position position;
//     private Queen queen;

//     @BeforeEach
//     void setUp()
//     {
//         position = new Position(new Piece[8][8], Colour.WHITE);
//         queen = new Queen(Colour.WHITE);

//         position.placePiece(queen, 3, 3);
//     }

//     @Test
//     void queenCanMoveHorizontally()
//     {

//         assertTrue(position.isMoveLegal(new Move(3, 3, 3, 0)));
//         assertTrue(position.isMoveLegal(new Move(3, 3, 0, 3)));

//         assertFalse(position.isMoveLegal(new Move(3, 3, 3, 3)));
//         assertFalse(position.isMoveLegal(new Move(3, 3, 1, 2)));
//     } 

//     @Test
//     void queenCanMoveDiagonally() {
//         assertTrue(position.isMoveLegal(new Move(3, 3, 0, 0)));
//         assertTrue(position.isMoveLegal(new Move(3, 3, 7, 7)));
//         assertFalse(position.isMoveLegal(new Move(3, 3, 2, 5)));
//     }

//     @Test
//     void queenBlockedByFriendly()
//     {
//         position.placePiece(new Pawn(Colour.WHITE), 3, 5);
//         assertFalse(position.isMoveLegal(new Move(3, 3, 3, 6)));
//         assertTrue(position.isMoveLegal(new Move(3, 3, 3, 4)));
//     }

//     @Test
//     void queenCanCaptureEnemy()
//     {
//         position.placePiece(new Pawn(Colour.BLACK), 5, 5);
//         assertTrue(position.isMoveLegal(new Move(3, 3, 5, 5)));
//         assertFalse(position.isMoveLegal(new Move(3, 3, 6, 6)));
//     }

//     @Test
//     void queenAtBoardEdge()
//     {
//         position.placePiece(queen, 7, 0);
//         assertTrue(position.isMoveLegal(new Move(7, 0, 0, 7)));
//         assertTrue(position.isMoveLegal(new Move(7, 0, 0, 0)));
//         assertFalse(position.isMoveLegal(new Move(7, 0, 1, 2)));
//     }
// }
