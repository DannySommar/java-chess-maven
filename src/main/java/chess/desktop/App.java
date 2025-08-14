package chess.desktop;

import chess.core.game.Game;
import chess.core.game.StandardGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application
{
     private Game game = new StandardGame("White", "Black");

    @Override
    public void start(Stage primaryStage)
    {

        BoardWithCoordinates root = new BoardWithCoordinates(game);
        root.connectChessBoardToThis();

        
        Scene scene = new Scene(root);

        Image icon = new Image("images/w_knock.png");
        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("JavaFX Chess");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
