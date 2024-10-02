package network;

import app.Chat;
import app.Dashboard;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

//a standalone class that hosts a ChatApp server
public class ChatServer extends Thread {

    private static final String LOGPREFIX = "SERVER: ";

    private ServerSocket server;
    private int port = 9999;

    public HashMap<String, ChatConnection> clients = new HashMap<String, ChatConnection>();
    public int clientID = 0;
    
    public Dashboard db;
    
    
    public ChatServer(int p, Dashboard d) {
        port = p;
        db = d;

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
                
                var newChat = new ChatConnection(newSocket, clientID++, this);
                newChat.start();
                
                if(newChat.getConnectionIP().equals(this.getServerIP())) {
                	newChat.register();
                }
                
            }
        } catch (IOException ioe) {
            System.out.println(LOGPREFIX + "ERROR: " + ioe);
        }
    }

    public String getServerIP() {
        return server.getInetAddress().getHostAddress();
    }

    public void register(String uname) {
        clients.get(uname).register();
    }

    public void sendMessage(Chat message) throws IOException {
        for (Entry<String, ChatConnection> connection : clients.entrySet()) {
        	
            connection.getValue().sendMessage(message);
        }
    }
    
    public ArrayList<Chat> getQueuedMessages() {
        var temp = new ArrayList<Chat>();
        
        for (Entry<String, ChatConnection> connection : clients.entrySet()) {
        	
            temp.addAll(connection.getValue().getQueuedMessages());
        }
       
        return temp;
    }

	public void notifyNewUser(String username, ChatConnection chatConnection) {
		clients.put(username, chatConnection);
		if(!chatConnection.getConnectionIP().equals(this.getServerIP())) {
			db.notifyNewConnection(username);			
		}
	}
}
