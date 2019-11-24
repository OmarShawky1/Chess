import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class OpponentPlayer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Label message;


    OpponentPlayer(Stage window) {

        message = new Label("Enter Data Below");

        Label enterIP = new Label("Enter IP Address:");
        TextField enteredIP = new TextField();
        enteredIP.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                connect(enteredIP);
            }
        });
        enteredIP.setMaxWidth(100);
        Button connect = new Button("Connect");
        connect.setOnAction(event -> {
            connect(enteredIP);
        });

        BorderPane root = new BorderPane();
        BorderPane.setMargin(message, new Insets(50));
        BorderPane.setAlignment(message, Pos.TOP_CENTER);
        root.setTop(message);


        GridPane gridPane = new GridPane();
        gridPane.add(enterIP, 0, 0);
        gridPane.add(enteredIP, 1, 0);
        gridPane.add(connect, 2, 0);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(40);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        root.setCenter(gridPane);

        window.setScene(new Scene(root, 500, 400));
        window.show();
    }

    private void connect(TextField enteredIP) {

        try {
            clientSocket = new Socket(enteredIP.getText(), 9090);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            message.setText("Connected Successfully to Server");
//                out.println("This Message is from The Opponent");
//                in.readLine();
            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main (String[] args) throws IOException {
//        OpponentPlayer opponentPlayer = new OpponentPlayer();
//        opponentPlayer.startConnection("localhost", 9050);
//        opponentPlayer.sendMessage("hello server");
//    }
//
//    public void startConnection(String ip, int port) throws IOException {
//        clientSocket = new Socket(ip, port);
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//    }
//
//    public void sendMessage(String msg) throws IOException {
////        System.out.println("This is Opponent, and this is sendMessage");
//        out.println(msg);
//        System.out.println(in.readLine());
//    }
//
//    public void stopConnection() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//    }
}
