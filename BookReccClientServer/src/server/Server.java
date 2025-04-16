package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket s = serverSocket.accept();
            System.out.println("Nuovo client connesso al server...");
            new Thread(new ClientHandler(s));

        }

    }
}
