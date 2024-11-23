package utils;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SerializeFileTest {

    @Test
    public void testServerSide() throws IOException {

        ServerSocket server = new ServerSocket(7777);
        Socket socket = server.accept();
        InputStream is = socket.getInputStream();

        // create empty file
        DataOutputStream daos = new DataOutputStream(new FileOutputStream("targetFile.txt"));

        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, "targetFile.txt");

        socket.close();
    }


    @Test
    public void testClientSide() throws IOException {
        String sampleData = "TestContent6666";
        String fileNameSource = "sourceFile.txt";

        // fill file
        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);

        // file exists - lets stream it
        File file = new File(fileNameSource);
        MySerialization ms = new MySerialization();

        Socket socket = new Socket("localhost", 7777);
        OutputStream os = socket.getOutputStream();
        ms.serializeFile(file, os);

        socket.close();
    }
}
