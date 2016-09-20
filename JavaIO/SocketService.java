import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by billin on 16-9-13.
 * <p>Socket service</p>
 */
public class SocketService {

    private static final int PORT = 12345;

    private List<Socket> mClient;

    private void start() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("service start fail...");
            System.exit(1);
        }

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                mClient.add(socket);
                new Reader(socket).start();
            } catch (IOException e) {
                System.out.println("connect error...");
                break;
            }
        }

    }


    private class Sender implements Runnable {

        private Socket client;

        private String message;

        Sender(Socket client, String message) {
            this.client = client;
            this.message = message;
        }

        @Override
        public void run() {
            for (Socket socket : mClient) {
                if (client.equals(socket)) {
                    continue;
                }
                try {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(client.getInetAddress() + ": " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("sender error");
                }

            }
        }

        void start() {
            new Thread(this).start();
        }
    }

    private class Reader implements Runnable {

        private Socket socket;

        Reader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (true) {
                DataInputStream inputStream = null;
                try {
                    inputStream = new DataInputStream(socket.getInputStream());
                    String message = inputStream.readUTF();
                    System.out.println(socket.getInetAddress() + ": " + message);
                    new Sender(socket, message).start();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("read error");

                    try {
                        mClient.remove(socket);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        System.out.println("can not close socket");
                    }
                    break;
                }
            }
        }

        void start() {
            new Thread(this).start();
        }
    }

    public static void main(String[] args) {
        System.out.println("System starting...");
        SocketService service = new SocketService();
        service.mClient = new ArrayList<>();
        service.start();
    }
}
