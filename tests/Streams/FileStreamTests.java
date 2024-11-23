package Streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamTests {
    @Test
    public void writeAndRead() throws Exception {
        String filename = "testfile2.txt";
        OutputStream os = new FileOutputStream(filename);

        // write
        String text = "test test";
        byte[] textAsBytes = text.getBytes();
        os.write(textAsBytes);

        //read
        InputStream is = new FileInputStream(filename);
        byte[] readBuffer = new byte[100];
        is.read(readBuffer);

        String readString = new String(readBuffer);
        StringBuilder sb = new StringBuilder();
        sb.append("wrote: ");
        sb.append(text);
        sb.append(" | read: ");
        sb.append(readString);
        System.out.println(sb);

        readString = readString.substring(0, text.length());
        System.out.println(readString);

        Assertions.assertTrue(readString.equals(text));


    }

}
