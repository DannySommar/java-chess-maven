package chess.desktop;

import chess.core.*;
import chess.core.game.Game;
import chess.core.piece.Piece;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessBoard extends GridPane {
    private static final int SQUARE_SIZE = 80;
    private final Game game;

    public ChessBoard(Game game)
    {
        this.game = game;
        initializeBoard();
    }

    private void initializeBoard()
    {
        getChildren().clear();
        
        for (int rank = 0; rank < 8; rank++)
        {
            for (int file = 0; file < 8; file++)
            {
                Color squareColor = (file + rank) % 2 == 0 ? Color.WHITE : Color.GRAY;
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColor);
                add(square, file, rank);
                
                Piece piece = game.getCurrentPosition().getPiece(7-file, rank);
                if (piece != null)
                {
                    System.out.println(piece.toString());
                    addPiece(piece, file, rank);
                }
                setSquareHandler(square, file, rank); // to make sure square is correct square
            }
        }
    }


    private void addPiece(Piece piece, int rank, int file)
    {
        PieceRenderer pieceView = new PieceRenderer(piece);
        add(pieceView, file, rank);
        pieceView.toFront(); // sometimes squares hide pieces
        
        System.out.println("added piece " + piece.toString() + " to " + file + ", " + rank);
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
}
