package java.main.io.net;

import java.io.*;
import java.net.Socket;

/**
 * Created by billin on 16-9-13.
 * <p>socket client</p>
 */
public class SocketClient {

    private static final String IP = "192.168.1.2";//服务器地址

    private static final int PORT = 12345;//服务器端口号

    public static void main(String[] args) {
        handler();
    }

    private static void handler() {
        try {

            //实例化一个Socket，并指定服务器地址和端口
            Socket client = new Socket(IP, PORT);

            //开启两个线程，一个负责读，一个负责写
            new Thread(new ReadHandlerThread(client)).start();
            new Thread(new WriteHandlerThread(client)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 *处理读操作的线程
 */
class ReadHandlerThread implements Runnable {

    private Socket client;

    ReadHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        while (true) {
            try {
                //读取服务器端数据
                dis = new DataInputStream(client.getInputStream());
                String receive = dis.readUTF();
                System.out.println(receive);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (dis != null) {
                        dis.close();
                    }
                    if (client != null) {
                        client = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
    }
}

/*
 * 处理写操作的线程
 */
class WriteHandlerThread implements Runnable {

    private Socket client;

    WriteHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            while (true) {

                //取得输出流
                dos = new DataOutputStream(client.getOutputStream());

                //键盘录入
                br = new BufferedReader(new InputStreamReader(System.in));
                StringBuilder send = new StringBuilder();
//                while (br.ready())
                    send = send.append(br.readLine());

                //发送数据
                dos.writeUTF(send.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (br != null) {
                    br.close();
                }
                if (client != null) {
                    client = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
