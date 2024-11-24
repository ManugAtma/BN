package sfs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class SfsTest {

    @Test
    public void testServer() throws IOException {

        ServerSocket server = new ServerSocket(4444);
        Socket socket = server.accept();

        SimpleFileServerHandler handler = new SimpleFileServerHandler(socket.getInputStream(), socket.getOutputStream());
        handler.runHandler();

        socket.close();
    }

    @Test
    public void testGetClient() throws IOException {

        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream());

        String requestedFileName = "server_side_file.txt";
        sfsClient.getFile(requestedFileName);
        socket.close();

        File file = new File(requestedFileName);
        Assertions.assertTrue((file.exists()));
    }


    @Test
    public void testPutClient() throws IOException {

        // create file
        String fileName = "client_side_file.txt";
        File file = new File(fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(42);
        os.close();

        // connect to server
        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream());

        // send PUT request
        sfsClient.putFile(fileName);

        // read and output answer from server
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        byte versionFromServer = dis.readByte();
        System.out.println("version received from server:" + versionFromServer);
        byte commandFromServer = dis.readByte();
        System.out.println("command received from server:" + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("file name received from server: " + fileNameFromServer);

        // PUT request did not succeed, output ERROR received from server
        if (commandFromServer == 2) {
            int errorCode = dis.readInt();
            String errorMessage = dis.readUTF();
            System.out.println("error code received from server: " + errorCode);
            System.out.println("error message received from server: " + errorMessage);
        }

        socket.close();
    }
}
