package utils;

import java.io.*;

public class MySerialization {

    public void serialize(int[] intArray, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(intArray.length);
        for (int intToSend : intArray) {
            dos.writeInt(intToSend);
        }
    }

    public int[] deserialize(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int length = dis.readInt();
        int[] receivedInts = new int[length];
        for (int i = 0; i < receivedInts.length; i++) {
            receivedInts[i] = dis.readInt();
        }
        return receivedInts;
    }

    public void serialize2D(int[][] intArray2D, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(intArray2D.length);
        for (int[] intArray1D : intArray2D) {
            serialize(intArray1D, dos);
        }
    }

    public int[][] deserialize2D(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int[][] received1DArr = new int[dis.readInt()][];
        for (int i = 0; i < received1DArr.length; i++) {
            received1DArr[i] = deserialize(dis);
        }
        return received1DArr;
    }

    public void serializeFile(File file, OutputStream os) throws IOException {

        // InputStream to read from file
        InputStream is = new FileInputStream(file);

        // write file length to server
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(file.length());
        // System.out.println("client sent: " + file.length());

        // read content from file and write it to server
        for (long i = 0; i < file.length(); i++) {
            dos.write(is.read());
        }
    }

    public File deserializeFile(InputStream is, String fileName) throws IOException {

        // read file length
        DataInputStream dis = new DataInputStream(is);
        long fileLength = dis.readLong();
        // System.out.println("file length from server: " + fileLength);


        // file and FileOutputStream to write received data to file
        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        // read from client and write to file
        for (long i = 0; i < fileLength; i++) {
            fos.write(dis.read());
        }

        return file;
    }
}
