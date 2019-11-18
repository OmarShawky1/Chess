import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPlayer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerPlayer(Stage window) throws IOException {

        Platform.runLater(() -> {
            try {
                serverSocket = new ServerSocket(9090);
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Label message = new Label("Waiting to connect to Opponent");
        GridPane gridPane = new GridPane();
        gridPane.add(message, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        window.setScene(new Scene(gridPane, 400, 400));
        window.show();
    }

    public static void main(String[] args) throws IOException {
//        ServerPlayer server = new ServerPlayer();
//        server.start();
    }

//    public void start() throws IOException {
//        serverSocket = new ServerSocket(9050);
//        clientSocket = serverSocket.accept();
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String greeting = in.readLine();
//        if ("hello server".equalsIgnoreCase(greeting)) {
//            out.println("hello client");
//        } else {
//            out.println("unrecognised greeting");
//        }
//    }
//
//    public void stop() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//        serverSocket.close();
//    }


    public void start() {

    }
}
