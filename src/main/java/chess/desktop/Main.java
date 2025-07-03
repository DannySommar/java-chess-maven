package chess.desktop;

import chess.core.game.Game;
import chess.core.game.StandardGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
     private Game game = new StandardGame("White", "Black");

    @Override
    public void start(Stage primaryStage)
    {
        ChessBoard chessBoard = new ChessBoard(game); // Custom board UI
        
        Scene scene = new Scene(chessBoard, 800, 800);
        
        primaryStage.setTitle("JavaFX Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
