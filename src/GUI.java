import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GUI extends Application {

    private Stage window;
    private GridPane root;
    private GridPane upperGridPane;
    private static Board board = new Board();
    private Tile sourceTile;


    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        createBlankBoard();
    }

    public static void main(String[] args) {
        launch();
    }

    private void createUpperMenu() {
        //setting the upper menu for (Player's turn, time, check)
        upperGridPane = new GridPane();
        upperGridPane.setHgap(100);
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
        upperGridPane.add(blackLabel, 2, 0);

        Button rstButton = new Button("Reset Game");
        rstButton.setOnAction(e -> {
            board = new Board();
            createBlankBoard();
        });

        upperGridPane.add(rstButton, 1, 1);

//        upperGridPane.setGridLinesVisible(true);
        int upperMenuInstes = 10;
        BorderPane.setMargin(upperGridPane, new Insets(upperMenuInstes));
    }

    private void createMainWindow() {

        //initial window conditions
        window.setTitle("Chess Game");
        window.centerOnScreen();
    }

    private void coloringTiles() {
        root = new GridPane();
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                Tile tile = board.getTile(coordinate);
                tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                tile.setOnAction(e -> play(coordinate));
                String tileColor = tile.getColor();
                tile.setStyle("-fx-background-color: " + tileColor + ";");
                root.add(tile, col, row);

            }
        }
    }

    private void putPieces() {

        //putting tile pieces on the GUI Board
        for (int row = 0; row < Board.BOARD_LENGTH; row++) {
            for (int col = 0; col < Board.BOARD_WIDTH; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                Tile tile = board.getTile(coordinate);

                if (!board.getTile(coordinate).isEmpty()) {
                    Piece piece = board.getTile(coordinate).getPiece();
                    Image image = piece.getImage();
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    tile.setGraphic(imageView);
                    tile.setAlignment(Pos.CENTER);
                    tile.setContentDisplay(ContentDisplay.CENTER);
                } else {
                    tile.setGraphic(null);
                }
            }
        }
    }

    private void constraintsAligning() {
        //setting the constraints to align the columns and rows (make them appear in the GUI)
        int size = 8;
        for (int i = 0; i < size; i++) {
            int square = 8;
            root.getColumnConstraints().add(
                    new ColumnConstraints(square, square, Double.POSITIVE_INFINITY,
                            Priority.ALWAYS
                            , HPos.CENTER, true));
            root.getRowConstraints().add(
                    new RowConstraints(square, square, Double.POSITIVE_INFINITY, Priority.ALWAYS,
                            VPos.CENTER, true));
        }
    }

    private void createBlankBoard() {

        createUpperMenu();
        coloringTiles();
        putPieces();
        constraintsAligning();

        //creating the main board that contains the center and the upper menu
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(upperGridPane);
        borderPane.setCenter(root);

        //putting the border menu in the main scene and the main scene in the main stage
        int WINDOWSIZE = 600;
        Scene scene = new Scene(borderPane, WINDOWSIZE, WINDOWSIZE);
        window.setScene(scene);
        createMainWindow();
        window.show();
    }

    private void play(Coordinate newCoordinate) {

        //if Kings are alive
        if (board.whiteKingAlive && board.blackKingAlive) {
            Tile newTile = board.getTile(newCoordinate);

            //Start of getSourceTile
            //if sourceTile is not yet assigned, assign the newTile to sourceTile if the newTile contains a piece
            if (sourceTile == null) {
                //if the newTile contains a piece
                if (!newTile.isEmpty()) {
                    sourceTile = newTile;
                }
                //End of getSourceTile
            } else{ //Start of getDestinationTile

                boolean newTileIsEmpty = newTile.isEmpty();
                boolean newTileContainsEnemy = !newTile.isEmpty() && (sourceTile.getPiece().getColor() != newTile.getPiece().getColor());
                boolean newTileDoesNotContainAlly = newTileIsEmpty || newTileContainsEnemy;
                if (newTileDoesNotContainAlly) {
                    board.play(sourceTile, newTile);
                    putPieces();
                    //after playing, set sourceTile to null
                    sourceTile = null;
                }
            }
            //End of getDestinationTile
        }
    }
}
