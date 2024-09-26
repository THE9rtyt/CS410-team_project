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
		
		//test
//		queue.add(new Chat("Hello everyone! How are you?", "Alice"));
//		queue.add(new Chat("This is a test message.", "Bob"));
//		queue.add(new Chat("Can anyone help me with my issue?", "Charlie"));
//		queue.add(new Chat("Looking forward to our meeting tomorrow.", "David"));
//		queue.add(new Chat("Does anyone know the schedule for today?", "Eve"));
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
			str = str + i.author + ": " + i.content + " [" + i.getTimeStamp() + "]" + "\n"; 
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
	
	public void setPanel (ChatPanel cp) {
		this.cp = cp;
	}

}
