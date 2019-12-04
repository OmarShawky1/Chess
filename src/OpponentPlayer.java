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

import java.io.*;
import java.net.Socket;

class OpponentPlayer {
    DataOutputStream out;
    DataInputStream in;
    private Label message;
    private GUI gui;
    public Socket clientSocket;
    OpponentPlayer(GUI gui) {

        this.gui = gui;
        Stage window = gui.window;
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
            out = new DataOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new DataInputStream(clientSocket.getInputStream());
            message.setText("Connected Successfully to Server");
            gui.startGame();
        } catch (IOException /*| InterruptedException*/ e) {
            e.printStackTrace();
        }
    }

}
