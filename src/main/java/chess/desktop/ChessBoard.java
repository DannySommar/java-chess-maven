package chess.desktop;

import java.util.List;
import java.util.Optional;

import chess.core.*;
import chess.core.game.Game;
import chess.core.move.Move;
import chess.core.move.PromotionMove;
import chess.core.piece.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ChessBoard extends GridPane {
    private static final int SQUARE_SIZE = 80;
    private final Game game;
    private ChessSquare[][] squares = new ChessSquare[8][8];

    private Integer selectedFile = null;
    private Integer selectedRank = null;

    private final ToggleGroup flipRadios = new ToggleGroup();
    private final RadioButton flipEachTurn = new RadioButton("Flip each turn");
    private final RadioButton noFlip = new RadioButton("No flip");
    private boolean shouldFlip = true;

    public ChessBoard(Game game)
    {
        this.game = game;
        initializeBoard();
        setupFlipControls();
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

                setSquareHandler(squarePane, file, rank); // to make sure square is correct square
                
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
        boolean flip = shouldFlip && game.getCurrentPosition().getTurn() == Colour.WHITE;
        rank = flip ? 7 - rank : rank;

        System.out.println("clicked on file: " + file + " , rank: " + rank);
        System.out.println("square " + (char)('a' + file) + (rank + 1));
        clearSelections();
        Position position = game.getCurrentPosition();
        Piece clickedPiece = position.getPiece(file, rank);

        if (clickedPiece != null)
            System.out.println("clicked on piece " + clickedPiece.toString());

        Piece pastPiece = null;

        if (selectedFile != null)
            pastPiece = position.getPiece(selectedFile, selectedRank);

        // if no piece currently slected
        if (pastPiece == null)
        {
            highlightSelection(file, rank);

            if (clickedPiece != null && clickedPiece.getColour() == position.getTurn())
            {
                List<Move> validMoves = clickedPiece.generateValidMoves(position, file, rank);
                for (Move move : validMoves)
                {
                    Move legalMove = position.returnLegalMoveOrNull(move);
                    if (legalMove != null)
                    {
                        System.out.println(legalMove.toString());
                        highlightSelection(move.toFile, move.toRank);
                    }
                }
            }


            selectedRank = rank;
            selectedFile = file;
        }
        else // some piece already selected, now do a move and check if it's valid
        {
            System.out.println("Past piece: " + pastPiece.toString());

            Move attemptedMove = new Move(selectedFile, selectedRank, file, rank);
            if (pastPiece instanceof Pawn && (rank == ((Pawn)pastPiece).getPromotionRank())) // check if possible promotion
            {
                // List<Move> validMoves = pastPiece.generateValidMoves(position, file, rank);
                // for (Move move : validMoves)
                // {
                //     if (attemptedMove.matches(move))
                //     {
                //         System.out.println("move is a promotion");
                //         attemptedMove = handlePromotion(attemptedMove);
                //         break;
                //     }
                // }

                System.out.println("move is a promotion");
                attemptedMove = handlePromotion(attemptedMove);
            }

            System.out.println("Tried making move: " + attemptedMove.toString());

            try
            {
                game.addMove(attemptedMove);

                updatePieces(); 

                System.out.println("move valid,new board should be:");
                Position newPosition = game.getCurrentPosition();
                newPosition.printBoard();
            } catch (InvalidMoveException e) {
                System.out.println("invalid move");
            }

            selectedFile = null;
            selectedRank = null;
        }
        
        System.out.println();
    }

    private PromotionMove handlePromotion(Move move)
    {
    
        PromotionDialogue dialog = new PromotionDialogue(game.getTurn());
        Optional<Piece> result = dialog.showAndWait();

        if (result.isPresent())
        {
            Piece dialoguePiece = result.get();
            return new PromotionMove(move.fromFile, move.fromRank, move.toFile, move.toRank, dialoguePiece);
        }

        // if nothing selected
        Piece promoPiece = new Bishop(game.getTurn());

        return new PromotionMove(move.fromFile, move.fromRank, move.toFile, move.toRank, promoPiece);
    }

    // used for each move. doesn't reset the squares, but replACES the ONE PIECEs
    void updatePieces()
    {
        Position current = game.getCurrentPosition();
        boolean flip = shouldFlip && current.getTurn() == Colour.WHITE;
        
        for (int rank = 0; rank < 8; rank++)
        {
            for (int file = 0; file < 8; file++)
            {
                int displayRank = flip ? 7 - rank : rank;

                Piece piece = current.getPiece(file, displayRank);
                squares[file][rank].setPiece(piece != null ? new PieceRenderer(piece) : null);
            }
        }
    }

    private void highlightSelection(int file, int rank)
    {
        boolean flip = shouldFlip && game.getCurrentPosition().getTurn() == Colour.WHITE;
        rank = flip ? 7 - rank : rank;

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
    }

    private void setupFlipControls()
    {
        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);

        flipEachTurn.setToggleGroup(flipRadios);
        noFlip.setToggleGroup(flipRadios);
        flipEachTurn.setSelected(true);

        flipRadios.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            shouldFlip = (newVal == flipEachTurn);
            updatePieces();
        });

        controls.getChildren().addAll(new Label("Board Perspective:"), flipEachTurn, noFlip);

        // add it so at the bottom and spans nicely
        add(controls, 0, 8, 8, 1);
    }

}
