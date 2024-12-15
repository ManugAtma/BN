package sfs;

import utils.MySerialization;

import java.io.*;

public class SimpleFileServerHandler implements Runnable {

    private InputStream is;
    private OutputStream os;
    private String receivedFileName;
    final byte VERSION_NUMBER = 1;
    private String rootDirectory;
    final byte GET_PDU = 0x00;
    final byte PUT_PDU = 0x01;
    final byte ERROR_PDU = 0x02;
    final byte OK_PDU = 0x03;
    private int THREAD_ID;


    public SimpleFileServerHandler(InputStream is, OutputStream os, String rootDirectory, int threadID) throws IOException {
        this.is = is;
        this.os = os;
        this.rootDirectory = rootDirectory;
        this.THREAD_ID = threadID;
    }

    public void run() {

        try {
            while (true) {
                DataInputStream dis = new DataInputStream(is);

                // read header from client
                byte version = dis.readByte();
                byte command = dis.readByte();
                receivedFileName = dis.readUTF();

                // answer to client according to request
                if (version != VERSION_NUMBER) {
                    System.out.println("incompatible version");
                    return;
                }

                switch (command) {
                    case 0:
                        this.handleGET();
                        break;
                    case 1:
                        this.handlePUT();
                        break;
                    default:
                        this.sendError(1, "invalid request");
                }
            }

        } catch (IOException e) {
            System.out.println("thread no " + this.THREAD_ID + ": connection closed by client");
            // e.printStackTrace();
        }

    }


    public void handlePUT() throws IOException {

        // deserialize and save file
        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, rootDirectory + receivedFileName);

        // check if file was saved successfully and inform client
        File file = new File(rootDirectory + receivedFileName);
        if (file.exists()) {
            this.sendOK();
        } else {
            this.sendError(3, "file couldn't be saved");
        }
    }

    public void handleGET() throws IOException {

        File file = new File(rootDirectory + receivedFileName);
        if (file.exists()) {
            this.putFile();
        } else {
            this.sendError(2, "file not found");
        }
    }

    public void putFile() throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        File file = new File(rootDirectory + receivedFileName);

        this.writeHeader(dos, PUT_PDU, receivedFileName);
        MySerialization ms = new MySerialization(); // write file length
        ms.serializeFile(file, os); // write file content
    }

    public void sendOK() throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        this.writeHeader(dos, OK_PDU, receivedFileName);
    }

    public void sendError(int errorCode, String errorMessage) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        this.writeHeader(dos, ERROR_PDU, receivedFileName);
        dos.writeInt(errorCode);
        dos.writeUTF(errorMessage);
    }

    private void writeHeader(DataOutputStream dos, byte command, String fileName) throws IOException {

        dos.writeByte(VERSION_NUMBER);
        dos.writeByte(command);
        dos.writeUTF(fileName);
    }
}