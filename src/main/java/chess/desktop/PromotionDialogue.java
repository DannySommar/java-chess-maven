package chess.desktop;

import chess.core.Colour;
import chess.core.piece.Bishop;
import chess.core.piece.Horse;
import chess.core.piece.Piece;
import chess.core.piece.Queen;
import chess.core.piece.Rook;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class PromotionDialogue extends Dialog<Piece>
{
    public PromotionDialogue(Colour pieceColour)
    {
        setTitle("Pawn Promotion");
        setHeaderText("choose what to prome to");

        ButtonType queenButton = new ButtonType("Queen", ButtonData.OK_DONE);
        ButtonType rookButton = new ButtonType("Rook", ButtonData.OK_DONE);
        ButtonType bishopButton = new ButtonType("Bishop", ButtonData.OK_DONE);
        ButtonType horseButton = new ButtonType("Horse", ButtonData.OK_DONE);

        getDialogPane().getButtonTypes().addAll(queenButton, rookButton, bishopButton, horseButton);

        getDialogPane().setContent(new HBox(10,
            createPromotionPicture(new Queen(pieceColour)),
            createPromotionPicture(new Rook(pieceColour)),
            createPromotionPicture(new Bishop(pieceColour)),
            createPromotionPicture(new Horse(pieceColour))
        ));

        setResultConverter(buttonType -> {
            if (buttonType == queenButton) return new Queen(pieceColour);
            if (buttonType == rookButton) return new Rook(pieceColour);
            if (buttonType == bishopButton) return new Bishop(pieceColour);
            if (buttonType == horseButton) return new Horse(pieceColour);
            return null;
        });

    }

    private Node createPromotionPicture(Piece piece)
    {
        PieceRenderer renderer = new PieceRenderer(piece);

        // to make them a bit smaller, maybe cange PieceRender itself in future to include square length
        renderer.setFitWidth(40);
        renderer.setFitHeight(40);
        return renderer;
    }
}
