package tcp;

import java.io.*;

public class KlausurIncrementProtocoll implements KlausurConnectionHandler {
    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {

        // read
        DataInputStream dis = new DataInputStream(is);
        int receivedInt = dis.readInt();
        String receivedString = dis.readUTF();
        System.out.println("Server: " + receivedInt);
        System.out.println("Server: " + receivedString);


        // write
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(++receivedInt);
        dos.writeUTF(receivedString + " added from server");

    }
}
