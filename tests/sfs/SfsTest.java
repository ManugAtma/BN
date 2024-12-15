package sfs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;


public class SfsTest {

    @Test
    public void testServer() throws IOException {

        SimpleFileServer server = new SimpleFileServer(4444);
        server.start();
    }


    @Test
    public void testGetAndPutClient() throws IOException {

        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(
                socket.getInputStream(), socket.getOutputStream(), "tests/test_files_client/");

        // GET
        String requestedFileName = "server_side_file.txt";
        sfsClient.getFile(requestedFileName);

        // PUT
        sfsClient.putFile("client_side_file.txt");
        socket.close();

        File file = new File("tests/test_files_client/" + requestedFileName);
        Assertions.assertTrue((file.exists()));
    }


    @Test
    public void testPutClient() throws IOException {

        // create file
        String fileName = "client_side_file.txt";
        File file = new File("tests/test_files_client/" + fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(42);
        os.close();

        // connect to server
        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(
                socket.getInputStream(), socket.getOutputStream(), "tests/test_files_client/");

        // send PUT request
        sfsClient.putFile(fileName);

        socket.close();
    }
}
