package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EchoProtocol  implements ConnectionHandler {
    public void handleConnection(InputStream is, OutputStream os) throws IOException {
        int received = is.read();
        os.write(received);
    }

}