package app;

public class Chat {

	public String content;
	public String author;

	private final long timeStamp;
	private final String hash;

	// create a new Chat with auto generated timestamp and hash
	public Chat(String c, String a) {
		content = c;
		author = a;

		timeStamp = System.currentTimeMillis();
		hash = generateHash();
	}

	// create a new Chat with pre-defined timestamp and hash
	public Chat(String c, String a, long ts, String h) {
		content = c;
		author = a;
		timeStamp = ts;
		hash = h;
	}

	public String getHash() {
		return hash;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	// simple hash that's good enough for what we need atm
	private String generateHash() {
		return content + timeStamp;
	}

	@Override
	public String toString() {
		return "Chat: content: " + content + " author: " + author + " hash: " + hash;
	}
}
