package tcp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WaitAndIncrementTest {


    @Test
    public void testServerSide() throws IOException {
        ConnectionFactory cf = new ConnectionFactory(7777, new WaitAndIncrement());
        cf.acceptNewConnections();
    }

    @Test
    public void testClientSide() throws IOException {

        Socket socket = new Socket("localhost", 7777);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        os.write(1);
        System.out.println("Client sent:     1");

        for (int i = 0; i < 10; i++) {
            int received = is.read();
            System.out.println("Client received: " + received);
            received++;
            os.write(received);
            System.out.println("Client sent:     " + received);
        }
    }
}


