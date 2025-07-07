package chess.desktop;

import chess.core.*;
import chess.core.game.Game;
import chess.core.piece.Piece;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ChessBoard extends GridPane {
    private static final int SQUARE_SIZE = 80;
    private final Game game;
    private  ChessSquare[][] squares = new ChessSquare[8][8];

    private Integer selectedFile = null;
    private Integer selectedRank = null;

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
                Color squareColor = (file + rank) % 2 == 0 ? Color.WHITE : Color.GRAY;
                ChessSquare squarePane = new ChessSquare(squareColor, SQUARE_SIZE);
                squares[file][rank] = squarePane;
                add(squarePane, file, rank);

                setSquareHandler(squarePane, file, 7-rank); // to make sure square is correct square
                
            }
        }
    }

    // needs to be a seperste function because lambda wants final variables
    private void setSquareHandler(ChessSquare square, final int file, final int rank)
    {
        square.setOnMouseClicked(e -> handleSquareClick(file, rank));
    }

    private void handleSquareClick(int file, int rank)
    {
        System.out.println("square " + (char)('a' + file) + (rank + 1));
        Position position = game.getCurrentPosition();
        Piece clickedPiece = position.getPiece(rank, file);


        ///// Very important to do the possible moves correct rn

        highlightSelection(file, 7-rank);

        if (clickedPiece != null)
            System.out.println("clicked on piece " + clickedPiece.toString());
    }

    // used for each move. doesn't reset the squares, but replACES the ONE PIECEs
    void updatePieces()
    {
        for (int rank = 0; rank < 8; rank++)
        {
            for (int file = 0; file < 8; file++)
            {
                ChessSquare squarePane = squares[rank][file];

                Piece piece = game.getCurrentPosition().getPiece(7-file, rank);

                PieceRenderer pieceView = null;
                if (piece != null)
                {
                    pieceView = new PieceRenderer(piece);
                    System.out.println("added piece " + piece.toString() + " to " + rank + ", " + file);
                }
                squarePane.setPiece(pieceView);
                printStackPaneChildren(squarePane, rank, file);
                
            }
        }
    }

    private void highlightSelection(int file, int rank)
    {
        clearSelections();
        
        ChessSquare pane = squares[file][rank];
        Rectangle highlight = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.YELLOW);
        highlight.setOpacity(0.5);
        pane.setHighlight(highlight);
    }

    private void clearSelections()
    {
        for (int f = 0; f < 8; f++)
        {
            for (int r = 0; r < 8; r++)
            {
                ChessSquare pane = squares[f][r];
                pane.setHighlight(null);
            }
        }
        selectedFile = null;
        selectedRank = null;
    }

    //[[maybe_unused]] Generated stuff to print out info about StackPanes to help debuging
    private void printStackPaneChildren(StackPane pane, int file, int rank) {
        System.out.printf("\nStackPane at %c%d (file=%d, rank=%d):%n", 
            (char)('a' + file), rank + 1, file, rank);
        
        System.out.println("  StackPane bounds: " + pane.getBoundsInLocal());
        System.out.println("  Children count: " + pane.getChildren().size());
        
        for (Node child : pane.getChildren()) {
            System.out.println("  └─ " + child.getClass().getSimpleName() + ":");
            System.out.println("      Bounds: " + child.getBoundsInParent());
            System.out.println("      Visible: " + child.isVisible());
            System.out.println("      Managed: " + child.isManaged());
            
            if (child instanceof ImageView) {
                Image img = ((ImageView)child).getImage();
                System.out.println("      Image: " + (img != null ? 
                    img.getWidth() + "x" + img.getHeight() + 
                    (img.isError() ? " [ERROR]" : "") : "null"));
            }
            else if (child instanceof Shape) {
                System.out.println("      Fill: " + ((Shape)child).getFill());
            }
        }
    }

}
