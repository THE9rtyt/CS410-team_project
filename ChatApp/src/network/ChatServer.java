package network;

import app.Chat;
import app.Dashboard;

import java.io.*;
import java.net.*;
import java.util.*;

//a standalone class that hosts a ChatApp server
public class ChatServer extends Thread {

    private static final String LOGPREFIX = "SERVER: ";

    private ServerSocket server;
    private int port = 9999;

    public ArrayList<ChatConnection> clients = new ArrayList<>();
    public int clientID = 0;
    
    
    public ChatServer(int p) {
        port = p;

        try {
            startServer();
        } catch (IOException ioe) {
            System.out.println(LOGPREFIX + "ERROR: " + ioe);
        }
    }

    private void startServer() throws IOException {
        server = new ServerSocket(port, 0, InetAddress.getLocalHost());

        System.out.println(LOGPREFIX + "started at " + server.getInetAddress().getHostAddress());
    }

    @Override
    public void run() {
        try {
            while (true) {
                // server accept loop
                // waits for a new connection, accepts it and throws it into a list of connections and starts a thread for it
                Socket newSocket = server.accept();

                System.out.println(LOGPREFIX + "new connection at " + newSocket.getInetAddress().getHostAddress());
                var newChat = new ChatConnection(newSocket, clientID++);
                clients.add(newChat);
                registerId(clientID - 1);
                newChat.start();
            }
        } catch (IOException ioe) {
            System.out.println(LOGPREFIX + "ERROR: " + ioe);
        }
    }

    public String getServerIP() {
        return server.getInetAddress().getHostAddress();
    }

    public void registerId(int id) {
        clients.get(id).register();
    }

    public void sendMessage(Chat message) throws IOException {
        for (var connection : clients) {
            connection.sendMessage(message);
        }
    }
    
    public ArrayList<Chat> getQueuedMessages() {
        var temp = new ArrayList<Chat>();

        for (var connection : clients) {
            temp.addAll(connection.getQueuedMessages());
        }
       
        return temp;
    }
}
