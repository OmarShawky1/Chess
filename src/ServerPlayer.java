import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPlayer {
    private ServerSocket serverSocket;
    public Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;
    private Label message;

    ServerPlayer(GUI gui) {

        Stage window = gui.window;
        message = new Label("Waiting to connect to Opponent");

        Thread runServerSocket = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(9090);
                clientSocket = serverSocket.accept();
                out = new DataOutputStream(clientSocket.getOutputStream());
                out.flush();
                in = new DataInputStream(clientSocket.getInputStream());
                if (clientSocket.isConnected()) {
                    Platform.runLater(() -> {
                        message.setText("Connected Successfully\n" + "clientSocket.getPort(): " + clientSocket.getPort());
                        gui.startGame();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        runServerSocket.start();
        GridPane gridPane = new GridPane();
        gridPane.add(message, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        window.setScene(new Scene(gridPane, 400, 400));
        window.show();
        window.setOnCloseRequest(e -> {
            window.close();
            System.exit(0);
        });
    }

    public static void main(String[] args)  {
//        ServerPlayer server = new ServerPlayer();
//        server.start();
    }
}
