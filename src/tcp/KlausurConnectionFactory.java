package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class KlausurConnectionFactory {
    private int port;
    private KlausurConnectionHandler handler;

    public KlausurConnectionFactory(int port, KlausurConnectionHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void acceptNewConnection() throws IOException {
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        this.handler.handleConnection(is, os);
        socket.close();
    }
}
