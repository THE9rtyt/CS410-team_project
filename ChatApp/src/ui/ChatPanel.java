package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.Chat;
import app.ChatManager;
import app.User;
import network.ChatClient;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ChatManager cm;
	private JTextField messageBox;
	private JTextPane chatBox;
	private String last_str;
	private ChatClient cc;
	/**
	 * Create the panel.
	 */
	public ChatPanel(ChatManager cm, ChatClient chat, String username) {
		this.cm = cm;
		this.cc = chat;
		setLayout(new BorderLayout(0, 0));
		
		messageBox = new JTextField();
		add(messageBox, BorderLayout.SOUTH);
		messageBox.setColumns(10);
		
		chatBox = new JTextPane();
		JScrollPane scroll = new JScrollPane(chatBox);
		chatBox.setText(cm.toString());
		add(scroll, BorderLayout.CENTER);
		last_str = new String(this.cm.toString());
		messageBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// cm.addChat(new Chat(messageBox.getText(), username));
				try {
					cc.sendMessage(messageBox.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				messageBox.setText("");
			}
			
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		updateText();
	}
	
	
	public void updateText () {
		String str = cm.toString();
		if (!str.equals(last_str)) {
			chatBox.setText(str);
			last_str = new String(str);
			revalidate();
			repaint();
		}
	}


}
