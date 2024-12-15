package sfs;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {
    private SimpleFileServer handler;
    private int PORT;

    public SimpleFileServer(int port) {
        this.PORT = port;
    }

    public void start() throws IOException {

        ServerSocket server = new ServerSocket(PORT);

        int threadID = 1;
        while (true) {
            Socket socket = server.accept();
            SimpleFileServerHandler handler = new SimpleFileServerHandler(
                    socket.getInputStream(), socket.getOutputStream(),"tests/test_files_server/", threadID);
            Thread thread = new Thread(handler);
            thread.start();
            System.out.println("thread no " + threadID + " started");
            threadID++;
        }
    }
}
