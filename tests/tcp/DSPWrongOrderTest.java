package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

public class DSPWrongOrderTest {

    @Test
    public void testServerSide() throws IOException {
        ConnectionFactory cf = new ConnectionFactory(7777, new DataStreamProtocol());
        cf.acceptNewConnections();
    }

    @Test
    public void testClientSide() throws IOException {

        Socket socket = new Socket("localhost", 7777);

        // write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(42);
        dos.writeDouble(3.3);
        dos.writeUTF("Hi");

        // read in wrong order
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        double receivedDouble = dis.readDouble();
        String receivedString = dis.readUTF(); // read String last instead of first
        long receivedLong = dis.readLong();


        System.out.println("Client: " + receivedDouble);
        System.out.println("Client: " + receivedLong);
        System.out.println("Client: " + receivedString);

        socket.close();
    }

}

