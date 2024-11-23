package sfs;
import utils.MySerialization;
import java.io.*;

public class SimpleFileServerHandler {

    private InputStream is;
    private OutputStream os;
    private String receivedFileName;
    final byte VERSION_NUMBER = 1;

    SimpleFileServerHandler(InputStream is, OutputStream os) throws IOException {
        this.is = is;
        this.os = os;
    }

    public void runHandler() throws IOException {

        DataInputStream dis = new DataInputStream(is);

        // read header from client
        byte version = dis.readByte();
        byte command = dis.readByte();
        receivedFileName = dis.readUTF();

        // answer to client according to request
        if (command == 0) {
            this.handleGET();
            return;
        }

        if (command == 1) {
            this.handlePUT();
            return;
        }

        this.sendError(1, "invalid request");
    }


    public void handlePUT() throws IOException {

        // TODO: cases?

        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, "tests/test_files_server/" + receivedFileName);
        this.sendOK();
    }

    public void handleGET() throws IOException {

        File file = new File("tests/test_files_server/" + receivedFileName);
        if (file.exists()) {
            this.putFile();
        } else {
            this.sendError(2, "file not found");
        }
    }

    public void putFile() throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        File file = new File("tests/test_files_server/" + receivedFileName);

        // write header
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(1); // 2. command: PUT
        dos.writeUTF(receivedFileName); // 3. file name

        // write file length and content
        MySerialization ms = new MySerialization();
        ms.serializeFile(file, os);
    }

    public void sendOK() throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(3); // 2. command: OK
        dos.writeUTF(receivedFileName); // 3. file name
    }

    public void sendError(int errorCode, String errorMessage) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write
        dos.writeByte(VERSION_NUMBER); // 1. version
        dos.writeByte(2); // 2. command: ERROR
        dos.writeUTF(receivedFileName); // 3. file name
        dos.writeInt(errorCode); // 4. error code
        dos.writeUTF(errorMessage); // 5. error message
    }
}


/*
// TODO: "fix write failed (broken pipe)" error if versions are incompatible. put check elsewhere?
        if (version != this.VERSION_NUMBER) {
        this.sendError(0, "protocol versions not compatible");
            return;
                    }*/
