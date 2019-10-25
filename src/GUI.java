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
//    private Tile destinationTile;


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
                Tile tile = board.getTile(coordinate);

                if (!board.getTile(coordinate).isEmpty()) {
                    Piece piece = board.getTile(coordinate).getPiece();
                    Image image = piece.getImage();
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
//                    button[col][row].setGraphic(imageView);
//                    button[col][row].setAlignment(Pos.CENTER); // for non obvious reasons, this does not work with me
//                    button[col][row].setContentDisplay(ContentDisplay.CENTER);
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
                return;
                //End of getSourceTile
            } else{ //Start of getDestinationTile

                boolean newTileIsEmpty = newTile.isEmpty();
                boolean newTileContainsEnemy = !newTile.isEmpty() && (sourceTile.getPiece().getColor() != newTile.getPiece().getColor());
                boolean newTileDoesNotContainAlly = newTileIsEmpty || newTileContainsEnemy;
                if (newTileDoesNotContainAlly) {
                    Tile destinationTile = newTile;
                    board.play(sourceTile, destinationTile);
                    putPieces();
                    //after playing, set sourceTile to null
                    sourceTile = null;
                }
                return;
            }
            //End of getDestinationTile

            //Start of IsMovement valid?
//            if (sourceTile != null && destinationTile != null) {
//
//            }
            //End of IsMovementValid?
        }

//        //setting the sourceTile and destinationTile to null after being used
//        if (sourceTile != null && destinationTile != null) {
//
//            sourceTile = null;
//            destinationTile = null;
//        }
//
//        Tile newTile = board.getTile(newCoordinate);
//        boolean newTileIsEmpty = newTile.isEmpty();
//
//        //assigning the piece, seeing if the piece is from old sourceTile or from the newCoordinate
//        Piece movingPiece = null;
//        Color movingPieceColor = null;
//        if (sourceTile != null && !sourceTile.isEmpty()) {
//            movingPiece = sourceTile.getPiece();
//            movingPieceColor = movingPiece.getColor();
//        } else if (!newTileIsEmpty) {
//            movingPiece = newTileIsEmpty ? null : newTile.getPiece();
//            movingPieceColor = movingPiece.getColor();
//        }
//
//
//        boolean whitePlayerPiece = movingPiece == null ? false : (movingPieceColor == Color.WHITE);
//        boolean blackPlayerPiece = movingPiece == null ? false : (movingPieceColor == Color.BLACK);
//        boolean rightWhiteTurn = board.whiteTurn && whitePlayerPiece;
//        boolean rightBlackTurn = !board.whiteTurn && blackPlayerPiece;
//        boolean rightPlayerTurn = rightWhiteTurn || rightBlackTurn;
//
//        //assigning the value of the newCoordinate to the sourceTile
//        if (rightPlayerTurn && sourceTile == null) {
//            sourceTile = board.getTile(newCoordinate);
//            return;
//        }
//
//        //if the sourceTile that contains the correct piece to move is already assigned, proceed to know the destination of the piece
//        if (sourceTile != null) {
//
//            //the code will reach this part after having moving piece
//            //this is dedicated to get the destination tile and see if the assigned movingPiece can move to the destination or not
//            boolean destinationIsEmpty = newTileIsEmpty;
//            Piece enemyPiece = destinationIsEmpty ? null : newTile.getPiece();
//            Color enemyColor = destinationIsEmpty ? null : enemyPiece.getColor();
//            boolean destinationIsFreeOrContainsEnemy = destinationIsEmpty || (enemyColor != movingPieceColor);
//
//            //if things went right, in other words, the destination is free or contain an enemy, pass those parameters to board.play();
//            if (rightPlayerTurn && destinationIsFreeOrContainsEnemy) {
//
//                destinationTile = board.getTile(newCoordinate);
//                Coordinate sourceCoordinate = sourceTile.getCoordinates();
//                Coordinate destinationCoordinate = destinationTile.getCoordinates();
//
//                board.play(sourceCoordinate, destinationCoordinate);
//                putPieces();
//                return;
//            }
//        }
    }
}
