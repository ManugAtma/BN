package ueben;

import org.junit.jupiter.api.Test;
import sfs.SimpleFileServerClient;

import java.io.*;
import java.net.Socket;

public class TestUeben {


    @Test
    void practiceSendFile() throws IOException {
        File file = new File("uebungs_file.txt");
        // boolean b = file.createNewFile();
        // System.out.println(b);
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        String s = "test string";

        // without UTF
        // byte [] bytes = s.getBytes();
        // fos.write(bytes);

        dos.writeUTF(s);

        Socket socket = new Socket("127.0.0.1", 4444);

        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream(), "");
        sfsClient.putFile("uebungs_file.txt");
    }

    @Test
    void practiceGetFile() throws IOException {
        Socket socket = new Socket("127.0.0.1", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream(), "");
        sfsClient.getFile("server_side_file.txt");
    }

    @Test
    void practiceReadFromFile() throws IOException {
        FileInputStream fis = new FileInputStream("uebungs_file.txt");
        DataInputStream dis = new DataInputStream(fis);
        String s = dis.readUTF();
        System.out.println(s);

       /* FileInputStream fis2 = new FileInputStream("uebungs_file.txt");
        byte[] data = new byte[13];
        fis2.read(data);
        String s2 = new String(data);
        System.out.println(s2);*/

      /*  byte [] arr = fis.readAllBytes();
        String str = new String(arr);
        System.out.println(str);*/
    }

    @Test
    void practiceSystemIn() throws IOException {
        System.out.println("Bitte was eingeben:");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            String readString = br.readLine();
            System.out.println("read: " + readString);
        } catch (IOException ex) {
            System.err.println("couldn’t read data (fatal)");
            System.exit(0);
        }
    }
}

// PrintStream - to write String
//PrintStream ps = new PrintStream(os);
//ps.println("Hello Stream");

// BufferedReader - to read String
// InputStreamReader isr = new InputStreamReader(is);
// BufferedReader br = new BufferedReader(isr);
// try {
// readString = br.readLine();
// System.out.println("read: " + readString);
// } catch (IOException ex) {
//        System.err.println("couldn’t read data (fatal)");
// System.exit(0);
// }
