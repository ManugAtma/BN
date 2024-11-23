package tcp;

import java.io.*;

public class DataStreamProtocol implements ConnectionHandler {
    public void handleConnection(InputStream is, OutputStream os) throws IOException {

        // read
        DataInputStream dis = new DataInputStream(is);
        long receivedLong = dis.readLong();
        double receivedDouble = dis.readDouble();
        String receivedString = dis.readUTF();

        System.out.println("Server: " + receivedLong);
        System.out.println("Server: " + receivedDouble);
        System.out.println("Server: " + receivedString);

        // write in reversed order
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(receivedString);
        dos.writeDouble(receivedDouble);
        dos.writeLong(receivedLong);
    }
}
