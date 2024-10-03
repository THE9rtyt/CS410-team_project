package app;

import java.util.ArrayList;

import ui.ChatPanel;

public class ChatManager {
	private ArrayList<Chat> chats;
	private ArrayList<Chat> queue;
	private ChatPanel cp;
	
	public ChatManager() {
		chats = new ArrayList<>();
		queue = new ArrayList<>();
	}
	
	public void addChat (Chat chat) {
		chats.add(chat);
		cp.updateText();
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Chat i : chats) {
			str = str + i.author + ": " + i.content + " [" + i.timestampToDate() + "]" + "\n"; 
		}
		return str;
	}
	public String queueToString() {
		String str = "";
		for (Chat i : queue) {
			str = str + i.author + ": " + i.content + " [" + i.timestampToDate() + "]" + "\n"; 
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
		if (queue.size() > 0) {
			Chat c = queue.get(0);
			queue.remove(0);
			return c;
		}
		return null;
	}
	
	public void setPanel (ChatPanel cp) {
		this.cp = cp;
	}

}
