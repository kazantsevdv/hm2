package hm7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;

    private String nick;
    private String login;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            System.out.println("RemoteSocketAddress:  " + socket.getRemoteSocketAddress());
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                sendMsg("/authok " + newNick);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                System.out.println("Клиент " + nick + " прошел аутентификацию");
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }


                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        } else if (str.startsWith("/w")) {
                            String[] s = str.split(" ");
                            if (s.length != 3) {
                                this.sendMsg("не правильный формат команды");
                            } else {
                                server.privateMsg(this, s[1], s[2]);
                            }
                        } else
                            server.broadcastMsg(str, nick);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public String getNick() {
        return nick;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
