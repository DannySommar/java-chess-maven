package chess.desktop;

import chess.core.Colour;
import chess.core.game.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardWithCoordinates extends GridPane
{
    private static final int SQUARE_SIZE = 80;
    private final ChessBoard chessBoard;

    public BoardWithCoordinates(Game game)
    {
        this.chessBoard = new ChessBoard(game);
        buildCoordinateSystem();
        setupFlipControls();
    }

    public void connectChessBoardToThis()
    {
        chessBoard.border = this;
    } 

    private void buildCoordinateSystem() {
        
        setGridLinesVisible(false);
        
        addEmptySquare(0, 0); // top left
        addEmptySquare(9, 0); // top right
        addEmptySquare(0, 9); // bottom left
        addEmptySquare(9, 9); // bottom right

        
        for (int file = 0; file < 8; file++)
        {
            char fileChar = (char)('a' + file);
            
            addCoordinateLabel(1 + file, 0, String.valueOf(fileChar)); // top border
            addCoordinateLabel(1 + file, 9, String.valueOf(fileChar)); // bottom border
        }

        for (int rank = 0; rank < 8; rank++)
        {
            int displayRank = 8 - rank;
            
            addCoordinateLabel(0, 1 + rank, String.valueOf(displayRank)); // left border
            addCoordinateLabel(9, 1 + rank, String.valueOf(displayRank)); // right border
        }

        add(chessBoard, 1, 1, 8, 8);
    }

    private void addEmptySquare(int col, int row)
    {
        Rectangle empty = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        empty.setFill(Color.WHEAT);
        add(empty, col, row);
    }

    private void addCoordinateLabel(int col, int row, String text)
    {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 32;");
        label.setMinSize(SQUARE_SIZE, SQUARE_SIZE);
        label.setMaxSize(SQUARE_SIZE, SQUARE_SIZE);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.WHEAT, null, null)));
        
        add(label, col, row);
    }

    public void updateNumberLabels()
    {
        for (int rank = 0; rank < 8; rank++)
        {
            int displayRank = chessBoard.colourDisplayPerspective == Colour.WHITE ? 8 - rank :  1+ rank;
            
            addCoordinateLabel(0, 1 + rank, String.valueOf(displayRank)); // left border
            addCoordinateLabel(9, 1 + rank, String.valueOf(displayRank)); // right border
        }
    }

    private void setupFlipControls()
    {
        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);

        ToggleGroup flipRadios = new ToggleGroup();
        RadioButton flipEachTurn = new RadioButton("Flip each turn");
        RadioButton noFlip = new RadioButton("No flip");

        flipEachTurn.setToggleGroup(flipRadios);
        noFlip.setToggleGroup(flipRadios);
        flipEachTurn.setSelected(true);

        flipRadios.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            chessBoard.shouldFlip = (newVal == flipEachTurn);
            chessBoard.updatePieces();
        });

        controls.getChildren().addAll(new Label("Board Perspective:"), flipEachTurn, noFlip);

        // add it so at the bottom and spans nicely
        add(controls, 0, 10, 10, 1);
    }
}