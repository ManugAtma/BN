package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {

    @Test
    public void arrayTest() throws IOException {
        MySerialization ms = new MySerialization();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int[] sample = new int[]{1,2,3,4};
        ms.serialize(sample, os);
        byte[] serializedData = os.toByteArray();
        InputStream is = new ByteArrayInputStream(serializedData);
        int result[] = ms.deserialize(is);
        Assertions.assertArrayEquals(sample, result);
    }

    @Test
    public void arrayTest2D() throws IOException {
        MySerialization ms = new MySerialization();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int[][] sample = {{1,2},{3,4},{5,6,7}};
        ms.serialize2D(sample, os);
        byte[] serializedData = os.toByteArray();
        InputStream is = new ByteArrayInputStream(serializedData);
        int result[][] = ms.deserialize2D(is);
        Assertions.assertArrayEquals(sample, result);
    }
}

