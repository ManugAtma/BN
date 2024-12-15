import sfs.SimpleFileServerClient;
import tcp.TestThread;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream(), "tests/test_files_client/");

        String requestedFileName = "server_side_file.txt";
        sfsClient.getFile(requestedFileName);
        socket.close();

        TestThread tt = new TestThread(socket);
        Thread thread = new Thread(tt);
        thread.start();

        File file = new File(requestedFileName);
        System.out.println(file.exists());
    }


}
