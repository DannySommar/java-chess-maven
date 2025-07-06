package chess.desktop;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessSquare extends StackPane
{
    public static final int LAYER_BACKGROUND = 0;
    public static final int LAYER_PIECE = 1;
    public static final int LAYER_HIGHLIGHT = 2;
    
    private final Node[] layers = new Node[3];
    
    public ChessSquare(Color colour)
    {
        layers[LAYER_BACKGROUND] = new Rectangle(80, 80, colour);
        getChildren().add(layers[LAYER_BACKGROUND]);
    }
    
    public void setPiece(Node piece)
    {
        setLayer(LAYER_PIECE, piece);
    }
    
    public void setHighlight(Node highlight)
    {
        setLayer(LAYER_HIGHLIGHT, highlight);
    }
    
    private void setLayer(int index, Node node)
    {
        // umm uhhh
    }
}
