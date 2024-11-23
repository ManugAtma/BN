package socket;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocketTests {

    @Test
    public void readAndWrite() throws Exception {

        Socket socket = new Socket("localhost",7777);

        //write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeByte(1);

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        byte receivedNum = dis.readByte();
        assertEquals(2,receivedNum);
    }
}
