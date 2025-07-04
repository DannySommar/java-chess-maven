package chess.desktop;

import chess.core.*;
import chess.core.game.Game;
import chess.core.piece.Piece;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessBoard extends GridPane {
    private static final int SQUARE_SIZE = 80;
    private final Game game;
    private StackPane[][] squarePanes = new StackPane[8][8];

    public ChessBoard(Game game)
    {
        this.game = game;
        initializeBoard();
        updatePieces();
    }

    private void initializeBoard()
    {
        getChildren().clear();
        
        for (int rank = 0; rank < 8; rank++)
        {
            for (int file = 0; file < 8; file++)
            {
                StackPane squarePane = new StackPane();
                squarePanes[file][rank] = squarePane;

                Color squareColor = (file + rank) % 2 == 0 ? Color.WHITE : Color.GRAY;
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColor);

                squarePane.getChildren().add(square);
                add(square, file, rank);

                setSquareHandler(square, file, 7-rank); // to make sure square is correct square
            }
        }
    }

    // needs to be a seperste function because lambda wants final variables
    private void setSquareHandler(Rectangle square, final int file, final int rank)
    {
        square.setOnMouseClicked(e -> handleSquareClick(file, rank));
    }

    private void handleSquareClick(int file, int rank)
    {
        System.out.println("square " + (char)('a' + file) + (rank + 1));
    }

    // used for each move. doesn't reset the squares, but replACES the ONE PIECEs
    void updatePieces()
    {
        for (int rank = 0; rank < 8; rank++)
        {
            for (int file = 0; file < 8; file++)
            {
                StackPane squarePane = squarePanes[file][rank];
                
                // remove existing
                if (squarePane.getChildren().size() > 1)
                {
                    squarePane.getChildren().remove(1);
                }
                
                // add new piece
                Piece piece = game.getCurrentPosition().getPiece(7-file, rank);
                if (piece != null)
                {
                    PieceRenderer pieceView = new PieceRenderer(piece);
                    add(pieceView, rank, file);
                    pieceView.toFront(); // sometimes squares hide pieces
                    System.out.println("added piece " + piece.toString() + " to " + rank + ", " + file);
                }
            }
        }
    }


}
