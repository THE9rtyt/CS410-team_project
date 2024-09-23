package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ChatManager {
	private ArrayList<Chat> chats;
	
	
	public ChatManager() {
		chats = new ArrayList<>();
		addChat("Hello world!");
	}
	
	
	public void addChat (String c) {
		chats.add(new Chat(c, 0));
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		float y = 15;
		g2.setColor(new Color(255, 255, 255));
		for (Chat i : chats) {
			g2.drawString(i.chat, 0, y);
			y += 20;
		}
	}
}
