package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class KlausurTest {


    @Test
    public void testServerSide() throws IOException {

        ServerSocket server = new ServerSocket(7777);
        Socket socket = server.accept();

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String receivedString = dis.readUTF();
        long receivedLong = dis.readLong();
        System.out.println("Server: " + receivedString);
        System.out.println("Server: " + receivedLong);

        // write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(receivedString + " there");
        dos.writeLong(++receivedLong);
    }

    @Test
    public void testClientSide() throws IOException {
        Socket socket = new Socket("localhost", 7777);

        // write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF("Hallo");
        dos.writeLong(420000);

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String receivedString = dis.readUTF();
        long receivedLong = dis.readLong();
        System.out.println("Client: " + receivedString);
        System.out.println("Client: " + receivedLong);
    }
}
