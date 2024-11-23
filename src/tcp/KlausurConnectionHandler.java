package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface KlausurConnectionHandler {
    public void handleConnection(InputStream is, OutputStream os) throws IOException;
}
