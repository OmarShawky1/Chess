import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GUI extends Application {

    private Stage window;
    private Scene scene;
    private GridPane root;
    private GridPane upperGridPane;
    private BorderPane borderPane;
    private static Board board = new Board();
    private final int WINDOWSIZE = 600;
    private final int size = 8;
    private Tile sourceTile;
    private Tile destinationTile;


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
//        window.setMinWidth(WINDOWSIZE);
//        window.setMinHeight(WINDOWSIZE + 100);
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
        for (int row = 0; row < board.BOARD_LENGTH; row++) {
            for (int col = 0; col < board.BOARD_WIDTH; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                if (!board.getTile(coordinate).isEmpty()) {
                    Piece piece = board.getTile(coordinate).getPiece();
                    Image image = piece.getImage();
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
//                    button[col][row].setGraphic(imageView);
//                    button[col][row].setAlignment(Pos.CENTER); // for non obvious reasons, this does not work with me
//                    button[col][row].setContentDisplay(ContentDisplay.CENTER);
                    Tile tile = board.getTile(coordinate);
                    tile.setGraphic(imageView);
                    tile.setAlignment(Pos.CENTER);
                    tile.setContentDisplay(ContentDisplay.CENTER);
                }
            }
        }
    }

    private void constraintsAligning() {
        //setting the constraints to align the columns and rows (make them appear in the GUI)
        for (int i = 0; i < size; i++) {
            // correct this shit because resizing the window does not change the area of each item
//            ColumnConstraints cc = new ColumnConstraints();
//            cc.setPercentWidth(12.5);
//            root.getColumnConstraints().add(cc);
//
//            RowConstraints rc = new RowConstraints();
//            rc.setPercentHeight(12.5);
//            root.getRowConstraints().add(rc);

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
        borderPane = new BorderPane();
        borderPane.setTop(upperGridPane);
        borderPane.setCenter(root);

        //putting the border menu in the main scene and the main scene in the main stage
        scene = new Scene(borderPane, WINDOWSIZE, WINDOWSIZE);
        window.setScene(scene);
        createMainWindow();
        window.show();
    }

    private void play(Coordinate coordinate) {

        Tile tile  = board.getTile(coordinate);
        boolean tileIsEmpty = tile.isEmpty();
        Piece piece = tile.getPiece();
        Color pieceColor = !tileIsEmpty? piece.getColor(): null;
        boolean whitePlayerPiece  = !tileIsEmpty && pieceColor == Color.WHITE;
        boolean blackPlayerPiece = !tileIsEmpty && pieceColor == Color.BLACK;
        boolean sourceContainsPlayersPiece = whitePlayerPiece || blackPlayerPiece;

        if (board.whiteTurn && sourceContainsPlayersPiece) {
            System.out.println("I assigned the sourceTile");
            sourceTile = board.getTile(coordinate);
            return;
        }

        if (sourceTile != null) {
            System.out.println("I am trying to assign the destination Tile");
            Piece enemyPiece = tileIsEmpty? null:tile.getPiece();
            Color enemyColor = !tileIsEmpty? enemyPiece.getColor(): null;
            boolean destinationIsEmpty = tileIsEmpty;
            boolean destinationIsFreeOrContainsEnemy = destinationIsEmpty || (enemyColor == Color.BLACK);
            if (board.whiteTurn && destinationIsFreeOrContainsEnemy) {

                System.out.println("i assigned the destination Tile");
                destinationTile = board.getTile(coordinate);
                Coordinate sourceCoordinate = sourceTile.getCoordinates();
                Coordinate destinationCoordinate = destinationTile.getCoordinates();
                System.out.println("sourceCoordinate: " + sourceCoordinate);
                System.out.println("destinationCoordinate: " + destinationCoordinate);
                board.play(sourceCoordinate, destinationCoordinate);
            }
            if (!sourceTile.isEmpty() && !destinationTile.isEmpty()){

                System.out.println("i assigned the sourceTile and the destinationTile to null");
                sourceTile = null;
                destinationTile = null;
            }
        }

    }

}
