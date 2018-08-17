package java.main.io.local;

import java.io.*;

/**
 * Created by billin on 16-5-18.
 *
 */
public class InputReader {

    private static void readWithDataInputStream(){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream("./src/mydemo.Max.java"));
            int s = in.readChar();
            while (in.available()>=2){
                System.out.println(s);
                s = in.readChar();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readWithBufferedRead(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/mydemo.Max.java"));
            String s = in.readLine();
            while (s!=null){
                System.out.println(s);
                s = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeWithBufferedWrite(){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./src/test"));
            out.write("Hello World");
            out.newLine();
            out.append("Hello World2");
            out.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readWithDataInputStream();
        System.out.println();
        readWithBufferedRead();
        writeWithBufferedWrite();
    }
}
