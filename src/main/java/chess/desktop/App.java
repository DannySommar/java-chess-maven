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

    @Override
    public void start(Stage primaryStage)
    {
        GameSelection gameSelection = new GameSelection();
        gameSelection.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
