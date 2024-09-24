package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ChatManager {
	private ArrayList<Chat> chats;
	private ArrayList<Chat> queue;
	
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
		g2.setColor(new Color(0, 0, 0));
		for (Chat i : chats) {
			g2.drawString(i.chat, 0, y);
			y += 20;
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Chat i : chats) {
			str = str + i.chat + "\n"; 
		}
		return str;
	}
	
	public void setQueue (ArrayList<Chat> queue) {
		this.queue = queue;
	}
	
	public Chat getQueue () {
		return null;
	}
	
	public Chat returnApproved () {
		return null;
	}
}
