import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;


public class GUI extends Application {

    private Stage window;
    private final int WINDOWSIZE = 500;
    private final Board board = new Board();


    private void createBlankBoard(Stage primaryStage){
        //initial window conditions
        window = primaryStage;
        window.setTitle("Chess Game");
        window.centerOnScreen();

        //setting the upper menu for (Player's turn, time, check)
        GridPane upperGridPane = new GridPane();
        upperGridPane.setHgap(20);
        upperGridPane.setVgap(20);
        Label whiteLabel = new Label("White Player Info");
        Label blackLabel = new Label("Black Player Info");

        whiteLabel.setStyle("-fx-font-weight: bold");
        blackLabel.setStyle("-fx-font-weight: bold");

        GridPane.setHalignment(whiteLabel, HPos.LEFT);
        GridPane.setHalignment(blackLabel, HPos.RIGHT);

        GridPane.setHgrow(whiteLabel, Priority.ALWAYS);
        GridPane.setHgrow(blackLabel, Priority.ALWAYS);

        upperGridPane.add(whiteLabel, 0, 0);
        upperGridPane.add(blackLabel, 1, 0);

        upperGridPane.setGridLinesVisible(true);
        HBox upperHBox = new HBox();
        upperHBox.getChildren().add(upperGridPane);

        //Drawing the 64 ChessBoard Tiles

        //making 64 tiles in columns and rows
        GridPane root = new GridPane();
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();
                Coordinate coordinate = new Coordinate(col,row);
                String tileColor = board.getTile(coordinate).getColor().toString();
                square.setStyle("-fx-background-color: " + tileColor + ";");
                root.add(square,col,row);
            }
        }

        //putting tile pieces on the GUI Board


        //setting the constraints to align the columns and rows (make them appear in the GUI)
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(
                    new ColumnConstraints(
                            5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(
                    new RowConstraints(
                            5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        //creating the main board that contains the center and the upper menu
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(upperGridPane);
        BorderPane.setMargin(upperGridPane, new Insets(10, 10, 40, 10));
        borderPane.setCenter(root);

        //putting the border menu in the main scene and the main scene in the main stage
        window.setScene(new Scene(borderPane, WINDOWSIZE, WINDOWSIZE));
        window.show();
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        createBlankBoard(window);

    }

    public static void main(String[] args) {
        launch(args);
    }
}