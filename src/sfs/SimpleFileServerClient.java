package sfs;
import java.io.*;
import utils.MySerialization;


public class SimpleFileServerClient {
    private InputStream is;
    private OutputStream os;
    final byte VERSION_NUMBER = 1;

    SimpleFileServerClient(InputStream is, OutputStream os) throws IOException {
        this.is = is;
        this.os = os;
    }

    public void putFile(String putFileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        File file = new File(putFileName);

        // write header
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(1); // 2. command: PUT
        dos.writeUTF(putFileName); // 3. file name

        // write file length and content
        MySerialization ms = new MySerialization();
        ms.serializeFile(file, os);
    }

    public void getFile(String getFileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write header
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(0); // 2. command: GET
        dos.writeUTF(getFileName); // 3. file name

        // read header from server and output it
        DataInputStream dis = new DataInputStream(is);
        int versionFromServer = dis.readByte();
        System.out.println("version received from server: " + versionFromServer);
        int commandFromServer = dis.readByte();
        System.out.println("command received from server: " + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("requested file name received from server: " + fileNameFromServer);

        // GET request succeeded, save file
        if (commandFromServer == 1) {

            // read file from server
            MySerialization ms = new MySerialization();
            ms.deserializeFile(is, getFileName);
        }

        // GET request did not succeed, output ERROR received from server
        if (commandFromServer == 2) {
            int errorCode = dis.readInt();
            String errorMessage = dis.readUTF();
            System.out.println("error code received from server: " + errorCode);
            System.out.println("error message received from server: " + errorMessage);
        }
    }



    /*public void sendError(String fileName, int errorCode, String errorMessage) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(2); // 2. command: ERROR
        dos.writeUTF(fileName); // 3. file name
        dos.writeInt(errorCode); // 4. error code
        dos.writeUTF(errorMessage); // 5. error message
    }

    public void sendOK(String fileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(3); // 2. command: OK
        dos.writeUTF(fileName); // 3. file name
    }*/
}
