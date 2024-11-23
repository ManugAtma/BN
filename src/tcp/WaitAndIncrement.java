package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WaitAndIncrement implements ConnectionHandler {
    public void handleConnection(InputStream is, OutputStream os) throws IOException {
        for (int i = 0; i < 10; i++) {
            int received = is.read();
            System.out.println("Server received: "  + received);
            received++;
            os.write(received);
            System.out.println("Server sent:     " + received);
        }
    }
}
