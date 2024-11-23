package socket;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {

    @Test
    public void readAndWrite() throws Exception {
        ServerSocket server = new ServerSocket(7777);
        Socket socket = server.accept();

        // read
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        byte receivedNum = dis.readByte();
        receivedNum++;


        // write
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeByte(receivedNum);
    }
}


// -- borrowBook
// happy path: in bib and ava (in middle)
// happy path: book in middle, but not ava
// happy path: mehrmals: erst is ava, dann nicht ava
// best case: empty bib
// (close to best: book at beginning)
// worst case: not in bib, bib is full
// fehlerfall: wrong isbn
// Grenzfall: book at beginning or end of inventory? noooo


// -- getRN
// happypath1: some Novels, all matchT and rel > 7, z.B.9
// happypath2: some Novels, some matchT and rel > 7
// happypath3: some Novels, some only matchT and some only rel > 7
// happypath4: no novels
// fehlerfall 1: empty as arg
// fehlerfall 2: null String as arg
// best case: empty bib
// worst case: all Novels, all Romance, but none rel > 7, full bib
// Grenzfall 1: ava and rel == 8? als normalfall?
// Grenzfall 2: ava and rel == 7? als normalfall?