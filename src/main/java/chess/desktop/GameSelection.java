package chess.desktop;

import chess.core.game.Chess960;
import chess.core.game.Game;
import chess.core.game.StandardGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameSelection extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Select game type");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Button standardButton = new Button("Standard Chess");
        standardButton.setOnAction(e -> launchGame(new StandardGame("White", "Black")));
        
        Button chess960Button = new Button("Chess 960");
        chess960Button.setOnAction(e -> launchGame(new Chess960("White", "Black")));
        
        root.getChildren().addAll(title, standardButton, chess960Button);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Variants");
        primaryStage.show();
    }
    
    private void launchGame(Game game)
    {
        Stage gameStage = new Stage();
        BoardWithCoordinates board = new BoardWithCoordinates(game);
        board.connectChessBoardToThis();
        
        Scene gameScene = new Scene(board);
        gameStage.setScene(gameScene);
        gameStage.setTitle("JavaFX Chess");
        gameStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
