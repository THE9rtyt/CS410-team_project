package network;

import app.Chat;
import app.ChatManager;
import java.io.*;
import java.net.Socket;
import java.util.*;

//a standalone class that connects to a ChatApp socket
public class ChatClient extends Thread {

    private static final String LOGPREFIX = "CLIENT: ";

    private Socket socket;
    private int id;
    private String username;
    private int port;
    private String host;

    private DataInputStream inStream;
    private DataOutputStream outStream;

    private List<Chat> inMessages;
    private ChatManager cm;

    public ChatClient(int p, String h, String u) {
        port = p;
        host = h;
        username = u;

        inMessages = new ArrayList<>();

        try {
            startClient();
        } catch (IOException ioe) {
            System.out.println(LOGPREFIX + "ERROR: " + ioe);
        }
    }

    private void startClient() throws IOException {
        //create, open and connect socket
        socket = new Socket(host, port);

        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());

        outStream.flush();

        outStream.writeUTF(username);
        id = inStream.readInt();

        System.out.println(LOGPREFIX + "started and connected to " + socket.getInetAddress().getHostAddress() + " as: " + username + " id: " + id);
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte type = inStream.readByte();
                System.out.println(LOGPREFIX + "type: " + type);
                switch (type) {
                    case 0x01 -> {
                        //message
                        String content = inStream.readUTF();
                        String uname = inStream.readUTF();
                        long ts = inStream.readLong();
                        String hash = inStream.readUTF();

                        System.out.println(LOGPREFIX + "new message recieved: " + content);

                        cm.addChat(new Chat(content, uname, ts, hash));
                        // inMessages.add(new Chat(content, uname, ts, hash));
                    }
                    case 0x0f -> //ping
                        outStream.writeByte(0x0d);
                    default -> {
                    }
                }
                throw new AssertionError(LOGPREFIX + "Unknown message type recieved: " + type);
            }
        } catch (IOException e) {
            System.out.println(LOGPREFIX + "ERROR: " + e);
        }
    }

    public void sendMessage(String message) throws IOException {
        Chat c = new Chat(message, username);
        sendMessage(c);
    }

    public void sendMessage(Chat message) throws IOException {
        outStream.writeByte(0x01); //sending message
        outStream.writeUTF(message.content);
        outStream.writeUTF(username);
        outStream.writeLong(message.getTimeStamp());
        outStream.writeUTF(message.getHash());
    }

    public ArrayList<Chat> getQueuedMessages() {
        var temp = new ArrayList<>(inMessages);
        inMessages = new ArrayList<>();
        return temp;
    }

    public void setChatManager(ChatManager cm) {
        this.cm = cm;
    }

    public String getUserName() {
        return username;
    }
}
