import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;


public class GUI extends Application {

    public Stage window;
    private GridPane chessBoard;
    private GridPane upperGridPane;
    private Board board = new Board();
    private Tile sourceTile;
    private LocalTime whiteTime, blackTime;
    static Label gameStatusBar;
    private Timer timer;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        connectToPlayer();
//        startGame();
    }

    static void main() {
        launch();
    }

    private void createPlayerInfo(String playerColorName) {

        int rightOrLeft = playerColorName.equals("White") ? 0 : 2;

        Label playerColorNameLabel = new Label(playerColorName + "Player Info");
        playerColorNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setHalignment(playerColorNameLabel, HPos.CENTER);
        GridPane.setHgrow(playerColorNameLabel, Priority.ALWAYS);
        upperGridPane.add(playerColorNameLabel, rightOrLeft, 0);

        Label playerColorTimerLabel = new Label("15:00");
        GridPane.setHalignment(playerColorTimerLabel, HPos.CENTER);
        GridPane.setHgrow(playerColorTimerLabel, Priority.ALWAYS);
        upperGridPane.add(playerColorTimerLabel, rightOrLeft, 1);
    }

    private void runTimer() {

        Label whiteTimerLabel = (Label) upperGridPane.getChildren().get(1);
        Label blackTimerLabel = (Label) upperGridPane.getChildren().get(3);

        whiteTime = LocalTime.of(0, 15, 0);
        blackTime = whiteTime;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (board.whiteTurn) {
                        boolean whiteTimeIsOver = whiteTime.getMinute() == 0 && whiteTime.getSecond() == 0;
                        if (!whiteTimeIsOver) {
                            whiteTime = whiteTime.minusSeconds(1);
                            whiteTimerLabel.setText(whiteTime.getMinute() + ":" + whiteTime.getSecond());
                        } else {
                            gameStatusBar.setText("White Lost");
                            board.whiteKingAlive = false;
                        }
                    } else {
                        boolean blackTimeIsOver = blackTime.getMinute() == 0 && blackTime.getSecond() == 0;
                        if (!blackTimeIsOver) {
                            blackTime = blackTime.minusSeconds(1);
                            blackTimerLabel.setText(blackTime.getMinute() + ":" + blackTime.getSecond());
                        } else {
                            gameStatusBar.setText("Black Lost");
                            board.blackKingAlive = false;
                        }
                    }
                });
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    private void createUpperMenu() {
        //setting the upper menu for (Player's turn, time, check)
        upperGridPane = new GridPane();
        upperGridPane.setHgap(100);
        upperGridPane.setVgap(20);

        createPlayerInfo("White");
        createPlayerInfo("Black");

        Button rstButton = new Button("Reset Game");
        rstButton.setOnAction(e -> {
            timer.cancel();
            timer.purge();
            sourceTile = null;
            board = new Board();
            createBlankWindow();

        });
        GridPane.setHalignment(rstButton, HPos.CENTER);
        GridPane.setHgrow(rstButton, Priority.ALWAYS);
        upperGridPane.add(rstButton, 1, 1);

        runTimer();

        gameStatusBar = new Label("Start Game");
        GridPane.setHalignment(gameStatusBar, HPos.CENTER);
        GridPane.setHgrow(gameStatusBar, Priority.ALWAYS);
        upperGridPane.add(gameStatusBar, 1, 0);

//        upperGridPane.setGridLinesVisible(true);
        int upperMenuInsets = 10;
        BorderPane.setMargin(upperGridPane, new Insets(upperMenuInsets));
    }

    private void createMainWindow() {

        //initial window conditions
        window.setTitle("Chess Game");
        window.centerOnScreen();
    }

    private void creatingBlankTiles() {
        chessBoard = new GridPane();
        final int size = 8;
        if (board.whiteTurn) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    Coordinate coordinate = new Coordinate(col, row);
                    Tile tile = board.getTile(coordinate);
                    tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    tile.setOnAction(e -> play(coordinate));
                    String tileColor = tile.getColor();
                    tile.setStyle("-fx-background-color: " + tileColor + ";");
                    chessBoard.add(tile, col, row);
                }
            }
        } else {
            int oppRow = 0;
            for (int row = size - 1; row >= 0; row--) {
                int oppCol = 0;
                for (int col = size - 1; col >= 0; col--) {
                    Coordinate coordinate = new Coordinate(col, row);
                    Tile tile = board.getTile(coordinate);
                    tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    tile.setOnAction(e -> play(coordinate));
                    String tileColor = tile.getColor();
                    tile.setStyle("-fx-background-color: " + tileColor + ";");
                    chessBoard.add(tile, oppCol, oppRow);
                    oppCol++;
                }
                oppRow++;
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
            chessBoard.getColumnConstraints().add(
                    new ColumnConstraints(square, square, Double.POSITIVE_INFINITY,
                            Priority.ALWAYS
                            , HPos.CENTER, true));
            chessBoard.getRowConstraints().add(
                    new RowConstraints(square, square, Double.POSITIVE_INFINITY, Priority.ALWAYS,
                            VPos.CENTER, true));
        }
    }

    private void createBlankBoard() {
        creatingBlankTiles();
        putPieces();
        constraintsAligning();

        //creating the main board that contains the center and the upper menu
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(upperGridPane);
        borderPane.setCenter(chessBoard);

        //putting the border menu in the main scene and the main scene in the main stage
        int WINDOWSIZE = 600;
//        Scene scene = new Scene(borderPane, WINDOWSIZE, WINDOWSIZE);
        Scene scene = new Scene(borderPane, WINDOWSIZE, WINDOWSIZE);
        window.setScene(scene);
        createMainWindow();
        window.show();
    }

    private void createBlankWindow() {

        createUpperMenu();
        createBlankBoard();
    }

    private void highlightTile(Tile tile) {
        tile.setStyle("-fx-background-color: " + "green" + ";");
    }

    private void highlightPossibleDestinations(Tile sourceTile) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (sourceTile.getPiece().canMove(sourceTile.getBoard().getTile(new Coordinate(row, col)))) {
//                    highlightTile();
                    sourceTile.getBoard().getTile(new Coordinate(row, col)).setStyle("-fx-background-color: " + "blue" + ";");
                }
            }
        }
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
                    boolean whiteTurn = newTile.getPiece().getColor().equalsIgnoreCase("white") && board.whiteTurn;
                    boolean blackTurn = newTile.getPiece().getColor().equalsIgnoreCase("black") && !board.whiteTurn;
                    boolean correctPlayerTurn = whiteTurn || blackTurn;
                    if (correctPlayerTurn) {
                        sourceTile = newTile;
                        highlightTile(sourceTile);
                        highlightPossibleDestinations(sourceTile);
                    } else {
                        gameStatusBar.setText("It's " + (board.whiteTurn ? "White" : "Black") + "'s Turn");
                    }
                }else {
                    gameStatusBar.setText("Please select a correct Piece");
                }
                //End of getSourceTile
            } else if (sourceTile.getCoordinates() == newTile.getCoordinates()) {
                sourceTile = null;
                createBlankBoard();
            } else { //Start of getDestinationTile

                boolean newTileIsEmpty = newTile.isEmpty();
                boolean newTileContainsEnemy = !newTile.isEmpty() && (!sourceTile.getPiece().getColor().equals(newTile.getPiece().getColor()));
                boolean newTileDoesNotContainAlly = newTileIsEmpty || newTileContainsEnemy;
                if (newTileDoesNotContainAlly) {
                    gameStatusBar.setText("");
                    board.play(sourceTile, newTile);
                    createBlankBoard();
                    putPieces();
                    //after playing, set sourceTile to null
                    sourceTile = null;
                }
            }
            //End of getDestinationTile
        }
    }

    private void startGame(){
        createBlankWindow();
        window.setOnCloseRequest(e -> {
            window.close();
            timer.cancel();
            timer.purge();
        });
    }

    private void connectToPlayer(){
        /** Choose if you are a server or an opponent to make the right object**/
        Label message = new Label("Choose if you are the server or the opponent");
//        message.setAlignment(Pos.TOP_CENTER);

        ToggleGroup toggleGroup = new ToggleGroup();


        RadioButton serverRadioButton = new RadioButton();
        RadioButton opponentRadioButton = new RadioButton();

        serverRadioButton.setSelected(true);
        serverRadioButton.setText("Server");
        serverRadioButton.setToggleGroup(toggleGroup);

        opponentRadioButton.setText("Opponent");
        opponentRadioButton.setToggleGroup(toggleGroup);

        Button selectButton = new Button("Select");
        selectButton.setOnAction(e->{
            String selection = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (selection.equalsIgnoreCase("Server")){
                try {
                    ServerPlayer serverPlayer = new ServerPlayer(window);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else {
                try {
                    OpponentPlayer opponentPlayer = new OpponentPlayer(window);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox radioButtons = new VBox();
        radioButtons.getChildren().addAll(serverRadioButton, opponentRadioButton, selectButton);
        radioButtons.setSpacing(20);

        GridPane connectionMainRoot = new GridPane();
        connectionMainRoot.add(radioButtons,0,0);
        connectionMainRoot.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(message);
        BorderPane.setAlignment(message,Pos.TOP_CENTER);
        borderPane.setCenter(connectionMainRoot);
        BorderPane.setMargin(message, new Insets(20, 0,0,0));
        window.setScene(new Scene(borderPane, 400, 400));
        window.show();
    }

}
