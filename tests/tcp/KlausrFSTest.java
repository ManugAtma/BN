package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class KlausrFSTest {

    @Test
    public void testFileStreams() throws IOException {

        // write
        OutputStream os = new FileOutputStream("test_klausur.txt");
        String s = "hallo klausur";
        byte[] stringAsBytes = s.getBytes();
        os.write(stringAsBytes);

        // read
        InputStream is = new FileInputStream("test_klausur.txt");
        byte[] readBuffer = new byte[100];
        is.read(readBuffer);
        String s2 = new String(readBuffer);
        System.out.println(s2);
    }

    @Test
    public void testFileAndDataStreams() throws IOException {

        // write
        OutputStream os = new FileOutputStream("test_klausur.txt");
        DataOutputStream dos = new DataOutputStream(os);
        String s = "hallo klausur";
        dos.writeUTF(s);

        // read
        InputStream is = new FileInputStream("test_klausur.txt");
        DataInputStream dis = new DataInputStream(is);
        String s2 = dis.readUTF();
        System.out.println(s2);
    }
}
