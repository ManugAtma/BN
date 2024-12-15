package tcp;
import java.net.Socket;

public class TestThread implements Runnable {
    Socket socket;

    public TestThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Thread running");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}
