package hm6.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String HOST = "localhost";
    public static final int PORT = 8189;

    public static void main(String[] args) {

        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        Scanner sc;
        try {
            socket = new Socket(HOST, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            sc = new Scanner(System.in);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {

                        try {
                            System.out.println("Сервер " + in.readUTF());
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }).start();
            while (true) {
                System.out.println("Можно что нибудь написать...");
                String str = sc.nextLine();
                try {
                    out.writeUTF(str);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
