package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionFactory {
    private final int PORT;
    private ConnectionHandler handler;

    ConnectionFactory(int port, ConnectionHandler handler) {
        this.PORT = port;
        this.handler = handler;
    }

    void acceptNewConnections() throws IOException {

        ServerSocket server = new ServerSocket(7777);
        Socket socket = server.accept();
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        this.handler.handleConnection(is, os);
        socket.close();
    }
}
