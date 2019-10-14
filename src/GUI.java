import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {

    private Stage window;
    private GridPane root;
    private HBox upperMenu;
    private BorderPane borderPane;
    private int WINDOWSIZE = 600;
    private Board board = new Board();
    private final int size = 8;

    private HBox createUpperMenu() {
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
        upperMenu = new HBox();
        upperMenu.getChildren().add(upperGridPane);

        return upperMenu;
    }

    private void createMainWindow(Stage primaryStage) {

        //initial window conditions
        window = primaryStage;
        window.setTitle("Chess Game");
        window.centerOnScreen();
        window.setMinWidth(60);
        window.setMinHeight(60);
        window.setResizable(false);

    }

    private GridPane coloringTiles() {
        root = new GridPane();
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();
                Coordinate coordinate = new Coordinate(col, row);
                String tileColor = board.getTile(coordinate).getColor().toString();
                square.setStyle("-fx-background-color: " + tileColor + ";");
                root.add(square, col, row);
            }
        }
        return root;
    }

    private void putPieces() {

        //putting tile pieces on the GUI Board
        for (int row = 0; row < board.BOARD_LENGTH; row++) {
            for (int col = 0; col < board.BOARD_WIDTH; col++) {
                Coordinate coordinate = new Coordinate(col, row); //this is not logic because it is x and y not vice versa
                if (!board.getTile(coordinate).isEmpty()) {
                    Piece piece = board.getTile(coordinate).getPiece();
                    Image image = piece.getImage();
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    root.add(imageView, col, row);
                }
            }
        }
    }

    private void constraintsAligning(){
        final int square = 60;
        //setting the constraints to align the columns and rows (make them appear in the GUI)
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(
                    new ColumnConstraints(
                            square, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(
                    new RowConstraints(
                            square, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }

    private void createBlankBoard(Stage primaryStage) {

        createMainWindow(primaryStage);
        createUpperMenu();
        coloringTiles();
        putPieces();
        constraintsAligning();

        //creating the main board that contains the center and the upper menu
        borderPane = new BorderPane();
        borderPane.setTop(upperMenu);
        BorderPane.setMargin(upperMenu, new Insets(10, 10, 40, 10));
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