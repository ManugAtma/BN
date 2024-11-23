package tcp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestTest {

    @Test
    public void testTest() throws IOException{
        Socket socket = new Socket("141.45.154.136", 4444);
        OutputStream os = socket.getOutputStream();

        String s = "594526 | Manug Atmacayan";
        byte[] stringAsBytes = s.getBytes();

        os.write(stringAsBytes);

        InputStream is = socket.getInputStream();
        int i = is.read();
        System.out.println(i);
    }
}
