package hm6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        Scanner sc;
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");
            socket = server.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            sc = new Scanner(System.in);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String str = sc.nextLine();
                        try {
                            out.writeUTF(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                    System.out.println("Клиент отключился");
                    break;
                }
                System.out.println("Клиент: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }

                if (server != null) {
                    server.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
