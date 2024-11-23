import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        int [] arr = new int[4];
        for (int item: arr){
            item = 3;
        }

        for (int item: arr){
            System.out.println(item);
        }



    }
}