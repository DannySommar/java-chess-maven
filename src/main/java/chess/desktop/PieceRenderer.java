package chess.desktop;

import chess.core.Colour;
import chess.core.piece.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceRenderer extends ImageView
{
    private static final String IMAGE_PATH = "/images/%s_%s.png";

    public PieceRenderer(Piece piece)
    {
        String color = piece.getColour() == Colour.WHITE ? "w" : "b";
        String type = piece.getClass().getSimpleName().toLowerCase();
        String path =  String.format(IMAGE_PATH, color, type);
        
        Image image = new Image(getClass().getResourceAsStream(path));
        setImage(image);
        setFitWidth(80);
        setFitHeight(80);
    }
}
