import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {

    private Stage window;
    private GridPane root;
    private GridPane upperGridPane;
    private BorderPane borderPane;
    private static Board board = new Board();
    private Button[][] button = new Button[8][8];
    private final int WINDOWSIZE = 500;
    private final int square = WINDOWSIZE / 8;
    private final int size = 8;


    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        createBlankBoard();
    }

    public static void main() {
        launch();
    }

    private void createUpperMenu() {
        //setting the upper menu for (Player's turn, time, check)
        upperGridPane = new GridPane();
        upperGridPane.setHgap(200);
        upperGridPane.setVgap(20);
        Label whiteLabel = new Label("White Player Info");
        Label blackLabel = new Label("Black Player Info");

        whiteLabel.setStyle("-fx-font-weight: bold");
        blackLabel.setStyle("-fx-font-weight: bold");

        GridPane.setHalignment(whiteLabel, HPos.CENTER);
        GridPane.setHalignment(blackLabel, HPos.CENTER);

        GridPane.setHgrow(whiteLabel, Priority.ALWAYS);
        GridPane.setHgrow(blackLabel, Priority.ALWAYS);


        upperGridPane.add(whiteLabel, 0, 0);
        upperGridPane.add(blackLabel, 1, 0);

        upperGridPane.setGridLinesVisible(true);
        int upperMenuInstes = 10;
        BorderPane.setMargin(upperGridPane, new Insets(upperMenuInstes));
    }

    private void createMainWindow() {

        //initial window conditions
        window.setTitle("Chess Game");
        window.centerOnScreen();
        window.setMinWidth(WINDOWSIZE);
        window.setMinHeight(WINDOWSIZE + upperGridPane.getScaleY() * 100);
    }

    private void coloringTiles() {
        root = new GridPane();
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                button[col][row] = new Button(coordinate.toString());
                button[col][row].setBackground(Background.EMPTY);
                button[col][row].setOnAction(e -> play(coordinate));
                button[col][row].setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
                String tileColor = board.getTile(coordinate).getColor();
                button[col][row].setStyle("-fx-background-color: " + tileColor + ";");
                root.add(button[col][row], col, row);
            }
        }
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
                    button[col][row].setGraphic(imageView);
                    button[col][row].setAlignment(Pos.CENTER);
                }
            }
        }
    }

    private void constraintsAligning() {
        //setting the constraints to align the columns and rows (make them appear in the GUI)
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(
                    new ColumnConstraints(
                            square, square, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(
                    new RowConstraints(
                            square, square, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }

    private void createBlankBoard() {

        //this causes an error if i'm using a value from border pane before it is created (null pointer exception)
//        createMainWindow(primaryStage);
        createUpperMenu();
        coloringTiles();
        putPieces();
        constraintsAligning();

        //creating the main board that contains the center and the upper menu
        borderPane = new BorderPane();
        borderPane.setTop(upperGridPane);
        borderPane.setCenter(root);


        //putting the border menu in the main scene and the main scene in the main stage
        window.setScene(new Scene(borderPane, WINDOWSIZE, WINDOWSIZE));
        createMainWindow();
        window.show();
    }

    private void play(Coordinate coordinate) {

        if (!board.getTile(coordinate).isEmpty()) {
            System.out.println(board.getTile(coordinate).getPiece().getClass());
        }
        System.out.println("You reached Play");

    }

}
