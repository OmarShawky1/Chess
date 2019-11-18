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

    private Stage window;
    private GridPane chessBoard;
    private GridPane upperGridPane;
    private Board board = new Board();
    private Tile sourceTile;
    private LocalTime whiteTime, blackTime;
    static Label gameStatusBar;
    private Timer timer;
//    private Socket opponent;


    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        createBlankWindow();
        window.setOnCloseRequest(e -> {
            window.close();
            timer.cancel();
            timer.purge();
        });
    }

    public static void main(String[] args) {
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

//    private void createGame() {
//        createBlankWindow();
//        window.setOnCloseRequest(e -> {
//            window.close();
//            timer.cancel();
//            timer.purge();
//        });
//    }
//
//    private Scene createCustomizedGridPane(Node... node) {
//        // Create a Grid and add vBox to it and set it at the center & set it to automatic resizing
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(node);
//        vBox.setSpacing(40);
//        GridPane gridPane = new GridPane();
//        gridPane.add(vBox, 0, 0);
//        gridPane.setAlignment(Pos.CENTER);
//        GridPane.setHalignment(gridPane, HPos.CENTER);
//        GridPane.setValignment(gridPane, VPos.CENTER);
//        GridPane.setHgrow(gridPane, Priority.ALWAYS);
//        GridPane.setVgrow(gridPane, Priority.ALWAYS);
//        return new Scene(gridPane, 400, 400);
//    }
//
//    private void createOpponentScene() {
//
//        // Create the Main Label
//        Label message = new Label("Enter Data");
//
//        //Create the Main items to get the IP and the connect Button and then add them to one hBox
//        Label enterIP = new Label("Enter IP Address");
//        enterIP.setAlignment(Pos.CENTER);
//
//        TextField IPEntered = new TextField();
//
//        Button connect = new Button("Connect");
//        AtomicReference<Socket> opponent = null;
//        AtomicReference<DataInputStream> dataInputStream = null;
//        AtomicReference<DataOutputStream> dataOutputStream = null;
//        connect.setOnAction(ev -> {
//            try {
//                System.out.println("I Am Opponent, I Am Trying To Connect");
//                assert false;
//                opponent.set(new Socket(IPEntered.getText(), 9090));
//                while (!opponent.get().isConnected()) {
//                    message.setText("IP Address Is incorrect, Please Enter a valid IP Address");
//                    Thread.sleep(1000);
//                }
//                dataInputStream.set(new DataInputStream(opponent.get().getInputStream()));
//                dataOutputStream.set(new DataOutputStream(opponent.get().getOutputStream()));
//            } catch (IOException | InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        // Add all of the Above to the Main hBox
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(enterIP, IPEntered, connect);
//        hBox.setSpacing(20);
//
//        // Create a GridPane Scene of automatic resizing
//        window.setScene(createCustomizedGridPane(message, hBox));
//    }
//
//    private void createServerScene() throws IOException {
//        /*Wait for an opponent to connect*/
//        Label message = new Label("Waiting for Opponent");
//        System.out.println("I Am Server, I Am Trying To connect");
//        window.setScene(createCustomizedGridPane(message));
//
//        ServerSocket serverSocket = new ServerSocket(9090);
//
//        Socket socket = serverSocket.accept();
//
//        System.out.println("I Am Server, I Am Connected");
//
//        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//    }
//
//    private void goToOpponentOrServerScene(ToggleGroup toggleGroup) throws IOException {
//
//        String selection = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
//        if (selection.equalsIgnoreCase("Opponent")) {
//            createOpponentScene();
//        } else {
//            createServerScene();
//        }
//    }
//
//    private void createServerOrOpponentScene() {
//
//        // Create the RadioButtons and add them to a VBox and a ToggleGroup
//        Label chooseServerOrOpponent = new Label("Choose Server Or Opponent");
//
//
//        ToggleGroup toggleGroup = new ToggleGroup();
//
//        RadioButton radioButton1 = new RadioButton("Server");
//        radioButton1.setToggleGroup(toggleGroup);
//        radioButton1.setSelected(true);
//
//        RadioButton radioButton2 = new RadioButton("Opponent");
//        radioButton2.setToggleGroup(toggleGroup);
//
//        VBox radioButtons = new VBox();
//        radioButtons.getChildren().addAll(radioButton1, radioButton2);
//        radioButtons.setSpacing(20);
//
//
//        // Create a button to get the selection from the radio button and pass it to connect To Player
//        Button selectButton = new Button("Select");
//        selectButton.setOnAction(e -> {
//            try {
//                goToOpponentOrServerScene(toggleGroup);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        // Create A VBox to add the RadioButtons VBox and select Button and the Main Label
//        //Create the Main Stage
//        window.setTitle("Connection Window");
//        window.setScene(createCustomizedGridPane(chooseServerOrOpponent, radioButtons, selectButton));
//        window.show();
//
//    }
//
//    private void connect() {
//
//        createServerOrOpponentScene();
//
//    }
}
