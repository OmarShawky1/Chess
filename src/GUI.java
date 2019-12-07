import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;


public class GUI extends Application {

    Stage window;
    private GridPane chessBoard;
    private GridPane upperGridPane;
    private Board board = new Board(this);
    private Tile sourceTile;
    private Tile destinationTile;
    private LocalTime whiteTime, blackTime;
    static Label gameStatusBar;
    private Timer timer;
    private ServerPlayer serverPlayer = null;
    private OpponentPlayer opponentPlayer = null;
    private String movement = null; //this is only written here for the receive Thread
    private Thread receiveThread;
    private boolean closing;


    static void main(String[] args) {
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

    private void endTime() {
        timer.cancel();
        timer.purge();
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
            endTime();
//            receiveThread.interrupt();
            sourceTile = null;
            board = new Board(this);
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
                    tile.setOnAction(e -> {
                        try {
                            play(coordinate);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
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
                    tile.setOnAction(e -> {
                        try {
                            play(coordinate);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
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
        Scene scene = new Scene(borderPane, WINDOWSIZE, WINDOWSIZE);
        window.setScene(scene);
        createMainWindow();
        window.show();
    }

    private void createBlankWindow() {

        createUpperMenu();
        createBlankBoard();
//        receiveMovement();
    }

    private void highlightSourceTile(Tile tile) {
        tile.setStyle("-fx-background-color: " + "green" + ";");
    }

    private void highlightPossibleDestinations(Tile sourceTile) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (sourceTile.getPiece().canMove(sourceTile.getBoard().getTile(new Coordinate(row, col)))) {
                    sourceTile.getBoard().getTile(new Coordinate(row, col)).setStyle("-fx-background-color: " + "blue" + ";");
                }
            }
        }
    }

    private void boardPlay(boolean received) throws IOException {
        gameStatusBar.setText("");
        board.play(sourceTile, destinationTile, received);
        createBlankBoard();

        //after playing, set sourceTile to null
        sourceTile = null;
        destinationTile = null;
    }

    private void guiPlay(Coordinate newCoordinate) throws IOException {
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
                    highlightSourceTile(sourceTile);
                    highlightPossibleDestinations(sourceTile);
                } else {
                    gameStatusBar.setText("It's " + (board.whiteTurn ? "White" : "Black") + "'s Turn");
                }
            } else {
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
                destinationTile = newTile;
                boardPlay(false);
            }
        }
        //End of getDestinationTile
    }

    private void play(Coordinate newCoordinate) throws IOException {
        //the error here is that the server plays first then sends the movement, but in order for the opponent to play he must receive 
        // that the server player to change the value of board.whiteTurn and that does not happen

        if (board.whiteKingAlive && board.blackKingAlive) {
            if (serverPlayer != null) {
                if (board.whiteTurn) {
                    guiPlay(newCoordinate);
                }else {
                    gameStatusBar.setText("Not Your Turn");
                }
            } else if (opponentPlayer != null) {
                if (!board.whiteTurn) {
                    guiPlay(newCoordinate);
                }else {
                    gameStatusBar.setText("Not Your Turn");
                }
            }
        }
    }

    private void connectToPlayer() {
        /* Choose if you are a server or an opponent to make the right object**/
        Label message = new Label("Choose if you are the server or the opponent");

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton serverRadioButton = new RadioButton();
        RadioButton opponentRadioButton = new RadioButton();

        serverRadioButton.setSelected(true);
        serverRadioButton.setText("Server");
        serverRadioButton.setToggleGroup(toggleGroup);

        opponentRadioButton.setText("Opponent");
        opponentRadioButton.setToggleGroup(toggleGroup);

        Button selectButton = new Button("Select");
        selectButton.setOnAction(e -> {
            String selection = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (selection.equalsIgnoreCase("Server")) {
                serverPlayer = new ServerPlayer(this);
            } else {
                opponentPlayer = new OpponentPlayer(this);
            }
        });

        VBox radioButtons = new VBox();
        radioButtons.getChildren().addAll(serverRadioButton, opponentRadioButton, selectButton);
        radioButtons.setSpacing(20);

        GridPane connectionMainRoot = new GridPane();
        connectionMainRoot.add(radioButtons, 0, 0);
        connectionMainRoot.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(message);
        BorderPane.setAlignment(message, Pos.TOP_CENTER);
        borderPane.setCenter(connectionMainRoot);
        BorderPane.setMargin(message, new Insets(20, 0, 0, 0));
        window.setScene(new Scene(borderPane, 400, 400));
        window.show();
    }

    void sendMovement() throws IOException {
        //i changed it's logic to be in piece.move (commented to this is so new)
        String movement = sourceTile.getCoordinates().toString() + destinationTile.getCoordinates().toString();

        if (serverPlayer != null) {
            serverPlayer.out.writeUTF(movement);
//            serverPlayer.out.write(movement + "\n");
        } else if (opponentPlayer != null) {
            opponentPlayer.out.writeUTF(movement);
//            opponentPlayer.out.write(movement + "\n");
        }
    }

    private void receiveMovement() {
        receiveThread = new Thread(() -> {
            while (!closing) {
                try {
                    if (serverPlayer != null) {
                        movement = serverPlayer.in.readUTF();
//                    movement = serverPlayer.in.readLine();
                    } else if (opponentPlayer != null) {
                        movement = opponentPlayer.in.readUTF();
//                    movement = opponentPlayer.in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (movement != null) {
                    Coordinate sourceCoordinate = new Coordinate(movement.charAt(0) + "" + movement.charAt(1));
                    Coordinate destinationCoordinate = new Coordinate(movement.charAt(2) + "" + movement.charAt(3));
                    sourceTile = board.getTile(sourceCoordinate);
                    destinationTile = board.getTile(destinationCoordinate);
                    Platform.runLater(() -> {
                        try {
                            boardPlay(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        receiveThread.start();
    }

    void startGame() {
        createBlankWindow();
        receiveMovement();
        window.setOnCloseRequest(e -> {
//            window.close();
//            closing = true;
////            receiveThread.interrupt();
//            endTime();
//            for (Thread t : Thread.getAllStackTraces().keySet()) {
//                if (t.getState() == Thread.State.RUNNABLE) {
//                    t.interrupt();
//                }
//            }
            System.exit(0);
        });
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        connectToPlayer();
    }
}