package sfs;
import java.io.*;
import utils.MySerialization;


public class SimpleFileServerClient {
    private InputStream is;
    private OutputStream os;
    final byte VERSION_NUMBER = 1;
    final String rootDirectory;
    final byte GET_PDU = 0x00;
    final byte PUT_PDU = 0x01;
    final byte ERROR_PDU = 0x02;
    final byte OK_PDU = 0x03;

    public SimpleFileServerClient(InputStream is, OutputStream os, String rootDirectory) throws IOException {
        this.is = is;
        this.os = os;
        this.rootDirectory = rootDirectory;
    }

    public void putFile(String putFileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        File file = new File(rootDirectory + putFileName);

        // write PUT_PDU
        this.writeHeader(dos, PUT_PDU, putFileName);  // write header
        MySerialization ms = new MySerialization();
        ms.serializeFile(file, os);  // write file

        // read and output answer from server
        DataInputStream dis = new DataInputStream(is);
        byte versionFromServer = dis.readByte();
        System.out.println("version received from server:" + versionFromServer);
        byte commandFromServer = dis.readByte();
        System.out.println("command received from server:" + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("file name received from server: " + fileNameFromServer);

        if (commandFromServer == OK_PDU) this.handleOK();

        if (commandFromServer == ERROR_PDU) this.handleERROR();
    }

    public void getFile(String fileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write GET_PDU
        this.writeHeader(dos, GET_PDU, fileName);

        // read header from server and output it
        DataInputStream dis = new DataInputStream(is);
        int versionFromServer = dis.readByte();
        System.out.println("version received from server: " + versionFromServer);
        int commandFromServer = dis.readByte();
        System.out.println("command received from server: " + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("requested file name received from server: " + fileNameFromServer);

        // GET request succeeded, save file
        if (commandFromServer == 1) this.handlePUT(fileName);

        // GET request did not succeed, output ERROR received from server
        if (commandFromServer == 2) this.handleERROR();
    }

    public void handlePUT(String fileName) throws IOException {
        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, rootDirectory + fileName);
    }

    public void handleOK() throws IOException {
        System.out.println("request successful");
    }

    public void handleERROR() throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int errorCode = dis.readInt();
        String errorMessage = dis.readUTF();
        System.out.println("error code received from server: " + errorCode);
        System.out.println("error message received from server: " + errorMessage);
    }

    public void writeHeader(DataOutputStream dos, byte command, String fileName) throws IOException {

        dos.writeByte(VERSION_NUMBER);
        dos.writeByte(command);
        dos.writeUTF(fileName);
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
