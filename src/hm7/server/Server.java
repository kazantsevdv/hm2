package hm7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class Server {
    private Vector<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");

                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void broadcastMsg(String msg, String nick) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + ": " + msg);
        }
    }

    public void privateMsg(ClientHandler from, String to, String msg) {
        boolean ifsend = false;
        for (ClientHandler c : clients) {
            if (c.getNick().equals(to.trim())) {
                c.sendMsg(from.getNick() + " to  " + to + ": " + msg);
                ifsend = true;
                //break; //из цыкла не выхожу на случай если есть одинаковые ники
            }
        }

        from.sendMsg(from.getNick() + " to  " + to + ": " + msg);

        //если в не найден ни один ник
        if (!ifsend) {
            from.sendMsg("Получатель не нйден");
        }


    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}
