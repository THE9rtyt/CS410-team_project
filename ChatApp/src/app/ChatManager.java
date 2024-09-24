package app;

import java.util.ArrayList;

public class ChatManager {
	private ArrayList<Chat> chats;
	private ArrayList<Chat> queue;
	
	public ChatManager() {
		chats = new ArrayList<>();
		queue = new ArrayList<>();
	}
	
	
	public void addChat (Chat chat) {
		chats.add(chat);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Chat i : chats) {
			str = str + i.author + ": " + i.content + "\n"; 
		}
		return str;
	}
	
	public void setQueue (ArrayList<Chat> queue) {
		if (this.queue.isEmpty()) {
			this.queue = queue;
		} else {
			this.queue.addAll(queue);
		}
	}
	
	public Chat popQueue () {
		Chat c = queue.get(0);
		queue.remove(0);
		return c;
	}
}
