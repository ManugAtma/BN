package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

public class KlausurCFTest {


    @Test
    public void testServerSide() throws IOException {
        KlausurConnectionFactory kcf = new KlausurConnectionFactory(7777,new KlausurIncrementProtocoll());
        kcf.acceptNewConnection();
    }

    @Test
    public void testClientSide() throws IOException {
        Socket socket = new Socket("localhost", 7777);

        // write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(666);
        dos.writeUTF("Klausur Test");

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        int receivedInt = dis.readInt();
        String receivedString = dis.readUTF();
        System.out.println("Client: " + receivedInt);
        System.out.println("Client: " + receivedString);
    }
}
