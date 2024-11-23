package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionFactoryTest {

    @Test
    public void testServerSide() throws IOException {
        ConnectionFactory cf = new ConnectionFactory(7777, new EchoProtocol());
        cf.acceptNewConnections();

    }

    @Test
    public void testClientSide () throws IOException {

        Socket socket = new Socket("localhost",7777);

        //write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        int sentByte = 15;
        dos.writeByte(sentByte);

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        byte receivedByte = dis.readByte();
        assertEquals(sentByte,15);


    }
}
