package network;

import app.Chat;
import java.io.*;
import java.net.Socket;
import java.util.*;

//a simple connection class for connection mangament from the server side
public class ChatConnection extends Thread {
    private static final String LOGPREFIX = "CHATCON: ";

    private Socket socket;
    private int id;
    private String username;
    private boolean registered = false;

    private DataInputStream inStream;
    private DataOutputStream outStream;

    private List<Chat> inMessages;

    public ChatConnection(Socket s, int i) {
        socket = s;
        id = i;

        inMessages = new ArrayList<>();

        try {
            startConnection();
        } catch (IOException ioe) {
            System.out.println(LOGPREFIX + "ERROR: " + ioe);
        }
    }

    private void startConnection() throws IOException {
        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());

        outStream.flush();

        username = inStream.readUTF().trim();

        // NotifyNewUser(username, id);
        outStream.writeInt(id);

        System.out.println(LOGPREFIX + "started new connection from: " + socket.getInetAddress().getHostAddress() + " as: " + username + " id: " + id);
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

                        if (registered) {
                            inMessages.add(new Chat(content, uname, ts, hash));
                        }
                    	break;
                    }
                    case 0x0f -> { //ping
                        outStream.writeByte(0x0d);
                        break;
                    }
                    default -> {
                    	throw new AssertionError(LOGPREFIX + "Unknown message type recieved: " + type);
                    }
                }
                
            }
        } catch (IOException e) {
            System.out.println(LOGPREFIX + "ERROR: " + e);
        }
    }

    public void register() {
        registered = true;
    }

    public void sendMessage(Chat message) throws IOException {
        outStream.writeByte(0x01); //sending message
        outStream.writeUTF(message.content);
        outStream.writeUTF(message.author);
        outStream.writeLong(message.getTimeStamp());
        outStream.writeUTF(message.getHash());
    }

    public ArrayList<Chat> getQueuedMessages() {
        var temp = new ArrayList<>(inMessages);
        inMessages = new ArrayList<>();
        return temp;
    }
}
