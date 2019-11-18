import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OpponentPlayer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public OpponentPlayer(Stage window) throws IOException {
        Label message = new Label("Enter Data Below");

        Label enterIP = new Label("Enter IP Address");
        TextField enteredIP = new TextField();
        Button connect = new Button("Connect");
        connect.setOnAction(event -> {
            try {
                clientSocket = new Socket(enteredIP.getText(), 9090);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        HBox hBox = new HBox();
        hBox.getChildren().addAll(enterIP, enteredIP, connect);
        hBox.setSpacing(20);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(message);
        BorderPane.setAlignment(message, Pos.TOP_CENTER);
        BorderPane.setMargin(message, new Insets(20, 0, 0, 0));
        borderPane.setCenter(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        window.setScene(new Scene(borderPane, 400, 400));
        window.show();

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
